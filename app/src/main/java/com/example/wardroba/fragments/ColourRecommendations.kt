package com.example.wardroba.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import androidx.navigation.fragment.navArgs
import com.example.wardroba.R
import com.example.wardroba.databinding.FragmentColourRecommendationsBinding
import com.example.wardroba.databinding.FragmentHomeBinding

class ColourRecommendations : Fragment() {
    private var _binding: FragmentColourRecommendationsBinding? = null
    private val binding get() = _binding!!
    private val clothingTypes = listOf<String>("Pants", "Top", "Skirt", "Hat")
    val args:ColourRecommendationsArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentColourRecommendationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnMainColour.setBackgroundColor(args.clothingColour)

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.accessory_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.spnClothingType.adapter = adapter
        }


    }
}