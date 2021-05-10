package com.example.bottomnavigation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bottomnavigation.R
import com.example.bottomnavigation.databinding.FragmentLoginBinding
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable


class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding : FragmentLoginBinding? = null
    private val binding  get() = _binding!!

    private val disposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RX habilitar boton de login hasta que se ingrese el email y password
        disposable.add(
            Observable.combineLatest(binding.emailText.textChanges(),binding.passwordText.textChanges(),
                { email, password -> email.toString().isNotEmpty() && password.toString().isNotEmpty()})
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    binding.button.isEnabled = it
                }
        )

        // RX evento de click del boton de login
        disposable.add(
            binding.button.clicks()
                .subscribe{
                    val action = LoginFragmentDirections.actionLoginFragmentToMainFragment()
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