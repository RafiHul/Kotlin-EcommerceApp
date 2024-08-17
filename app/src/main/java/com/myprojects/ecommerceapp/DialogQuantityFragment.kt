package com.myprojects.ecommerceapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.myprojects.ecommerceapp.databinding.FragmentDialogQuantityBinding

class DialogQuantityFragment : DialogFragment() {
    private var _binding : FragmentDialogQuantityBinding? = null
    private val binding get() = _binding!!

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
        val args = arguments
        val quantity = args?.getInt("Quantity")
        binding.textView.text = quantity.toString()
        binding.buttonClose.setOnClickListener {
            dismiss()
        }
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
        fun newInstance(param:Int): DialogQuantityFragment {
            val fragment = DialogQuantityFragment()
            val args = Bundle()
            args.putInt("Quantity",param)
            fragment.arguments = args
            return fragment
        }
    }
}