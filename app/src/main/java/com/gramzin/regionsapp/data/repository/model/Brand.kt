package com.gramzin.regionsapp.data.repository.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class Brand(
    @Json(name = "brandId")
    val brandId: String,
    @Json(name = "slug")
    val slug: String,
    @Json(name = "tagIds")
    val tagIds: List<String>,
    @Json(name = "thumbUrls")
    val thumbUrls: List<String>,
    @Json(name = "title")
    val title: String,
    @Json(name = "type")
    val type: String,
    @Json(name = "viewsCount")
    val viewsCount: Int
)