package com.example.bottomnavigation.extensions

import com.example.bottomnavigation.R

fun String.mapToDrawable():Int{
    when (this){
        "grassland" -> return R.drawable.grassland
        "forest" -> return R.drawable.forest
        "waters-edge" -> return R.drawable.watersedge
        "sea" -> return R.drawable.sea
        "cave" -> return R.drawable.cave
        "mountain" -> return R.drawable.mountain
        "rough-terrain" -> return R.drawable.rough
        "urban" -> return R.drawable.urban
        "rare" -> return R.drawable.rare
        "water" -> return R.drawable.water
        "fire" -> return R.drawable.fire
        "grass" -> return R.drawable.grass
        "ground" -> return R.drawable.ground
        "rock" -> return R.drawable.rock
        "steel" -> return R.drawable.steel
        "ice" -> return R.drawable.ice
        "electric" -> return R.drawable.electric
        "dragon" -> return R.drawable.dragon
        "ghost" -> return R.drawable.ghost
        "psychic" -> return R.drawable.psychic
        "normal" -> return R.drawable.normal
        "fighting" -> return R.drawable.fighting
        "poison" -> return R.drawable.poison
        "bug" -> return R.drawable.bug
        "flying" -> return R.drawable.flying
        "dark" -> return R.drawable.dark
        "fairy" -> return R.drawable.fairy
        else -> return R.drawable.ic_baseline_catching_pokemon_24
    }
}