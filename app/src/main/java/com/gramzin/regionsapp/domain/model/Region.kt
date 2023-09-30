package com.gramzin.regionsapp.domain.model

data class Region(
    val id: String,
    val name: String,
    val imageUrls: List<String>,
    val viewsCount: Int,
    val isFavorite: Boolean
)