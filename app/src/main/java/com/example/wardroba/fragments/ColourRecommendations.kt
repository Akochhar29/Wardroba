package com.example.wardroba.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.wardroba.R
import com.example.wardroba.api.RetrofitInstance
import com.example.wardroba.databinding.FragmentColourRecommendationsBinding
import com.example.wardroba.databinding.FragmentHomeBinding
import com.example.wardroba.databinding.FragmentRecommendationsBinding
import com.example.wardroba.models.Colour
import com.example.wardroba.vms.ColourRecommendations.ColourRecommendationsViewModel
import com.example.wardroba.vms.ColourRecommendations.ColourRecommendationsViewModelFactory

class ColourRecommendations() : Fragment() {
    private var _binding: FragmentColourRecommendationsBinding? = null
    private val binding get() = _binding!!
    private var colourShowcaseButtons = listOf<Button>()

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
        model.getMatchingColours(Colour(255,0,0))

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

        binding.btnDiscard.setOnClickListener {
            findNavController().popBackStack()
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