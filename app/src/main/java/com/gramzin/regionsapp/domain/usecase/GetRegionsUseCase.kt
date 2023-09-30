package com.gramzin.regionsapp.domain.usecase

import com.gramzin.regionsapp.domain.model.Region
import com.gramzin.regionsapp.domain.repository.RegionsRepository
import com.gramzin.regionsapp.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

class GetRegionsUseCase @Inject constructor(
    private val repository: RegionsRepository
) {

    operator fun invoke(): SharedFlow<List<Region>> {
        return repository.getRegions()
    }
}