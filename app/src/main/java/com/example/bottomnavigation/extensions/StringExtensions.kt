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
        else -> return R.drawable.ic_baseline_catching_pokemon_24
    }
}