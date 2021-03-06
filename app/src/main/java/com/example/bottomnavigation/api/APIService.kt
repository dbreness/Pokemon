package com.example.bottomnavigation.api

import com.example.bottomnavigation.models.*
import retrofit2.Call
import retrofit2.http.*

interface APIService {
    @GET("pokemon")
    fun getPokemonList(
        @Query("limit") limit:Int,
        @Query("offset") offset: Int
    ):Call<PokemonListResponse>

    @GET("pokemon/{name}")
    fun getPokemonDetail(
        @Path("name") name:String
    ):Call<Pokemon>

    @GET("pokemon-species/{name}")
    fun getPokemonSpeciesDetail(
        @Path("name") name:String
    ):Call<PokemonSpecie>

    @GET("evolution-chain/{id}")
    fun getPokemonEvolutionChain(
        @Path("id") id:String
    ):Call<EvolutionChainResponse>
}