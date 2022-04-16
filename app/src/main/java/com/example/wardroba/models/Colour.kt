package com.example.wardroba.models

import android.graphics.Color
import android.graphics.Color.rgb

// not final
class Colour (val r: Int = 0, val g: Int = 0, val b: Int = 0, val name: String = ""){

    fun toInt(): Int {
        return rgb(r,g,b)
    }

}