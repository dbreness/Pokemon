package com.example.bottomnavigation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bottomnavigation.R
import com.example.bottomnavigation.databinding.FragmentDetailBinding
import com.example.bottomnavigation.databinding.FragmentHomeBinding
import com.example.bottomnavigation.models.User

class HomeFragment: Fragment(R.layout.fragment_home) {

    private var _binding : FragmentHomeBinding? = null
    private  val binding get() = _binding!!

//    val arguments : HomeFragmentArgs by navArgs()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.textView.setText(arguments.email)?:""
//
//        binding.btnDetail.setOnClickListener {
//
//            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(User("test@gmail.com","12345"))
//            findNavController().navigate(action)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}