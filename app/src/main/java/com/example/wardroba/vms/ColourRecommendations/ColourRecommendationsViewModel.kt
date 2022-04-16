package com.example.wardroba.vms.ColourRecommendations

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wardroba.api.ColourMatches
import com.example.wardroba.api.ColourMatchesAPI
import com.example.wardroba.models.Colour
import kotlinx.coroutines.launch
import java.lang.Exception

class ColourRecommendationsViewModel(private val api: ColourMatchesAPI): ViewModel() {
    var colourMatches: ColourMatches? = null
    var colours: MutableList<Colour> = mutableListOf()
    var chosenColour: Colour? = null

    fun getMatchingColours(colourToMatch:Colour){
        viewModelScope.launch {
            try {
                val rgb = colourToMatch.r.toString() + "," + colourToMatch.g.toString() + "," + colourToMatch.b.toString()
                colourMatches = api.getMatches(rgb)
            } catch (e: Exception) {
                Log.d("ABC", "Error occured: ${e.message}")
            }
        }
    }

    fun colourConversion() {
        for (colour in colourMatches!!.colors) {
            colours.add(Colour(colour.rgb.r, colour.rgb.g, colour.rgb.b, colour.name.value))
        }
    }
}