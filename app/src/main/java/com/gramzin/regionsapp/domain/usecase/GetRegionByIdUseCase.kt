package com.gramzin.regionsapp.domain.usecase

import com.gramzin.regionsapp.domain.model.Region
import com.gramzin.regionsapp.domain.repository.RegionsRepository
import com.gramzin.regionsapp.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRegionByIdUseCase @Inject constructor(
    private val repository: RegionsRepository
) {

    operator fun invoke(id: String): Flow<Region> {
        return repository.getRegionById(id)
    }
}