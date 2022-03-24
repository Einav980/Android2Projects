package com.example.composeproject.model

enum class CharacterType{ HUMAN, NON_HUMAN, ANIMAL}

data class Character(
    var id: Long,
    var name: String,
    var description: String,
    var dubber: String,
    var image1: String,
    var image2: String,
    var section: CharacterType
)
