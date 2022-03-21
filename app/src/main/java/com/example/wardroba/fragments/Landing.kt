package com.example.wardroba.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.wardroba.R
import com.example.wardroba.databinding.FragmentHomeBinding
import com.example.wardroba.databinding.FragmentLandingBinding


class Landing : Fragment() {
    private var _binding: FragmentLandingBinding? = null
    private val binding get() = _binding!!




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLandingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.landingRcmndBtn.setOnClickListener{
            val action = LandingDirections.actionLandingToRecommendations()
            findNavController().navigate(action)
        }
    }
}