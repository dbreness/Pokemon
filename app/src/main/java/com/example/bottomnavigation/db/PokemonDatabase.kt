package com.example.bottomnavigation.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bottomnavigation.db.dao.PokemonDAO
import com.example.bottomnavigation.models.PokemonReference

@Database(entities = arrayOf(PokemonReference::class), version = 1)
abstract class PokemonDatabase : RoomDatabase(){
    abstract fun pokemonDAO(): PokemonDAO

    companion object{
        @Volatile
        private var INSTANCE: PokemonDatabase? = null

        fun getDatabase(context: Context):PokemonDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PokemonDatabase::class.java,
                    "pokemondatabase"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}