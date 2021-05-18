package com.example.bottomnavigation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.bottomnavigation.models.PokemonReference
import com.example.bottomnavigation.repositories.PokemonRepository
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokemonDBViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: PokemonRepository = PokemonRepository(application.applicationContext)

    fun getPokemonList() : LiveData<List<PokemonReference>>{
        return repository.getAllPokemons()
    }

    fun getPokemon(name: String) : LiveData<List<PokemonReference>>{
        return repository.getPokemon(name)
    }

    fun getPokeList() : List<PokemonReference>{
        return repository.getAllPoke()
    }

    fun addPokemon(name:String, url:String){
        viewModelScope.launch (Dispatchers.IO) {
            if (!repository.getPokemonExistsByName(name))
                repository.insertPokemon(PokemonReference(name = name,url = url))
            else {
                repository.deletePokemon(PokemonReference(name = name, url = url))
            }
        }
    }

    fun getPokemonExistsByName(name:String):Boolean{
        var result: Boolean = false
        viewModelScope.launch () {
            result = repository.getPokemonExistsByName(name)
        }

        return result
    }
}