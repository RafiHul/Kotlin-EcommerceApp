package com.myprojects.ecommerceapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.myprojects.ecommerceapp.MainActivity
import com.myprojects.ecommerceapp.R
import com.myprojects.ecommerceapp.databinding.FragmentTopUpBinding
import com.myprojects.ecommerceapp.model.User
import com.myprojects.ecommerceapp.viewmodel.TopUpSaldoViewModel
import com.myprojects.ecommerceapp.viewmodel.UserViewModel

class TopUpFragment : Fragment(R.layout.fragment_top_up) {
    private var _binding: FragmentTopUpBinding? = null
    private val binding get() = _binding!!

    lateinit var topUpSaldoViewModel: TopUpSaldoViewModel
    lateinit var userViewModel: UserViewModel
    lateinit var userData: User
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTopUpBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        topUpSaldoViewModel = ViewModelProvider(this).get(TopUpSaldoViewModel::class.java)
        userViewModel = (activity as MainActivity).userViewModel

        val namaUser = arguments?.getString("username")

        userViewModel.getUserByName(namaUser.toString())?.observe(viewLifecycleOwner){
            it?.let{
                topUpSaldoViewModel.setSaldoSaatIni(it.saldo)
                userData = it
            } ?: Toast.makeText(context, "Error Saat Load Akun Anda", Toast.LENGTH_SHORT).show()
        }

        initUi()
        topUpButton()
    }

    private fun topUpButton() {
        binding.buttonTopup.setOnClickListener {
            val nominal = binding.editTextNumberDecimalTopupSaldo.text.toString()
            if (nominal.isEmpty()){
                binding.editTextNumberDecimalTopupSaldo.error = "Nominal harus diisi"
            } else {
                userViewModel.updateUser(User(userData.id, userData.username, userData.password, userData.saldo + nominal.toInt()))
                Toast.makeText(context, "Berhasil Menambahkan Saldo", Toast.LENGTH_SHORT).show()
                it.findNavController().navigate(R.id.action_topUpFragment_to_profileFragment)
            }
        }
    }

    private fun initUi() {
        topUpSaldoViewModel.saldoSaatIni.observe(viewLifecycleOwner){
            binding.textViewSaldoUser.text = "Saldo Anda ${it}"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}