package com.example.bottomnavigation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.bottomnavigation.R
import com.example.bottomnavigation.databinding.FragmentDetailBinding

class DetailFragment : Fragment(R.layout.fragment_detail) {

//    Obtener los valores de los argumentos
//    val arguments : DetailFragmentArgs by navArgs()

    private var _binding : FragmentDetailBinding? = null
    private val binding  get() = _binding!!

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

//        binding.txtCardTitle.setText(arguments.user.email)?:""

    }



}