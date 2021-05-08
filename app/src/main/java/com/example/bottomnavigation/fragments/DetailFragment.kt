package com.example.bottomnavigation.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.example.bottomnavigation.R
import com.example.bottomnavigation.databinding.FragmentDetailBinding
import com.example.bottomnavigation.extensions.mapToDrawable
import com.example.bottomnavigation.viewmodels.PokemonDetailViewModel
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
            binding.txtCardTitle.setCompoundDrawablesWithIntrinsicBounds(0,0,it.habitat.name.mapToDrawable(),0)
            binding.txtCardSubtitle.text = it.flavor_text_entries[0].flavor_text.replace("\n", " ")
            binding.txtCardDescription.text =
                it.flavor_text_entries[7].flavor_text.replace("\n", " ")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}