package com.example.bottomnavigation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bottomnavigation.api.APIService
import com.example.bottomnavigation.models.PokemonDetail
import com.example.bottomnavigation.models.PokemonListResponse
import com.example.bottomnavigation.models.PokemonReference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonListViewModel:ViewModel (){

    private val pokemonList = MutableLiveData<List<PokemonReference>>()
    private val pokemonDetail = MutableLiveData<PokemonDetail>()
    private var service: APIService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(APIService::class.java)
    }

    fun makeAPIRequest(){
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

/*    fun makeAPIDetailRequest(id:Int){
        service.getPokemonDetail(id).enqueue(object: Callback<PokemonDetail>{
            override fun onResponse(
                call: Call<PokemonDetail>,
                response: Response<PokemonDetail>
            ) {
                response.body()?.let {
                    pokemonDetail.postValue(it)
                }
            }

            override fun onFailure(call: Call<PokemonDetail>, t: Throwable) {
            }

        })
    }*/

    fun getPokemonList():LiveData<List<PokemonReference>>{
        return pokemonList
    }
}