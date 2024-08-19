package com.myprojects.ecommerceapp.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.myprojects.ecommerceapp.MainActivity
import com.myprojects.ecommerceapp.databinding.FragmentDialogQuantityBinding
import com.myprojects.ecommerceapp.model.Cart
import com.myprojects.ecommerceapp.model.Item
import com.myprojects.ecommerceapp.model.User
import com.myprojects.ecommerceapp.viewmodel.AppViewModel
import com.myprojects.ecommerceapp.viewmodel.DialogQuantityViewModel
import com.myprojects.ecommerceapp.viewmodel.ProfileViewModel

class DialogQuantityFragment : DialogFragment() {
    private var _binding : FragmentDialogQuantityBinding? = null
    private val binding get() = _binding!!

    lateinit var dialogQuantityViewModel : DialogQuantityViewModel
    lateinit var currentItem : Item
    lateinit var appViewModel: AppViewModel
    lateinit var userid : LiveData<Int>
    lateinit var profileViewModel: ProfileViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDialogQuantityBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpArguments()
        setUpViewAndButton()
    }

    private fun setUpViewAndButton() {
        binding.textViewJumlah.text = currentItem.quantity.toString()
        binding.textViewId.text = id.toString()
        binding.buttonClose.setOnClickListener {
            dismiss()
        }
        binding.buttonBuy.setOnClickListener {
            buyButton()
        }
        inputQuantityListener()
    }

    private fun buyButton() {
        val userInputQuantity = binding.editTextInputQuantity.text.toString()
        val totalPrice = binding.textViewHarga.text.toString().toDouble()
        if (userInputQuantity.isNotEmpty()){
            appViewModel.insertCart(Cart(0,currentItem.id,userInputQuantity.toInt(),totalPrice,userid.value!!))
            Toast.makeText(context, "Item Di Taruh Di Keranjang", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputQuantityListener() {
        binding.editTextInputQuantity.addTextChangedListener {
            dialogQuantityViewModel.total.value = it.toString().toIntOrNull() ?: 0
        }
        dialogQuantityViewModel.total.observe(viewLifecycleOwner) {
            if (it > currentItem.quantity){
                binding.editTextInputQuantity.setText(currentItem.quantity.toString())
                binding.textViewHarga.text = (currentItem.quantity * currentItem.price).toString()
            } else {
                binding.textViewHarga.text = (it * currentItem.price).toString()
            }
        }
    }

    private fun setUpArguments() {
        dialogQuantityViewModel = ViewModelProvider(this).get(DialogQuantityViewModel::class.java)
        appViewModel = (activity as MainActivity).appViewModel
        profileViewModel = (activity as MainActivity).profileViewModel
        userid = profileViewModel.userLogId
        val args = arguments
        val itemDat: Item? = args?.getParcelable("ItemDat")
        currentItem = itemDat!!
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance(param:Item): DialogQuantityFragment {
            val fragment = DialogQuantityFragment()
            val args = Bundle()
            args.putParcelable("ItemDat",param)
            fragment.arguments = args
            return fragment
        }
    }
}