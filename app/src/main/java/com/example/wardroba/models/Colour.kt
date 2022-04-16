package com.example.wardroba.models

import android.graphics.Color

// not final
class Colour (val r: Int = 0, val g: Int = 0, val b: Int = 0, val name: String = ""){
    val hex = String.format("#%02X%02X%02X", r, g, b)

    fun toInt(): Int {
        return Color.parseColor(hex)
    }

}