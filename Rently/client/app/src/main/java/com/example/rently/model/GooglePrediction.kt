package com.example.rently.model

data class GooglePrediction(
    val description: String,
    val terms: List<GooglePredictionTerm>
)
