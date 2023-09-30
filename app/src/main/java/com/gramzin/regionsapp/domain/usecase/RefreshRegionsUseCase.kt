package com.gramzin.regionsapp.domain.usecase

import com.gramzin.regionsapp.domain.repository.RegionsRepository
import javax.inject.Inject

class RefreshRegionsUseCase @Inject constructor(
    private val repository: RegionsRepository
) {

    suspend operator fun invoke(){
        repository.refreshRegions()
    }
}