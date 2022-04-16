package com.example.wardroba.fragments

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wardroba.R
import com.example.wardroba.databinding.FragmentColourRecommendationsBinding
import com.example.wardroba.databinding.FragmentHomeBinding

class ColourRecommendations() : Fragment(), Parcelable {
    private var _binding: FragmentColourRecommendationsBinding? = null
    private val binding get() = _binding!!

    constructor(parcel: Parcel) : this() {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ColourRecommendations> {
        override fun createFromParcel(parcel: Parcel): ColourRecommendations {
            return ColourRecommendations(parcel)
        }

        override fun newArray(size: Int): Array<ColourRecommendations?> {
            return arrayOfNulls(size)
        }
    }
}