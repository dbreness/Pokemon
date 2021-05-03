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
import com.example.bottomnavigation.databinding.FragmentDetailBinding
import com.example.bottomnavigation.databinding.FragmentLoginBinding
import com.example.bottomnavigation.databinding.PokemonCellBinding
import com.example.bottomnavigation.models.Pokemon
import com.squareup.picasso.Picasso

class PokemonAdapter : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    private lateinit var txtCellTitle : TextView
    private lateinit var txtCellDescription : TextView
    private lateinit var imgCell : ImageView

    //Se crea el set para la lista qe va a alimentar el adapter(datasource)
     var pokemones: List<Pokemon> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(name: String, imageUrl: String, description: String){
            txtCellTitle.text = name
            txtCellDescription.text = description

            //Se controla el evento del click sobre la celda
            txtCellTitle.setOnClickListener {
                txtCellDescription.visibility = if (txtCellDescription.isVisible) View.GONE else View.VISIBLE

            }
            Picasso.get().load(imageUrl).into(imgCell)
        }
    }

    //Metodo encargado de inflar el layout/xml en cada celda
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_cell, parent, false)
        txtCellTitle = view.findViewById(R.id.txtCellTitle)
        txtCellDescription = view.findViewById(R.id.txtCellDescription)
        imgCell = view.findViewById(R.id.imgCell)
        return PokemonViewHolder(view)
    }

    //Metodo encargado de pintar datos para cada celda dependiendo se la posicion
    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemones[position]
        holder.bind(pokemon.name, pokemon.imageUrl, pokemon.description)
    }
    //Metodo que determina la cantidad de elementos  en la lista
    override fun getItemCount(): Int = pokemones.size
}