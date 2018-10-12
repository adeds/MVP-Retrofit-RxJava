package com.adeyds.noesdev.cookingqueen.ui.home

import com.adeyds.noesdev.cookingqueen.base.BasePresenter
import com.adeyds.noesdev.cookingqueen.base.BaseView
import com.adeyds.noesdev.cookingqueen.ui.home.homeModel.Resep

interface HomeView {

    interface View : BaseView<Presenter>

    interface Presenter : BasePresenter {
        fun getRecipe(ingredients: String, query: String, page: String) {

        }
    }

    interface MainView {
        fun showError(text : String)
        fun showLoading()
        fun hideLoading()
        fun showTeamList(data: List<Resep.ResultsItem>)
    }

}