package com.example.bottomnavigation.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.bottomnavigation.db.PokemonDatabase
import com.example.bottomnavigation.models.PokemonReference
import io.reactivex.rxjava3.core.Observable

class PokemonRepository(context: Context) {
    var db: PokemonDatabase = PokemonDatabase.getDatabase(context)

    fun insertPokemon(pokemon:PokemonReference){
        db.pokemonDAO().insertPokemon(pokemon)
    }

    fun deletePokemon(pokemon: PokemonReference){
        db.pokemonDAO().deletePokemon(pokemon)
    }

    fun getAllPokemons():LiveData<List<PokemonReference>> = db.pokemonDAO().getAllPokemons()

    fun getPokemon(name:String):LiveData<List<PokemonReference>> = db.pokemonDAO().getPokemon(name)

    fun getAllPoke():List<PokemonReference> = db.pokemonDAO().getAllPoke()

    suspend fun getPokemonExistsByName(name:String):Boolean{
        val result = db.pokemonDAO().getPokemonByName(name)
        return (result.size > 0)
    }
}