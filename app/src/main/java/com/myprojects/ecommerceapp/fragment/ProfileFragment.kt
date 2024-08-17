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
import com.myprojects.ecommerceapp.clearLoginInfo
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

        setupButtons()

        val profileViewModelFactory = ProfileViewModelFactory(userViewModel,viewLifecycleOwner)
        profileViewModel = ViewModelProvider(this,profileViewModelFactory)
            .get(ProfileViewModel::class.java)

        isUserLogged()

        profileViewModel.userLogId.observe(viewLifecycleOwner){
            if (it != -1){

                binding.progressBar.visibility = View.VISIBLE
                userViewModel.getUserById(profileViewModel.userLogId.value!!)?.observe(viewLifecycleOwner){ user ->
                    user?.let {
                        initProfileMainUI(user)
                    } ?: Toast.makeText(context, "Account Error", Toast.LENGTH_SHORT).show()
                }
            } else {
                initProfileLogin()
            }
        }
    }

    private fun isUserLogged() {
        lifecycleScope.launch {
            userViewModel.getLoginData(requireContext()).collect{
                profileViewModel.setUserLogged(it)
            }
        }
    }

    fun navigateTo(destinationId: Int) {
        binding.root.findNavController().navigate(destinationId)
    }

    fun setupButtons() {
        binding.buttonProfileAddItem.setOnClickListener {
            navigateTo(R.id.action_profileFragment_to_newItemFragment)
        }
        binding.buttonLogin.setOnClickListener {
            navigateTo(R.id.action_profileFragment_to_loginFragment)
        }
        binding.buttonTopupSaldo.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("id",profileViewModel.userLogId.value!!) //kalo ada error pas topup
            }
            it.findNavController().navigate(R.id.action_profileFragment_to_topUpFragment,bundle)
        }
        binding.buttonLogout.setOnClickListener {
            lifecycleScope.launch {
                clearLoginInfo(requireContext())
                Toast.makeText(context, "Anda Berhasil Logout", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initProfileLogin() {
        binding.apply {
            imageViewProfilePic.visibility = View.GONE
            textViewProfileName.visibility = View.GONE
            textViewProfileName.visibility = View.GONE
            textViewProfileBalance.visibility = View.GONE
            buttonProfileAddItem.visibility = View.GONE
            buttonTopupSaldo.visibility = View.GONE
            buttonLogout.visibility = View.GONE
            buttonLogin.visibility = View.VISIBLE
        }
    }

    private fun initProfileMainUI(user:User){
        binding.apply {
            textViewProfileName.text = user.username
            textViewProfileBalance.text = user.saldo.toString()
            imageViewProfilePic.visibility = View.VISIBLE
            textViewProfileName.visibility = View.VISIBLE
            textViewProfileName.visibility = View.VISIBLE
            textViewProfileBalance.visibility = View.VISIBLE
            buttonProfileAddItem.visibility = View.VISIBLE
            buttonTopupSaldo.visibility = View.VISIBLE
            buttonLogout.visibility = View.VISIBLE
            buttonLogin.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}