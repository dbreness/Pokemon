package com.example.bottomnavigation.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.bottomnavigation.models.PokemonReference
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.QueryName
import java.util.*

@Dao
interface PokemonDAO {
    @Query ("SELECT * FROM PokemonReference")
    fun getAllPoke(): List<PokemonReference>

    @Query ("SELECT * FROM PokemonReference")
    fun getAllPokemons(): LiveData<List<PokemonReference>>

    @Query ("SELECT * FROM PokemonReference WHERE name LIKE :name")
    fun getPokemon(name:String) : LiveData<List<PokemonReference>>

    @Query ("SELECT * FROM PokemonReference WHERE name LIKE :name")
    suspend fun getPokemonByName(name:String) : List<PokemonReference>

    @Insert
    fun insertPokemon(pokemonReference: PokemonReference)

    @Delete
    fun deletePokemon(pokemonReference: PokemonReference)
}