package com.example.wardroba.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.wardroba.R
import com.example.wardroba.databinding.FragmentMyWardrobeBinding


class MyWardrobe : Fragment() {
    private var _binding: FragmentMyWardrobeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyWardrobeBinding.inflate(inflater,container,false)
        val view = binding.root
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.accessory_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.spnAccessoryType.adapter = adapter
        }
        return view
    }
}