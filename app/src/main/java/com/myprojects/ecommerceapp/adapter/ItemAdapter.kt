package com.myprojects.ecommerceapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.myprojects.ecommerceapp.databinding.ItemViewBinding
import com.myprojects.ecommerceapp.model.Item

class ItemAdapter():RecyclerView.Adapter<ItemAdapter.MyViewHolder>() {
    inner class MyViewHolder(val itemBinding: ItemViewBinding):RecyclerView.ViewHolder(itemBinding.root) {

    }

    private val differCallback = object : DiffUtil.ItemCallback<Item>(){
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.nameItem == newItem.nameItem &&
                    oldItem.price == newItem.price &&
                    oldItem.description == newItem.description
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return newItem == oldItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemViewBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        ))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = differ.currentList[position]

        holder.itemBinding.textViewId.text = currentItem.id.toString()
        holder.itemBinding.textViewName.text = currentItem.nameItem
        holder.itemBinding.textViewPrice.text = currentItem.price.toString()
        holder.itemBinding.textViewPrice.text = currentItem.description
    }
}