package com.example.wardroba.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wardroba.R
import com.example.wardroba.databinding.FragmentMyWardrobeBinding
import com.example.wardroba.databinding.FragmentRandomOutfitBinding


class RandomOutfit : Fragment() {
    private var _binding: FragmentRandomOutfitBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRandomOutfitBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }
}