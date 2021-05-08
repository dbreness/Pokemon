package com.example.bottomnavigation.models

data class Pokemon(val id: Int, val name: String, val height: Int, val weight: Int) {

}

data class PokemonSpecie(val is_baby:Boolean,
                         val is_legendary: Boolean,
                         val is_mythical: Boolean,
                         val flavor_text_entries: List<FlavorTextEntry>,
                         val color: Color,
                         val habitat: Habitat) {

}


data class FlavorTextEntry(val flavor_text: String){

}

data class Color(val name: String){

}

data class Habitat(val name: String){

}

