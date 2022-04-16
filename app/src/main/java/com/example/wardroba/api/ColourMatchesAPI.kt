package com.example.wardroba.api

import retrofit2.http.*

interface ColourMatchesAPI {
    @GET("/scheme?")
    suspend fun getMatches(@Query("rgb") rgb: String, @Query("mode") mode: String = "quad"):ColourMatches

}