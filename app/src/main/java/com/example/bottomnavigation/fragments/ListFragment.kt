package com.example.bottomnavigation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.bottomnavigation.R
import com.example.bottomnavigation.adapter.PokemonAdapter
import com.example.bottomnavigation.databinding.FragmentDetailBinding
import com.example.bottomnavigation.databinding.FragmentListBinding
import com.example.bottomnavigation.models.Pokemon
import com.example.bottomnavigation.models.PokemonDetail
import com.example.bottomnavigation.viewmodels.PokemonListViewModel

class ListFragment : Fragment(R.layout.fragment_list) {

    private val adapter = PokemonAdapter()
    private val viewModel: PokemonListViewModel by viewModels()

    private var _binding : FragmentListBinding? = null
    private val binding  get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.makeAPIRequest()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)


        binding.PokemonRecyclerView.adapter = adapter
        binding.PokemonRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(),VERTICAL))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPokemonList().observe(viewLifecycleOwner){
            adapter.pokemons = it
        }

        binding.PokemonRecyclerView.setOnClickListener(){
            
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}