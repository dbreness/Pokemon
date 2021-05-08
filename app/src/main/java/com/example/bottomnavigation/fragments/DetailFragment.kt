package com.example.bottomnavigation.fragments

import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.bottomnavigation.R
import com.example.bottomnavigation.databinding.FragmentDetailBinding
import com.example.bottomnavigation.extensions.mapToDrawable
import com.example.bottomnavigation.viewmodels.PokemonListViewModel
import com.squareup.picasso.Picasso

class DetailFragment : Fragment(R.layout.fragment_detail) {

//    Obtener los valores de los argumentos
//    val arguments : DetailFragmentArgs by navArgs()

    private val viewModel: PokemonListViewModel by viewModels()

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    val args: DetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.makeAPIRequestDetail(args.name)
        viewModel.makeAPIRequestSpecies(args.name)

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

        viewModel.getPokemonDetail().observe(viewLifecycleOwner) {
            binding.txtCardTitle.text = it.name.capitalize()
            val x =
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/" + it.id + ".png"
            Picasso.get().load(x).into(binding.imgCardImage)
        }

        viewModel.getPokemonSpecie().observe(viewLifecycleOwner) {
            binding.txtCardTitle.setCompoundDrawablesWithIntrinsicBounds(0,0,it.habitat.name.mapToDrawable(),0)
            binding.txtCardSubtitle.text = it.flavor_text_entries[0].flavor_text.replace("\n", " ")
            binding.txtCardDescription.text =
                it.flavor_text_entries[7].flavor_text.replace("\n", " ")
        }
//        binding.txtCardTitle.setText(arguments.user.email)?:""
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}