package com.gramzin.regionsapp.domain.repository

import com.gramzin.regionsapp.domain.model.Region
import com.gramzin.regionsapp.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface RegionsRepository {

    fun getRegions(): SharedFlow<List<Region>>

    fun getRegionById(id: String): Flow<Region>

    suspend fun refreshRegions()

    suspend fun changeLikeStateRegion(region: Region)
}