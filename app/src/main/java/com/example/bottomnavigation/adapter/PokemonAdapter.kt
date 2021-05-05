package com.example.bottomnavigation.adapter

import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavigation.R
import com.example.bottomnavigation.databinding.FragmentMainBinding
import com.example.bottomnavigation.databinding.PokemonCellBinding
import com.example.bottomnavigation.models.Pokemon
import com.squareup.picasso.Picasso

class PokemonAdapter : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {


    //Se crea el set para la lista qe va a alimentar el adapter(datasource)
     var pokemones: List<Pokemon> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class PokemonViewHolder(private val binding: PokemonCellBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(name: String, imageUrl: String, description: String){
            binding.txtCellTitle.text = name
            binding.txtCellDescription.text = description


            //Se controla el evento del click sobre la celda
            binding.root.setOnClickListener {
                binding.txtCellDescription.visibility = if (binding.txtCellDescription.isVisible) View.GONE else View.VISIBLE

            }
            Picasso.get().load(imageUrl).into(binding.imgCell)
        }
    }

    //Metodo encargado de inflar el layout/xml en cada celda
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {

        val binding = PokemonCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

    //Metodo encargado de pintar datos para cada celda dependiendo se la posicion
    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemones[position]
        holder.bind(pokemon.name, pokemon.imageUrl, pokemon.description)
    }
    //Metodo que determina la cantidad de elementos  en la lista
    override fun getItemCount(): Int = pokemones.size
}