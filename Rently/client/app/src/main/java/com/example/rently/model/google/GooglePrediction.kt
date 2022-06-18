package com.example.rently.model.google

data class GooglePrediction(
    val description: String,
    val terms: List<GooglePredictionTerm>
)
