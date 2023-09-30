package com.gramzin.regionsapp.data.network

import com.gramzin.regionsapp.data.repository.model.RegionResponseDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface RegionsApiService {

    @GET("api/guide-service/v1/getBrands")
    suspend fun getRegions(): RegionResponseDto
}