package com.myprojects.ecommerceapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.myprojects.ecommerceapp.MainActivity
import com.myprojects.ecommerceapp.R
import com.myprojects.ecommerceapp.databinding.FragmentShowItemBinding
import com.myprojects.ecommerceapp.model.Item
import com.myprojects.ecommerceapp.model.User
import com.myprojects.ecommerceapp.viewmodel.AppViewModel
import com.myprojects.ecommerceapp.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch

class ShowItemFragment : Fragment(R.layout.fragment_show_item) {

    private var _binding : FragmentShowItemBinding? = null
    private val binding get() = _binding!!
    private val args: ShowItemFragmentArgs by navArgs()

    lateinit var currentItem: Item
    lateinit var appViewModel: AppViewModel
    lateinit var profileViewModel: ProfileViewModel
    var userid : Int? = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentShowItemBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appViewModel = (activity as MainActivity).appViewModel
        profileViewModel = (activity as MainActivity).profileViewModel
        currentItem = args.item!!

        userid = profileViewModel.userLogId.value

        if (userid == currentItem.owner_id){
            initViews("Delete")
        } else {
            initViews("Buy")
        }
    }

    private fun initViews(key: String) {
        when (key){
            "Delete" -> {
                binding.textViewId.text = currentItem.id.toString()
                binding.textViewHarga.text = currentItem.price.toString()
                binding.textViewJudul.text = currentItem.nameItem
                binding.buttonHapus.visibility = View.VISIBLE
                binding.buttonBeli.visibility = View.GONE
                binding.buttonHapus.setOnClickListener {
                    deleteItem()
                }
            }
            "Buy" -> {
                binding.textViewId.text = currentItem.id.toString()
                binding.textViewHarga.text = currentItem.price.toString()
                binding.textViewJudul.text = currentItem.nameItem
                binding.buttonHapus.visibility = View.GONE
                binding.buttonBeli.visibility = View.VISIBLE
                binding.buttonBeli.setOnClickListener {
                    if (userid == null || userid == -1){
                        Toast.makeText(context, "Harap Login Terlebih Dahulu", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun deleteItem(){
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Hapus Item ini")
            setMessage("Kamu yakin ingin menghapus item ini ?")
            setPositiveButton("Hapus"){_,_ ->
                appViewModel.deleteItems(currentItem)
                view?.findNavController()?.navigate(R.id.action_showItemFragment_to_itemHomeFragment)
            }
            setNegativeButton("TIdak",null)
        }.create().show()
    }
}