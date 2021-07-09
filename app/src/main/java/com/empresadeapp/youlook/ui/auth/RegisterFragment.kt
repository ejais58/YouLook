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
import com.empresadeapp.youlook.databinding.FragmentRegisterBinding
import com.empresadeapp.youlook.domain.auth.AuthRepoImpl
import com.empresadeapp.youlook.presentation.auth.AuthViewModel
import com.empresadeapp.youlook.presentation.auth.AuthViewModelFactory


class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<AuthViewModel> { AuthViewModelFactory(AuthRepoImpl(AuthDataSource())) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        registrar()
    }

    private fun registrar(){
        binding.btnSignup.setOnClickListener {

            val nombre = binding.editTextName.text.toString().trim()
            val apellido = binding.editTextApellido.text.toString().trim()
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            val confpassword = binding.editTextConfirmPassword.text.toString().trim()

            validateUserData(password, confpassword, nombre, apellido, email)

            createUser(email,password,nombre,apellido)
        }
    }




    private fun validateUserData(password: String, confpassword: String, nombre: String, apellido: String, email: String) {
        if (password != confpassword) {
            binding.editTextConfirmPassword.error = "La contraseña no coincide"
            binding.editTextPassword.error = "La contraseña no coincide"
            return
        }

        if (nombre.isEmpty()) {
            binding.editTextName.error = "Coloque Nombre"
            return
        }
        if (apellido.isEmpty()) {
            binding.editTextApellido.error = "Coloque Apellido"
            return
        }
        if (email.isEmpty()) {
            binding.editTextEmail.error = "Ingrese E-mail"
            return
        }
        if (password.isEmpty()) {
            binding.editTextPassword.error = "Ingrese Contraseña"
            return
        }
        if (confpassword.isEmpty()) {
            binding.editTextPassword.error = "Confirme Contraseña"
            return
        }
    }

    private fun createUser(email: String, password: String, nombre: String, apellido: String) {
        viewModel.signUp(email,password, nombre, apellido).observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                    binding.btnSignup.isEnabled = false
                }
                is Result.Success -> {
                    binding.progressbar.visibility = View.GONE
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
                is Result.Failure -> {
                    binding.progressbar.visibility = View.GONE
                    binding.btnSignup.isEnabled = true
                    Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}