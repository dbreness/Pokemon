package com.example.bottomnavigation.api

import com.example.bottomnavigation.models.PokemonDetail
import com.example.bottomnavigation.models.PokemonListResponse
import retrofit2.Call
import retrofit2.http.*

interface APIService {
    @GET("pokemon")
    fun getPokemonList(
        @Query("limit") limit:Int,
        @Query("offset") offset: Int
    ):Call<PokemonListResponse>

    @GET("pokemon/{id}")
    fun getPokemonDetail(
        @Path("id") id:Int
    ):Call<PokemonDetail>
}