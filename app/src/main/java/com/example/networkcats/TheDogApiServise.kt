package com.example.networkcats

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

private const val API_KEY ="45ce11a0-a93e-4b3a-9842-093441f7be32"
interface  TheDogApiServise {
    @GET("images/search")
    fun getDogs(@Header("x-api-key") apiKey: String = API_KEY):
            Call<List<DogResponse>>
}