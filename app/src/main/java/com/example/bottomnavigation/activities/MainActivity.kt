package com.example.bottomnavigation.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.bottomnavigation.R
import com.example.bottomnavigation.models.User
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
        setContentView(R.layout.activity_main)

//        visibilityNavElements(findNavController(R.id.fragment))

//        binding.bottomNavigation.menu.getItem(1).setOnMenuItemClickListener {
//            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(User("test@gmail.com","12345"))
//            findNavController().navigate(action)
//        }
    }
//
//    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
//        return super.onCreateView(name, context, attrs)
//    }

//    private fun visibilityNavElements(navController: NavController) {
//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            when (destination.id) {
//                R.id.loginFragment -> binding.bottomNavigation?.visibility = View.GONE
//                else -> binding.bottomNavigation?.visibility = View.VISIBLE
//            }
//        }
//    }
}