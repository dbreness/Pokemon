package com.example.bottomnavigation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavigation.databinding.PokemonCellBinding
import com.example.bottomnavigation.fragments.ListFragmentDirections
import com.example.bottomnavigation.models.PokemonReference
import com.squareup.picasso.Picasso


class PokemonAdapter : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>(), Filterable {


    var pokemonsList: List<PokemonReference> = emptyList()
    var pokemonsListFilter: List<PokemonReference> = emptyList()
    var pokemons: List<PokemonReference> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class PokemonViewHolder(private val binding: PokemonCellBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(pokemon:PokemonReference){

            binding.txtCellName.text = pokemon.name

            //Se controla el evento del click sobre la celda
            binding.root.setOnClickListener {

                val navController = Navigation.findNavController(binding.root)
                val action = ListFragmentDirections.actionListFragmentToDetailFragment2(pokemon.name)
                navController.navigate(action)
            }

            val s = pokemon.url.dropLast(1).split("/").last().toString()
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

        val pokemon = pokemons[position]
        holder.bind(pokemons[position])
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