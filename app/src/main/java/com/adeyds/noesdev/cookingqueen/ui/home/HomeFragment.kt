package com.adeyds.noesdev.cookingqueen.ui.home


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.adeyds.noesdev.cookingqueen.R
import com.adeyds.noesdev.cookingqueen.R.color.colorAccent
import com.adeyds.noesdev.cookingqueen.ui.home.homeModel.MainAdapter
import com.adeyds.noesdev.cookingqueen.ui.home.homeModel.Resep
import kotlinx.android.synthetic.main.rview.*
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.*


class HomeFragment : Fragment(), HomeView.MainView, HomeView.View, MainAdapter.recipeClickListener {
    override fun getItem(position: Int) {
        toast("${resep.get(position).title}")
    }

//    private lateinit var listRecipe: RecyclerView
//    private lateinit var progressBar: ProgressBar
//    private lateinit var swipeRefresh: SwipeRefreshLayout
//    private lateinit var rootView: View
//      private lateinit var presenter: HomePresenter

    private lateinit var resep: List<Resep.ResultsItem>
    private var data: List<Resep> = mutableListOf()
    //  private lateinit var adapter: MainAdapter

    private lateinit var mPresenter: HomeView.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        HomePresenter(this, this)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        adapter = MainAdapter(context, resep) {
//            toast(it.title + " is from " + it.ingredients)
//            //startActivity<DetailActivity>(EXTRA_DETAIL to it)
//        }

        //adapter = MainAdapter(resep, this)
        // listRecipe.adapter = MainAdapter(resep, this)
//        callRecipe(resep)
        mPresenter.getRecipe("", "", "1")
        //   callRecipe()
        swp_layout.onRefresh {
            mPresenter.getRecipe("", "", "1")
        }
    }

    override fun setPresenter(presenter: HomeView.Presenter) {
        this.mPresenter = presenter
    }

    override fun showError(err: String) {
        toast("Error : $err")
    }

    override fun showLoading() {
  //      swp_layout.isRefreshing = true
    }

    override fun hideLoading() {
//        swp_layout.isRefreshing = false
    }

    override fun showTeamList(dataNew: List<Resep.ResultsItem>) {
        Log.e("showList HomeFragment", dataNew.toString() + " tot123123")
    //    swp_layout.isRefreshing = false
//        resep.clear()
//        resep = dataNew.toMutableList()

//        Log.e("cloneResep HomeFragment", resep.get(0).toString()+" tot123123")
        callRecipe(dataNew)
        //  adapter.notifyDataSetChanged()
    }

    fun callRecipe(data: List<Resep.ResultsItem>) {
        rview_layout.setHasFixedSize(true)
        rview_layout.layoutManager = LinearLayoutManager(context)
        rview_layout.adapter = MainAdapter(data, this)

    }


    //override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

    /* return UI {
         linearLayout {
             lparams(width = matchParent, height = matchParent)
             orientation = LinearLayout.VERTICAL
             topPadding = dip(16)
             leftPadding = dip(16)
             rightPadding = dip(16)


             swipeRefresh = swipeRefreshLayout {
                 setColorSchemeResources(colorAccent,
                         android.R.color.holo_green_light,
                         android.R.color.holo_orange_light,
                         android.R.color.holo_red_light)

                 relativeLayout {
                     lparams(width = matchParent, height = wrapContent)

                     listRecipe = recyclerView {
                         lparams(width = matchParent, height = wrapContent)
                         layoutManager = LinearLayoutManager(ctx)
                     }.lparams()

                     progressBar = progressBar {
                     }.lparams {
                         centerInParent()
                     }
                 }
             }
         }

     }.view}*/
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.rview, container, false)
    }


}
