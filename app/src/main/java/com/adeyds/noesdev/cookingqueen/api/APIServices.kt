package com.adeyds.noesdev.cookingqueen.api

import com.adeyds.noesdev.cookingqueen.ui.model.Resep
import com.adeyds.noesdev.cookingqueen.utils.Constants
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface APIServices {


    @GET("api/?")
    fun doGetRecipe(@Query("i") ingredients: String,
                    @Query("q") queryRecipe: String,
                    @Query("p") page: String): Observable<Resep.Resep>

    companion object {
        fun create(): APIServices {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Constants.BASE_URL)
                    .build()

            return retrofit.create(APIServices::class.java)
        }
    }

}