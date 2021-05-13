package com.example.bottomnavigation.fragments

import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Filter
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.isVisible
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.example.bottomnavigation.R
import com.example.bottomnavigation.adapter.PokemonAdapter
import com.example.bottomnavigation.databinding.FragmentListBinding
import com.example.bottomnavigation.databinding.PokemonCellBinding
import com.example.bottomnavigation.viewmodels.PokemonListViewModel
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit
import kotlin.math.max
import kotlin.math.min


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
        binding.PokemonRecyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                VERTICAL
            )
        )

        //Clase para el manejo del swipe de la celda
        class SwipeHelperCallBack : ItemTouchHelper.Callback() {

            private var currentPosition: Int? = null
            private var previousPosition: Int? = null
            private var currentDx = 0f
            private var clamp = 0f

            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return makeMovementFlags(0, SCROLL_INDICATOR_RIGHT)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            }

            override fun clearView(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) {
                currentDx = 0f
                previousPosition = viewHolder.adapterPosition
                getDefaultUIUtil().clearView(getView(viewHolder))

            }

            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                viewHolder?.let {
                    currentPosition = viewHolder.adapterPosition
                    getDefaultUIUtil().onSelected(getView(it))
                }
            }

            override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
                val isClamped = getTag(viewHolder)
                setTag(viewHolder, !isClamped && currentDx >= clamp)
                return 2f
            }

            @RequiresApi(Build.VERSION_CODES.Q)
            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                if (actionState == ACTION_STATE_SWIPE) {
                    val view = getView(viewHolder)
                    val isClamped = getTag(viewHolder)
                    val x = clampViewPositionHorizontal(view, dX, isClamped, isCurrentlyActive)

                    currentDx = x
                    getDefaultUIUtil().onDraw(c, recyclerView, view, x, dY, actionState, isCurrentlyActive)
                }


            }

            private fun getView(viewHolder: RecyclerView.ViewHolder): View {
                return (viewHolder as PokemonAdapter.PokemonViewHolder).itemView.findViewById(R.id.swipe_view)
            }


            override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
                return defaultValue * 10
            }

            private fun clampViewPositionHorizontal(
                view: View,
                dX: Float,
                isClamped: Boolean,
                isCurrentlyActive: Boolean
            ): Float {
                val min: Float = 0f
                val max: Float = view.right.toFloat()/2

                val x = if (isClamped) {
                    if (isCurrentlyActive) dX + clamp else clamp
                } else {
                    dX
                }

                return min(max(min, x), max)
            }

            private fun setTag(viewHolder: RecyclerView.ViewHolder, isClamped: Boolean) {
                viewHolder.itemView.tag = isClamped
            }

            private fun getTag(viewHolder: RecyclerView.ViewHolder): Boolean {
                return viewHolder.itemView.tag as? Boolean ?: false
            }

            fun setClamp(clamp: Float) {
                this.clamp = clamp
            }

            fun removePreviousClamp(recyclerView: RecyclerView) {
                if (currentPosition == previousPosition)
                    return
                previousPosition?.let {
                    val viewHolder = recyclerView.findViewHolderForAdapterPosition(it) ?: return
                    getView(viewHolder).translationX = 0f
                    setTag(viewHolder, false)
                    previousPosition = null
                }
            }
        }

        val swipeHelperCallBack = SwipeHelperCallBack().apply {
            setClamp(500f)
        }
        val itemTouchHelper = ItemTouchHelper(swipeHelperCallBack)
        itemTouchHelper.attachToRecyclerView(binding.PokemonRecyclerView)

        binding.PokemonRecyclerView.apply {
            setOnTouchListener { _, _ ->
                swipeHelperCallBack.removePreviousClamp(this)
                false
            }
        }

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
                .skipInitialValue()
                .debounce(300, TimeUnit.MILLISECONDS)
                .map { it.toString() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    adapter.filter.filter(it, object : Filter.FilterListener {
                        override fun onFilterComplete(count: Int) {
                            binding.listEmpty.visibility = if (adapter.itemCount == 0) VISIBLE else GONE
                        }
                    }
                }
        )

        //Suscribirse al evento del click sobre la celda de la lista de pokemons
        disposable.add(
            adapter.onItemClicked
                .subscribe {
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