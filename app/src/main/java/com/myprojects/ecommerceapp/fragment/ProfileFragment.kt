package com.myprojects.ecommerceapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.myprojects.ecommerceapp.R
import com.myprojects.ecommerceapp.databinding.FragmentProfileBinding
import com.myprojects.ecommerceapp.viewmodel.ProfileViewModel
import com.myprojects.ecommerceapp.viewmodel.ProfileViewModelFactory

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding:FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater,container,false)

        val profileViewModelFactory = ProfileViewModelFactory(binding)

        val profileViewModel = ViewModelProvider(this,profileViewModelFactory)
            .get(ProfileViewModel::class.java)

        profileViewModel.addItemButton()
        profileViewModel.registerButton()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}