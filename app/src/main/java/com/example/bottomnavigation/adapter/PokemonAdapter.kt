package com.example.bottomnavigation.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavigation.R
import com.example.bottomnavigation.databinding.PokemonCellBinding
import com.example.bottomnavigation.models.PokemonReference
import com.example.bottomnavigation.viewmodels.PokemonDBViewModel
import com.squareup.picasso.Picasso
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlin.collections.ArrayList


class PokemonAdapter : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>(), Filterable {


    private val clicksAcceptor = PublishSubject.create<PokemonReference>()
    private val favoriteButtonClickListener = PublishSubject.create<PokemonReference>()

    val onItemClicked: Observable<PokemonReference> = clicksAcceptor.hide()
    val onFavoriteButtonClick: Observable<PokemonReference> = favoriteButtonClickListener.hide()

    var pokemonsList: List<PokemonReference> = emptyList()
    var pokemonsListFilter: List<PokemonReference> = emptyList()
    var pokemonsFavorites: List<PokemonReference> = emptyList()

    var pokemons: List<PokemonReference> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class PokemonViewHolder(private val binding: PokemonCellBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(pokemon:PokemonReference){
            binding.txtCellName.text = pokemon.name

            if (pokemonsFavorites.contains(pokemon)){
                setFavoriteStyle(binding.favoriteButton,1)
            }

            val pokemonId = pokemon.url.dropLast(1).split("/").last().toString()
            val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemonId}.png"
            Picasso.get().load(imageUrl).into(binding.imgCell)

            //Evento RX para controlar click sobre las celdas
            binding.detailsButton.setOnClickListener{
                clicksAcceptor.onNext(pokemon)
            }

            binding.favoriteButton.setOnClickListener {
                if (pokemonsFavorites.contains(pokemon))
                    setFavoriteStyle(binding.favoriteButton, 0)
                else
                    setFavoriteStyle(binding.favoriteButton,1)

                favoriteButtonClickListener.onNext(pokemon)
            }


        }

        fun setFavoriteStyle(button:ImageButton, style:Int){
            if (style == 0)
                button.setImageResource(android.R.drawable.btn_star_big_off)
            else
                button.setImageResource(android.R.drawable.btn_star_big_on)


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