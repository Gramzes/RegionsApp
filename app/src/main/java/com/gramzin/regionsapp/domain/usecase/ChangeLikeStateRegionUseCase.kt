package com.gramzin.regionsapp.domain.usecase

import com.gramzin.regionsapp.domain.model.Region
import com.gramzin.regionsapp.domain.repository.RegionsRepository
import javax.inject.Inject

class ChangeLikeStateRegionUseCase @Inject constructor(
    private val repository: RegionsRepository
) {

    suspend operator fun invoke(region: Region){
        repository.changeLikeStateRegion(region)
    }
}