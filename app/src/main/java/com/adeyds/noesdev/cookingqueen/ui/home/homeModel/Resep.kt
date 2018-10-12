package com.adeyds.noesdev.cookingqueen.ui.home.homeModel


import com.google.gson.annotations.SerializedName


object Resep {

    data class Resep(

//            @field:SerializedName("href")
            val href: String,

//            @field:SerializedName("title")
            val title: String,

//            @field:SerializedName("version")
            val version: Double,

//            @field:SerializedName("results")
            val results: List<ResultsItem?>
    )

    data class ResultsItem(

//            @field:SerializedName("thumbnail")
            val thumbnail: String,

//            @field:SerializedName("ingredients")
            val ingredients: String,

//            @field:SerializedName("href")
            val href: String,

//            @field:SerializedName("title")
            val title: String
    )

}