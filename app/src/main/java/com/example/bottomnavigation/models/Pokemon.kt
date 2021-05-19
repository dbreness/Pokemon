package com.example.bottomnavigation.models

import retrofit2.http.Url

data class Pokemon(val id: Int, val name: String, val height: Int, val weight: Int, val types: List<PokemonType>) {

}

data class PokemonSpecie(val is_baby:Boolean,
                         val is_legendary: Boolean,
                         val is_mythical: Boolean,
                         val flavor_text_entries: List<FlavorTextEntry>,
                         val color: Color,
                         val habitat: Habitat,
                         val evolves_from_species: PreEvolution,
                         val evolution_chain: EvolutionChainReference
) {

}


data class FlavorTextEntry(val flavor_text: String){

}

data class Color(val name: String){

}

data class Habitat(val name: String){

}

data class PokemonType (val slot: Int, val type: Type)

data class Type(val name: String){

}

data class PreEvolution(val name: String){

}

data class EvolutionChainReference(val url: String){

}

data class EvolutionChainResponse(val chain: EvolutionChain){

}

data class EvolutionChain(val evolves_to: List<EvolutionChain>, val species: Specie){

}

data class Specie(val name:String){

}


