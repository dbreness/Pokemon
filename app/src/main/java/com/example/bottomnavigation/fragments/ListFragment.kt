package com.example.bottomnavigation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.bottomnavigation.R
import com.example.bottomnavigation.adapter.PokemonAdapter
import com.example.bottomnavigation.databinding.FragmentDetailBinding
import com.example.bottomnavigation.databinding.FragmentListBinding
import com.example.bottomnavigation.models.Pokemon

class ListFragment : Fragment(R.layout.fragment_list) {

    private val adapter = PokemonAdapter()

    private var _binding : FragmentListBinding? = null
    private val binding  get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)


        binding.PokemonRecyclerView.adapter = adapter
        binding.PokemonRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(),VERTICAL))

        adapter.pokemones = getPokemons()
        return binding.root
    }


    private fun getPokemons(): List<Pokemon>{
        return mutableListOf(
            Pokemon("Squirtle","https://pngimg.com/uploads/pokemon/pokemon_PNG72.png", "Water"),
            Pokemon("Jolteon","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSLelt2ZEdkMwAt44gDvitHSrneE3NshMYDiw&usqp=CAU","Electric"),
            Pokemon("Charmander","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSuSUq6XNdmtTLkeEsFxPqoL_9V40NiW-Xq7Q&usqp=CAU","Fire"),
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}