package com.myprojects.ecommerceapp.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.myprojects.ecommerceapp.MainActivity
import com.myprojects.ecommerceapp.R
import com.myprojects.ecommerceapp.databinding.FragmentProfileBinding
import com.myprojects.ecommerceapp.model.User
import com.myprojects.ecommerceapp.viewmodel.ProfileViewModel
import com.myprojects.ecommerceapp.viewmodel.ProfileViewModelFactory
import com.myprojects.ecommerceapp.viewmodel.UserViewModel
import kotlinx.coroutines.launch
import kotlin.math.log

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding:FragmentProfileBinding? = null
    private val binding get() = _binding!!

    lateinit var profileViewModel: ProfileViewModel
    lateinit var userViewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel = (activity as MainActivity).userViewModel

        addItemButton()
        registerButton()

        val profileViewModelFactory = ProfileViewModelFactory(userViewModel,viewLifecycleOwner)
        profileViewModel = ViewModelProvider(this,profileViewModelFactory)
            .get(ProfileViewModel::class.java)

        isUserLogged()

        profileViewModel.userLogName.observe(viewLifecycleOwner){
            if (it.isNotBlank()){
                userViewModel.getUserByName(profileViewModel.userLogName.value.toString())?.observe(viewLifecycleOwner){ user ->
                    if (user != null) {
                        initProfileMainUI(user)
                    } else {
                        Log.w("Account Error","Gagal memuat akun")
                    }
                }
            } else {
                initProfileLogin()
            }
        }
    }

    private fun isUserLogged() {
        lifecycleScope.launch {
            userViewModel.getLoginData(requireContext()).collect{
                profileViewModel.setUserLogged(it.toString())
            }
        }
    }

    fun addItemButton(){
        binding.buttonProfileAddItem.setOnClickListener{
            it.findNavController().navigate(R.id.action_profileFragment_to_newItemFragment)
        }
    }

    fun registerButton(){
        binding.buttonLogin.setOnClickListener{
            it.findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }
    }

    private fun initProfileLogin() {
        binding.apply {
            imageViewProfilePic.visibility = View.GONE
            textViewProfileName.visibility = View.GONE
            textViewProfileName.visibility = View.GONE
            textViewProfileBalance.visibility = View.GONE
            buttonProfileAddItem.visibility = View.GONE
            buttonLogin.visibility = View.VISIBLE
        }
    }

    private fun initProfileMainUI(user:User){
        binding.apply {
            imageViewProfilePic.visibility = View.VISIBLE
            textViewProfileName.visibility = View.VISIBLE
            textViewProfileName.visibility = View.VISIBLE
            textViewProfileBalance.visibility = View.VISIBLE
            buttonProfileAddItem.visibility = View.VISIBLE
            buttonLogin.visibility = View.GONE
            textViewProfileName.text = user.username
            textViewProfileBalance.text = user.saldo.toString()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}