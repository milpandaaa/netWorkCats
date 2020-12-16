package com.example.networkcats

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class DogResponse(
    @Json(name="breeds")
    val breeds:List<Breed>,

    @Json(name="id")
    val id:String,

    @Json(name="url")
    val url:String,

    @Json(name="width")
    val width:Int,

    @Json(name="height")
    val height:Int

){

}