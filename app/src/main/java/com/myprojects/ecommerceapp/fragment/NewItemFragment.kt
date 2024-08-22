package com.myprojects.ecommerceapp.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.myprojects.ecommerceapp.activity.MainActivity
import com.myprojects.ecommerceapp.R
import com.myprojects.ecommerceapp.databinding.FragmentNewItemBinding
import com.myprojects.ecommerceapp.model.Item
import com.myprojects.ecommerceapp.viewmodel.AppViewModel
import com.myprojects.ecommerceapp.viewmodel.ProfileViewModel

class NewItemFragment : Fragment(R.layout.fragment_new_item) {

    private var _binding: FragmentNewItemBinding? = null
    private val binding get() = _binding!!

    val appViewModel: AppViewModel by activityViewModels()
    val profileViewModel: ProfileViewModel by activityViewModels()
    lateinit var navController : NavController
    var userid : Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNewItemBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        userid = profileViewModel.userLogId.value!!

        setupButton()
    }

    private fun setupButton() {
        binding.buttonSave.setOnClickListener{
            saveItem()
        }
    }


    private fun saveItem() {
        val judulItem = binding.editTextJudul.text.toString().trim()
        val hargaItem = binding.editTextHarga.text.toString().trim()
        val jumlahitem = binding.editTextJumlah.text.toString().toInt()

        if (judulItem.isEmpty() || hargaItem.isEmpty()){
            Toast.makeText(context, "TIdak Boleh Ada yang kosong", Toast.LENGTH_SHORT).show()
        } else {
            appViewModel.insertItems(Item(0,judulItem,hargaItem.toDouble(),"test",jumlahitem,userid))
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