package com.example.networkcats

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Breed(

    @Json(name="name")
    val name:String,

    @Json(name="bred_for")
    val bredFor:String,

    @Json(name="breed_group")
    val breedGroup:String,

    @Json(name="temperament")
    val temperament:String
    )