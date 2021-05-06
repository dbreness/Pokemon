package com.example.bottomnavigation.adapter

import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavigation.R
import com.example.bottomnavigation.databinding.FragmentMainBinding
import com.example.bottomnavigation.databinding.PokemonCellBinding
import com.example.bottomnavigation.models.Pokemon
import com.example.bottomnavigation.models.PokemonDetail
import com.example.bottomnavigation.models.PokemonReference
import com.example.bottomnavigation.viewmodels.PokemonListViewModel
import com.squareup.picasso.Picasso
import retrofit2.http.Url

class PokemonAdapter : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>(), Filterable {

    //Se crea el set para la lista qe va a alimentar el adapter(datasource)
/*
     var pokemones: List<Pokemon> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
*/

    var pokemonsList: List<PokemonReference> = emptyList()
    var pokemonsListFilter: List<PokemonReference> = emptyList()
    var pokemons: List<PokemonReference> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    lateinit var pokemonDetail: PokemonDetail


    inner class PokemonViewHolder(private val binding: PokemonCellBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(name: String, url: String){
            binding.txtCellName.text = name
            //binding.txtCellDescription.text = description


            //Se controla el evento del click sobre la celda
            binding.root.setOnClickListener {
                binding.txtCellDescription.visibility = if (binding.txtCellDescription.isVisible) View.GONE else View.VISIBLE

            }

            val s = url.dropLast(1).split("/").last().toString()
            val x = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"+s+".png"
            Picasso.get().load(x).into(binding.imgCell)
        }
    }

    //Metodo encargado de inflar el layout/xml en cada celda
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {


        val binding = PokemonCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)

    }

    //Metodo encargado de pintar datos para cada celda dependiendo se la posicion
    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
/*
        val pokemon = pokemones[position]
        holder.bind(pokemon.name, pokemon.imageUrl, pokemon.description)
*/

        val pokemon = pokemons[position]
        holder.bind(pokemon.name,pokemon.url)
    }
    //Metodo que determina la cantidad de elementos  en la lista
    override fun getItemCount(): Int = pokemons.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    pokemonsListFilter = pokemonsList
                } else {
                    val resultList = ArrayList<PokemonReference>()
                    for (row in pokemonsList) {
                        if (row.name.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            resultList.add(row)
                        }
                    }
                    pokemonsListFilter = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = pokemonsListFilter
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                pokemons = results?.values as List<PokemonReference>
                notifyDataSetChanged()
            }
        }
    }
}