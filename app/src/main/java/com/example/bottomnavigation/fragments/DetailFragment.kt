package com.example.bottomnavigation.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bottomnavigation.R
import com.example.bottomnavigation.databinding.FragmentDetailBinding
import com.example.bottomnavigation.extensions.mapToDrawable
import com.example.bottomnavigation.models.EvolutionChain
import com.example.bottomnavigation.models.EvolutionChainResponse
import com.example.bottomnavigation.viewmodels.PokemonDetailViewModel
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.itemClicks
import com.squareup.picasso.Picasso

class DetailFragment : Fragment(R.layout.fragment_detail) {

    //Obtener los valores de los argumentos
    private val arguments : DetailFragmentArgs by navArgs()

    private val viewMoleDetail : PokemonDetailViewModel by viewModels()

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Llamados al servidor
        viewMoleDetail.makeAPIRequestDetail(arguments.name)
        viewMoleDetail.makeAPIRequestSpecies(arguments.name)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // suscribirse al evento que retorna la informacion de un Pokemon especifico
        viewMoleDetail.getPokemonDetail().observe(viewLifecycleOwner) {
            binding.txtCardTitle.text = it.name.capitalize()
            val x =
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/" + it.id + ".png"
            Picasso.get().load(x).into(binding.imgCardImage)
        }

        viewMoleDetail.getPokemonSpecie().observe(viewLifecycleOwner) {
            binding.txtCardTitle.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                it.habitat.name.mapToDrawable(),
                0
            )
            binding.txtCardSubtitle.text = it.flavor_text_entries[0].flavor_text.replace("\n", " ")
            binding.txtCardDescription.text =
                it.flavor_text_entries[7].flavor_text.replace("\n", " ")
            if (it.evolves_from_species != null)
                binding.btnCardActionPrimary.text = it.evolves_from_species.name
            else
                binding.btnCardActionPrimary.text = "No tiene"

            val id = it.evolution_chain.url.dropLast(1).split("/").last()
            viewMoleDetail.makeAPIRequestEvolutionChain(id)
        }

        binding.btnCardActionPrimary.clicks()
            .subscribe{
                val text = binding.btnCardActionPrimary.text
                if (text != "No tiene"){
                    val action = DetailFragmentDirections.actionDetailFragment2Self(text.toString())
                    findNavController().navigate(action)
                }

            }

        viewMoleDetail.getEvolutionChain().observe(viewLifecycleOwner){
            val pokemons = getEvolutionList(it.chain)

            val adapter = ArrayAdapter(view.context,R.layout.simplerow,pokemons)

            binding.evolutionList.adapter = adapter

        }

        binding.evolutionList.itemClicks()
            .subscribe(){
                val name = binding.evolutionList.adapter.getItem(it).toString()
                val action = DetailFragmentDirections.actionDetailFragment2Self(name.decapitalize())
                findNavController().navigate(action)
            }


    }

    //funcion recursiva para obtener toda la cadena de evoluciones
    private fun getEvolutionList (itemlist:EvolutionChain):ArrayList<String>{
        var list = arrayListOf<String>()
        if(itemlist.evolves_to.size>0) {
            for (chain in itemlist.evolves_to) {
                list.addAll(getEvolutionList(chain).toList())
            }
        }
        list.add(itemlist.species.name.capitalize())
        return list
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}