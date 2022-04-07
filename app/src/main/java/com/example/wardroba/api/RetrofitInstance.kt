package com.example.wardroba.api

import com.squareup.okhttp.OkHttpClient

/*object RetrofitInstance {

    // When using the emulator, localhost = 10.0.2.2
    private const val BASE_URL:String = "https://jsonplaceholder.typicode.com" // https://sightengine.com/docs/color-detection#use-model

    // setup a client with logging
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor(
            HttpLoggingInterceptor.Logger { message ->
                println("LOG-APP: $message")
            }).apply {
            level= HttpLoggingInterceptor.Level.BODY
        })
        .build()

    // used to ensure Moshi annotations work with Kotlin
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    // instantiate a Retrofit instance with Moshi as the data converter
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .client(httpClient)
        .build()

    val retrofitService: GameAPI by lazy {
        retrofit.create(GameAPI::class.java)
    }

}*/