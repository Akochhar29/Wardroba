package com.example.wardroba.api

data class ColourMatches(
    val mode: String,
    val count: String,
    val colors: List<ColourData>
) {

    data class ColourData(
        val hex: HexData,
        val rgb: RgbData,
        val name: ColourName
    )

    data class HexData(
        val value: String,
        val clean: String
    )

    data class RgbData(
        val r: Int,
        val g: Int,
        val b: Int,
        val value: String
    )

    data class ColourName(
        val value: String,
        val closest_named_hex: String,
        val exact_match_name: Boolean,
        val distance: Int
    )
}