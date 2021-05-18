package com.example.bottomnavigation.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonReference (
    @PrimaryKey val name: String,
    val url: String,
    val isFavorite: Boolean = false){
}