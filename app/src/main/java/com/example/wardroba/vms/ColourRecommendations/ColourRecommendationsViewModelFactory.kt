package com.example.wardroba.vms.ColourRecommendations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wardroba.api.ColourMatchesAPI

class ColourRecommendationsViewModelFactory (private val api: ColourMatchesAPI): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ColourRecommendationsViewModel::class.java)) {
            return ColourRecommendationsViewModel(api) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}