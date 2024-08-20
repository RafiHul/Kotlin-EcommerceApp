package com.myprojects.ecommerceapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.myprojects.ecommerceapp.database.CartDao
import com.myprojects.ecommerceapp.databinding.CartViewBinding
import com.myprojects.ecommerceapp.model.Cart

class CartAdapter(private val clickListener : (CartDao.CartWithItemAndUser) -> Unit):RecyclerView.Adapter<CartAdapter.MyViewHolder>() {

    inner class MyViewHolder(val cartBinding:CartViewBinding):RecyclerView.ViewHolder(cartBinding.root){

    }

    private val differCallback = object : DiffUtil.ItemCallback<CartDao.CartWithItemAndUser>(){
        override fun areItemsTheSame(oldItem: CartDao.CartWithItemAndUser, newItem: CartDao.CartWithItemAndUser): Boolean {
            return oldItem.cart.id == newItem.cart.id && oldItem.item.id == newItem.item.id
        }

        override fun areContentsTheSame(oldItem: CartDao.CartWithItemAndUser, newItem: CartDao.CartWithItemAndUser): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(CartViewBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        ))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentList = differ.currentList[position]
        holder.cartBinding.apply {
            textViewId.text = currentList.cart.itemId.toString()
            textViewNamaItem.text = currentList.item.nameItem
            textViewHargaTotal.text = currentList.cart.totalPrice.toString()
            textViewJumlahBeli.text = currentList.cart.quantity.toString()
            buttonBeli.setOnClickListener {
                clickListener(currentList)
            }
        }
    }
}