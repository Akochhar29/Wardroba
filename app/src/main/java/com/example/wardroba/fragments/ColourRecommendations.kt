package com.example.wardroba.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.wardroba.R
import com.example.wardroba.api.RetrofitInstance
import com.example.wardroba.databinding.FragmentColourRecommendationsBinding
import com.example.wardroba.models.Colour
import com.example.wardroba.vms.ColourRecommendations.ColourRecommendationsViewModel
import com.example.wardroba.vms.ColourRecommendations.ColourRecommendationsViewModelFactory
import com.example.wardroba.vms.UserViewModel

class ColourRecommendations() : Fragment() {
    private var _binding: FragmentColourRecommendationsBinding? = null
    private val binding get() = _binding!!
    private var colourShowcaseButtons = listOf<Button>()

    val args:ColourRecommendationsArgs by navArgs()
    private val clothingTypes = listOf<String>("Pants", "Top", "Skirt", "Hat")
    private val userModel: UserViewModel by viewModels()

    private val model: ColourRecommendationsViewModel by viewModels {
        ColourRecommendationsViewModelFactory(RetrofitInstance.retrofitService)
    }

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val c: Color = Color.valueOf(args.clothingColour)
        model.getMatchingColours(Colour((c.red() * 255).toInt() ,(c.green() * 255).toInt(),(c.blue() * 255).toInt()))

        val haveColourObserver = Observer<Boolean> { _ ->
            showcaseColours(model.coloursList.value!!.toList())
        }
        model.haveColours.observe(this, haveColourObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNotAButton.visibility = View.INVISIBLE
        colourShowcaseButtons = listOf(binding.btnColour1, binding.btnColour2, binding.btnColour3, binding.btnColour4, binding.btnColour5)

        for (i in colourShowcaseButtons.indices) {
            colourShowcaseButtons[i].visibility = View.INVISIBLE
            colourShowcaseButtons[i].setOnClickListener {
                if (model.coloursList.value!!.size > i) {
                    updateSelectedColour(model.coloursList.value!![i])
                }
            }
        }

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

        binding.btnDiscard.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSave.setOnClickListener{
            if(userModel.auth.currentUser == null){
                val action = ColourRecommendationsDirections.actionColourRecommendationsToSignIn()
                findNavController().navigate(action)
            }
            else{
                val action = ColourRecommendationsDirections.actionColourRecommendationsToAddAccessory(args.clothingColour)
                findNavController().navigate(action)
            }
        }
    }

    private fun showcaseColours(colourList: List<Colour>) {
        for (i in colourList.indices) {
            if (i < colourShowcaseButtons.size) {
                colourShowcaseButtons[i].setBackgroundColor(colourList[i].toInt())
                colourShowcaseButtons[i].visibility = View.VISIBLE
            } else {
                colourShowcaseButtons[i].visibility = View.INVISIBLE
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun updateSelectedColour(colour: Colour) {
        binding.tvColourName.text = colour.name
        binding.tvValues.text =
            "(" + colour.r.toString() + ", " + colour.g.toString() + ", " + colour.b.toString() + ")"
        binding.btnNotAButton.setBackgroundColor(colour.toInt())
        binding.btnNotAButton.visibility = View.VISIBLE
        model.chosenColour = colour
    }

}