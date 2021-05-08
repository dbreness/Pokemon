package com.example.bottomnavigation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bottomnavigation.api.APIService
import com.example.bottomnavigation.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonListViewModel:ViewModel (){

    private val pokemonList = MutableLiveData<List<PokemonReference>>()
    private val pokemonDetail = MutableLiveData<PokemonDetail>()
    private val pokemon = MutableLiveData<Pokemon>()
    private val pokemonSpecie = MutableLiveData<PokemonSpecie>()
    private var service: APIService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(APIService::class.java)
    }

    fun makeAPIRequestList(){
        service.getPokemonList(1118,0).enqueue(object: Callback<PokemonListResponse>{
            override fun onResponse(
                call: Call<PokemonListResponse>,
                response: Response<PokemonListResponse>
            ) {
                response.body()?.let {
                    pokemonList.postValue(it.results)
                }
            }

            override fun onFailure(call: Call<PokemonListResponse>, t: Throwable) {

            }

        })
    }

    fun makeAPIRequestDetail(name: String){
        service.getPokemonDetail(name).enqueue(object: Callback<Pokemon>{
            override fun onResponse(
                call: Call<Pokemon>,
                response: Response<Pokemon>)
            {
                response.body()?.let {
                    pokemon.postValue(it)

                }
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
            }

        })
    }

    fun makeAPIRequestSpecies(name: String){
        service.getPokemonSpeciesDetail(name).enqueue(object: Callback<PokemonSpecie>{
            override fun onResponse(
                call: Call<PokemonSpecie>,
                response: Response<PokemonSpecie>)
            {
                response.body()?.let {
                    pokemonSpecie.postValue(it)

                }
            }

            override fun onFailure(call: Call<PokemonSpecie>, t: Throwable) {
            }

        })
    }

    fun getPokemonList():LiveData<List<PokemonReference>>{
        return pokemonList
    }

    fun getPokemonDetail():LiveData<Pokemon>{
        return pokemon
    }

    fun getPokemonSpecie():LiveData<PokemonSpecie>{
        return pokemonSpecie
    }
}