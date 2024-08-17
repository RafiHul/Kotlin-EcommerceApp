package com.myprojects.ecommerceapp.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.myprojects.ecommerceapp.MainActivity
import com.myprojects.ecommerceapp.R
import com.myprojects.ecommerceapp.databinding.FragmentNewItemBinding
import com.myprojects.ecommerceapp.model.Item
import com.myprojects.ecommerceapp.model.User
import com.myprojects.ecommerceapp.viewmodel.ItemViewModel
import com.myprojects.ecommerceapp.viewmodel.UserViewModel

class NewItemFragment : Fragment(R.layout.fragment_new_item) {

    private var _binding: FragmentNewItemBinding? = null
    private val binding get() = _binding!!

    lateinit var itemViewModel: ItemViewModel
    lateinit var myView: View
    lateinit var userViewModel: UserViewModel
    var userid : Int = -1

    lateinit var navController : NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNewItemBinding.inflate(inflater,container,false)
        binding.buttonSave.setOnClickListener{
            saveItem()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemViewModel = (activity as MainActivity).itemViewModel
        userViewModel = (activity as MainActivity).userViewModel
        myView = view
        navController = findNavController()
        userid = userViewModel.getLoginData(requireContext()).toString().toInt()
    }

    private fun saveItem() {
        val judulItem = binding.editTextJudul.text.toString().trim()
        val hargaItem = binding.editTextHarga.text.toString().trim()

        if (judulItem.isEmpty() || hargaItem.isEmpty()){
            Toast.makeText(context, "TIdak Boleh Ada yang kosong", Toast.LENGTH_SHORT).show()
        } else {
            itemViewModel.insertItems(Item(0,judulItem,hargaItem.toDouble(),"test",userid))
            navController.navigate(R.id.action_newItemFragment_to_itemHomeFragment)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        requireActivity().onBackPressedDispatcher.addCallback{
            if (navController.currentDestination?.id == R.id.profileFragment){
                navController.navigate(R.id.action_newItemFragment_to_profileFragment)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}