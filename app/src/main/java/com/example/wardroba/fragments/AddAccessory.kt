package com.example.wardroba.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.wardroba.R
import com.example.wardroba.databinding.FragmentAddAccessoryBinding
import com.example.wardroba.models.Accessory
import com.example.wardroba.vms.UserViewModel
import kotlinx.coroutines.coroutineScope


class AddAccessory : Fragment() {
    private var _binding: FragmentAddAccessoryBinding? = null
    private val binding get() = _binding!!
    val args:ColourRecommendationsArgs by navArgs()
    private val userModel: UserViewModel by activityViewModels<UserViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddAccessoryBinding.inflate(inflater,container,false)
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
        binding.btnAdd.setOnClickListener {
            userModel.storeAccessory(Accessory(args.clothingColour,binding.spnAccessoryType.selectedItem.toString(),getSeasons()))
            val action = AddAccessoryDirections.actionAddAccessoryToHome2()
            findNavController().navigate(action)
        }
        return view
    }
    private fun getSeasons():List<String>{
        val seasons: MutableList<String> = mutableListOf()
        if(binding.cbSummer.isChecked){
            seasons.add("Summer")
        }
        if(binding.cbTransitional.isChecked){
            seasons.add("Transitional")
        }
        if(binding.cbWinter.isChecked){
            seasons.add("Winter")
        }
        return seasons
    }
}