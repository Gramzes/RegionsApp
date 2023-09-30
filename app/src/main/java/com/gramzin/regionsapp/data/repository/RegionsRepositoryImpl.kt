package com.gramzin.regionsapp.data.repository

import com.gramzin.regionsapp.data.mapper.RegionMapper
import com.gramzin.regionsapp.data.network.RegionsApiService
import com.gramzin.regionsapp.domain.model.Region
import com.gramzin.regionsapp.domain.repository.RegionsRepository
import com.gramzin.regionsapp.util.Result
import com.gramzin.regionsapp.util.mergeWith
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegionsRepositoryImpl @Inject constructor(
    private val apiService: RegionsApiService,
    private val mapper: RegionMapper
    ): RegionsRepository {

    // Список идентификаторов избранных регионов.
    private val favoriteRegionsId = mutableSetOf<String>()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val changeLikeState = MutableSharedFlow<Region>(replay = 1)

    // SharedFlow для добавления региона в список избранных.
    private val regionsWithChangedStatus = flow{
        changeLikeState.collect{
            if (it.isFavorite){
                favoriteRegionsId.remove(it.id)
            } else {
                favoriteRegionsId.add(it.id)
            }
            emit(regions.first())
        }
    }.shareIn(coroutineScope, SharingStarted.Eagerly)


    // SharedFlow для хранения списка регионов.
    private val regions: SharedFlow<List<Region>> = flow {
        refreshRegions.emit(Unit)
        refreshRegions.collect {
            val regions = mapper.mapToDomain(apiService.getRegions())
            emit(regions)
        }
    }.mergeWith(regionsWithChangedStatus)
        .map { regions ->
            //Преобразование полученных регионов в соответствии со списком сохраненных
            //избранный регионов.
            regions.map {
                if (favoriteRegionsId.contains(it.id))
                    it.copy(isFavorite = true)
                else
                    it.copy(isFavorite = false)
            }
        }
        .retry(3) {
            delay(RETRY_TIMEOUT_MILLIS)
            true
        }
        .catch {  }
        .shareIn(coroutineScope, SharingStarted.Lazily, replay = 1)

    // SharedFlow для сигнала обновления регионов.
    private val refreshRegions = MutableSharedFlow<Unit>(replay = 1)

    override fun getRegions() = regions

    override fun getRegionById(id: String): Flow<Region> {
        return regions.map {
            it.find { it.id == id }!!
        }
    }

    override suspend fun refreshRegions() {
        refreshRegions.emit(Unit)
    }

    override suspend fun changeLikeStateRegion(region: Region) {
        changeLikeState.emit(region)
    }

    companion object {
        const val RETRY_TIMEOUT_MILLIS = 3000L
    }
}