package com.myprojects.ecommerceapp.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.myprojects.ecommerceapp.MainActivity
import com.myprojects.ecommerceapp.R
import com.myprojects.ecommerceapp.databinding.FragmentRegisterBinding
import com.myprojects.ecommerceapp.model.User
import com.myprojects.ecommerceapp.viewmodel.AppViewModel

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private var _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    lateinit var navController: NavController
    lateinit var appViewModel: AppViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater,container,false)

        clickbutton()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        appViewModel = (activity as MainActivity).appViewModel
    }

    private fun clickbutton(){
        binding.apply {
            buttonDaftar.setOnClickListener {
                RegisterUser()
            }
            buttonToLogin.setOnClickListener {
                navController.navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }
    }

    private fun RegisterUser() {
        val nameField = binding.editTextRegNama.text.toString().trim()
        val passwordField = binding.editTextRegPassword.text.toString().trim()

        appViewModel.checkExitsUser().observe(viewLifecycleOwner) {
            if (it.contains(nameField)) {
                Toast.makeText(context, "UserName Ini Sudah Ada", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("RegisterFragment", "Name: $nameField, Password: $passwordField")
                appViewModel.registerUser(User(0,nameField,passwordField,100))
                navController.navigate(R.id.action_registerFragment_to_loginFragment)
                Toast.makeText(context, "Berhasil Registrasi Akun", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            when (navController.currentDestination?.id) {
                R.id.loginFragment -> {
                    navController.navigate(R.id.action_registerFragment_to_loginFragment)
                }
                R.id.profileFragment -> {
                    navController.navigate(R.id.action_registerFragment_to_profileFragment)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}