package com.example.wardroba.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.wardroba.R
import com.example.wardroba.databinding.FragmentHomeBinding


class Home : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recommendBtn.setOnClickListener{
            val action = HomeDirections.actionHome2ToRecommendations()
            findNavController().navigate(action)
        }
        binding.wardrobeBtn.setOnClickListener {
            val action = HomeDirections.actionHome2ToMyWardrobe()
            findNavController().navigate(action)
        }

        binding.randomBtn.setOnClickListener{
            val action = HomeDirections.actionHome2ToRandomOutfit()
            findNavController().navigate(action)
        }

        binding.sharedBtn.setOnClickListener{

        }

    }



}