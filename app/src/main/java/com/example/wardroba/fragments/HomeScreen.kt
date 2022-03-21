package com.example.wardroba.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wardroba.databinding.FragmentMyWardrobeBinding

class HomeScreen : Fragment() {
    private var _binding: FragmentMyWardrobeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyWardrobeBinding.inflate(inflater, container, false)
        return binding.root
    }


}