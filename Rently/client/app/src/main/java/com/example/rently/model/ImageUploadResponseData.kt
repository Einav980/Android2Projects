package com.example.rently.model

data class ImageUploadResponseData(
    val id: String,
    val deleteUrl: String,
    val url: String,
    val displayUrl: String,
    val width: Int,
    val height: Int,
    val size: Int
) {}
