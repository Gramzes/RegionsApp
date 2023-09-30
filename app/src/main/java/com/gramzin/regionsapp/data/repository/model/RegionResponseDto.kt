package com.gramzin.regionsapp.data.repository.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class RegionResponseDto(
    @Json(name = "brands")
    val brands: List<Brand>
)