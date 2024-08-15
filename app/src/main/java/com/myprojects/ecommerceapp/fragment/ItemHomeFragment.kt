package com.myprojects.ecommerceapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.myprojects.ecommerceapp.MainActivity
import com.myprojects.ecommerceapp.R
import com.myprojects.ecommerceapp.adapter.ItemAdapter
import com.myprojects.ecommerceapp.databinding.FragmentItemHomeBinding
import com.myprojects.ecommerceapp.viewmodel.ItemViewModel


class ItemHomeFragment : Fragment(R.layout.fragment_item_home) {

    private var _binding: FragmentItemHomeBinding? = null
    private val binding get() = _binding!!

    lateinit var itemAdapter: ItemAdapter
    lateinit var itemsViewModel: ItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentItemHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemAdapter = ItemAdapter()
        itemsViewModel = (activity as MainActivity).itemViewModel
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            adapter = itemAdapter
        }
        activity.let {
            itemsViewModel.getAllItems().observe(
                viewLifecycleOwner, {
                    itemAdapter.differ.submitList(it)
                }
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}