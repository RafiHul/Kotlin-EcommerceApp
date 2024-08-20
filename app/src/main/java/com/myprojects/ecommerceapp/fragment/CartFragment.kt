package com.myprojects.ecommerceapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.myprojects.ecommerceapp.MainActivity

import com.myprojects.ecommerceapp.R
import com.myprojects.ecommerceapp.adapter.CartAdapter
import com.myprojects.ecommerceapp.database.CartDao
import com.myprojects.ecommerceapp.databinding.FragmentCartBinding
import com.myprojects.ecommerceapp.model.User
import com.myprojects.ecommerceapp.viewmodel.AppViewModel
import com.myprojects.ecommerceapp.viewmodel.ProfileViewModel
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch

class CartFragment : Fragment(R.layout.fragment_cart) {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private var _userData: User? = null
    private val userData get() = _userData!!

    private lateinit var appViewModel: AppViewModel
    private lateinit var cartAdapter: CartAdapter
    private lateinit var myNavController: NavController
    private lateinit var profileViewModel: ProfileViewModel
    private var userid : Int = -1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appViewModel = (activity as MainActivity).appViewModel
        cartAdapter = CartAdapter{
            buyItemClick(it)
        }
        myNavController = findNavController()
        profileViewModel = (activity as MainActivity).profileViewModel
        profileViewModel.userLogId.value?.let {
            userid = it
            appViewModel.getUserById(it)?.observe(viewLifecycleOwner){ user ->
                _userData = user
            }
        } ?: {
            binding.recyclerViewCart.visibility = View.GONE
            Toast.makeText(context, "Anda Harus Login Terlebih Dahulu", Toast.LENGTH_SHORT).show()
        }

        if (userid == -1){
            binding.recyclerViewCart.visibility = View.GONE
            Toast.makeText(context, "Anda Harus Login Terlebih Dahulu", Toast.LENGTH_SHORT).show()
        } else {
            setUpRecyclerView()
        }
    }

    private fun buyItemClick(currentList: CartDao.CartWithItemAndUser) {
        if (userData.saldo < currentList.cart.totalPrice){
            Toast.makeText(context, "Saldo Anda Tidak Mencukupi", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Berhasil Membeli item", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpRecyclerView() {
        binding.recyclerViewCart.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cartAdapter
            visibility = View.VISIBLE
        }
        binding.textViewKosong.visibility = View.GONE
        activity.let {
            appViewModel.getCartUser(userid).observe(viewLifecycleOwner){
                if (it.isEmpty()){
                    Toast.makeText(context, "null masuk sini", Toast.LENGTH_SHORT).show()
                    binding.recyclerViewCart.visibility = View.GONE
                    binding.textViewKosong.visibility = View.VISIBLE
                } else {
                    cartAdapter.differ.submitList(it)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        _userData = null
    }
}