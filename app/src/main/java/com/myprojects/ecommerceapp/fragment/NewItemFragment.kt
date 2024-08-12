package com.myprojects.ecommerceapp.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.myprojects.ecommerceapp.R
import com.myprojects.ecommerceapp.databinding.FragmentNewItemBinding

class NewItemFragment : Fragment(R.layout.fragment_new_item) {

    private var _binding: FragmentNewItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNewItemBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        requireActivity().onBackPressedDispatcher.addCallback{
            val navController = findNavController()
            if (navController.currentDestination?.id == R.id.newItemFragment){
                navController.navigate(R.id.action_newItemFragment_to_profileFragment)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}