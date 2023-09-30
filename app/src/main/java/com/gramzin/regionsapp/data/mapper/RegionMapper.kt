package com.gramzin.regionsapp.data.mapper

import com.gramzin.regionsapp.data.repository.model.RegionResponseDto
import com.gramzin.regionsapp.domain.model.Region
import javax.inject.Inject

class RegionMapper @Inject constructor() {

    fun mapToDomain(regionResponseDto: RegionResponseDto) =
        regionResponseDto.brands.map {
            Region(
                id = it.brandId,
                name = it.title,
                imageUrls = it.thumbUrls,
                viewsCount = it.viewsCount,
                isFavorite = false
            )
        }
}