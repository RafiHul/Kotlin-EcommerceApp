package com.myprojects.ecommerceapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.myprojects.ecommerceapp.activity.MainActivity

import com.myprojects.ecommerceapp.R
import com.myprojects.ecommerceapp.adapter.CartAdapter
import com.myprojects.ecommerceapp.database.CartDao
import com.myprojects.ecommerceapp.databinding.FragmentCartBinding
import com.myprojects.ecommerceapp.model.Cart
import com.myprojects.ecommerceapp.model.Item
import com.myprojects.ecommerceapp.model.User
import com.myprojects.ecommerceapp.viewmodel.AppViewModel
import com.myprojects.ecommerceapp.viewmodel.ProfileViewModel

class CartFragment : Fragment(R.layout.fragment_cart) {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private var _userData: User? = null
    private val userData get() = _userData!!

    private lateinit var appViewModel: AppViewModel
    private lateinit var cartAdapter: CartAdapter
    private lateinit var myNavController: NavController
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var currentList: CartDao.CartWithItemAndUser
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
        setUpViewModel()

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

    private fun setUpViewModel() {
        appViewModel = (activity as MainActivity).appViewModel
        cartAdapter = CartAdapter{
            currentList = it
            buyItemClick()
        }
        myNavController = findNavController()
        profileViewModel = (activity as MainActivity).profileViewModel
    }

    private fun buyItemClick() {
        if (userData.saldo < currentList.cart.totalPrice){
            Toast.makeText(context, "Saldo Anda Tidak Mencukupi", Toast.LENGTH_SHORT).show()
        } else {
            buyItem()
        }
    }

    private fun buyItem() {
        updateCartAndItem()
        appViewModel.updateSaldo(userData.id, (userData.saldo - currentList.cart.totalPrice).toInt())
        Toast.makeText(context, "Berhasil Membeli item", Toast.LENGTH_SHORT).show()
    }

    private fun updateCartAndItem() {
        val currentCart = currentList.cart
        val currentItem = currentList.item
        val totalItemQuantity = currentItem.quantity - currentCart.quantity
        val cart = Cart(currentCart.id, currentCart.itemId, 0, currentCart.totalPrice,currentCart.userId)
        val item = Item(currentItem.id,currentItem.nameItem,currentItem.price,currentItem.description,totalItemQuantity,currentItem.owner_id)

        appViewModel.deleteCart(cart)
        if (totalItemQuantity == 0){
            appViewModel.deleteItems(item)
        } else {
            appViewModel.updateItems(item)
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