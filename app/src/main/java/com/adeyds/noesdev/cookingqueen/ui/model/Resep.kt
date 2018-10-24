package com.adeyds.noesdev.cookingqueen.ui.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


object Resep {

    data class Resep(

            val href: String,
            val title: String,
            val version: Double,
            val results: List<ResultsItem?>
    )

    @Parcelize
    data class ResultsItem(

            val thumbnail: String,
            val ingredients: String,
            val href: String,
            val title: String
    ) : Parcelable

}