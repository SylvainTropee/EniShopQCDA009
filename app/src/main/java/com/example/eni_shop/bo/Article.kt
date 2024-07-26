package com.example.eni_shop.bo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import java.util.Date

@Entity
data class Article(
    @PrimaryKey
    var id : Long = 0,
    @Json(name = "title")
    var name : String = "",
    var description : String = "",
    var price : Double = 0.0,
    @Json(name = "image")
    var urlImage : String = "",
    var category : String = "",
    @Json(ignore = true)
    var date : Date = Date()
)
