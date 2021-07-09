package com.empresadeapp.youlook.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.empresadeapp.youlook.R
import com.empresadeapp.youlook.core.Result
import com.empresadeapp.youlook.data.remote.auth.AuthDataSource
import com.empresadeapp.youlook.databinding.FragmentLoginBinding
import com.empresadeapp.youlook.domain.auth.AuthRepoImpl
import com.empresadeapp.youlook.presentation.auth.AuthViewModel
import com.empresadeapp.youlook.presentation.auth.AuthViewModelFactory
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val viewModel by viewModels<AuthViewModel> { AuthViewModelFactory(AuthRepoImpl(AuthDataSource())) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        isUserLoggedIn()
        doLogin()
        register()
    }

    private fun isUserLoggedIn(){
        firebaseAuth.currentUser?.let {user ->
            if (user.isEmailVerified){
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }
    }

    private fun doLogin(){
        binding.btnSignin.setOnClickListener {
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            validation(email,password)
            signIn(email,password)
        }
    }

    private fun validation(email: String, password: String){
        if (email.isEmpty()){
            binding.editTextEmail.error = "Ingrese Email"
            return
        }
        if (password.isEmpty()){
            binding.editTextPassword.error = "Ingrese Contraseña"
            return
        }
    }

    private fun signIn(email: String, password: String){
        viewModel.signIn(email,password).observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                    binding.btnSignin.isEnabled = false
                }
                is Result.Success -> {
                    firebaseAuth.currentUser?.let { user ->
                        if (user.isEmailVerified) {
                            binding.progressbar.visibility = View.GONE
                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        } else {
                            Toast.makeText(requireContext(), "Verifique su email", Toast.LENGTH_SHORT).show()
                            binding.btnSignin.isEnabled = true
                        }
                    }

                }
                is Result.Failure -> {
                    binding.progressbar.visibility = View.GONE
                    binding.btnSignin.isEnabled = true
                    Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun register () {
        binding.textSignup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
}