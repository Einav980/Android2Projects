package com.example.countries.models

data class Flag(
    var png: String
){
    override fun toString(): String {
        return png
    }
}
