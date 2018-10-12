package com.adeyds.noesdev.cookingqueen.ui.home

import android.util.Log
import com.adeyds.noesdev.cookingqueen.api.APIServices
import com.adeyds.noesdev.cookingqueen.ui.home.homeModel.Resep
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class HomePresenter(private val view: HomeView.View, private val viewImp: HomeView.MainView) : HomeView.Presenter {

    val apiService by lazy {
        //initializing Retrofit stuff
        APIServices.create()
    }
    //Rx Java object that tracks fetching activity
    var disposable: Disposable? = null


    init {
        this.view.setPresenter(this)
    }

    override fun start() {
        Log.e("on start presenter", " tot123123")
    }

    override fun getRecipe(ingredients: String, query: String, page: String) {
        disposable =
                apiService.doGetRecipe(ingredients, query, page)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { result -> showResult(result) },
                                { error ->
                                    error.printStackTrace()
                                    showError(error.message.toString())
                                }
                        )
    }

    fun showResult(result: Resep.Resep) {
        viewImp.showLoading()
        if (!result.title.isNullOrEmpty()) {
//            Log.e("showRes HomePresenter1", result.toString()+" tot123123")
//            Log.e("showRes HomePresenter2", "${result.results} tot123123")
            viewImp.showTeamList(result.results as List<Resep.ResultsItem>)
        } else {
            showError("data empty")
        }
        viewImp.hideLoading()
    }

    fun showError(error: String) {
        viewImp.showError(error)
    }
}