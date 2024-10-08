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
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.myprojects.ecommerceapp.activity.MainActivity
import com.myprojects.ecommerceapp.R
import com.myprojects.ecommerceapp.databinding.FragmentLoginBinding
import com.myprojects.ecommerceapp.viewmodel.AppViewModel

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    lateinit var navController: NavController
    val appViewModel: AppViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        clickbutton()
    }

    private fun clickbutton(){
        binding.apply {
            buttonLogin.setOnClickListener {
                LoginUser()
            }
            buttonToDaftar.setOnClickListener {
                navController.navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
    }

    private fun LoginUser() {
        val nameField = binding.editTextLogNama.text.toString()
        val passwordField = binding.editTextLogPassword.text.toString()

        if (nameField.isEmpty() || passwordField.isEmpty()) {
            binding.editTextLogPassword.error = "Password tidak boleh kosong"
        } else {
            val logDat = appViewModel.login(nameField, passwordField)
            logDat.observe(viewLifecycleOwner){
                if (it.isNotEmpty()){
                    appViewModel.saveLoginData(requireActivity(),it[0].id)
                    Log.d("LoginFragment", "LoginUser: $it")
                    Toast.makeText(context, "Selamat Datang $nameField", Toast.LENGTH_SHORT).show()
                    navController.navigate(R.id.action_loginFragment_to_profileFragment)
                } else {
                    Toast.makeText(context, "Username Atau Password anda salah", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            when (navController.currentDestination?.id) {
                R.id.registerFragment -> {
                    navController.navigate(R.id.action_loginFragment_to_registerFragment)
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