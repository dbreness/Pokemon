package com.example.bottomnavigation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.bottomnavigation.R
import com.example.bottomnavigation.adapter.PokemonAdapter
import com.example.bottomnavigation.databinding.FragmentListBinding
import com.example.bottomnavigation.viewmodels.PokemonListViewModel
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class ListFragment : Fragment(R.layout.fragment_list) {

    private val adapter = PokemonAdapter()
    private val viewModel: PokemonListViewModel by viewModels()

    private var _binding : FragmentListBinding? = null
    private val binding  get() = _binding!!

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Llamado al servidor
        viewModel.makeAPIRequestList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        binding.PokemonRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(),VERTICAL))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.PokemonRecyclerView.adapter = adapter


        viewModel.getPokemonList().observe(viewLifecycleOwner){
            adapter.pokemons = it
            adapter.pokemonsList = it
        }

        //Suscribirse al evento para la busqueda al escribir sobre inputEditText
        disposable.add(
            binding.searchInputEditText.textChanges()
                .debounce(300,TimeUnit.MILLISECONDS)
                .map { it.toString() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    adapter.filter.filter(it)
                }
        )

        //Suscribirse al evento del click sobre la celda de la lista de pokemons
        disposable.add(
            adapter.onItemClicked
                .subscribe{
                    val action = ListFragmentDirections.actionListFragmentToDetailFragment2(it.name)
                    findNavController().navigate(action)
                }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable.clear()
        _binding = null
    }
}