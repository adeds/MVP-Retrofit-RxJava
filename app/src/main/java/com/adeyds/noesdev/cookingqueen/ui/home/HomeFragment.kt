package com.adeyds.noesdev.cookingqueen.ui.home


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.adeyds.noesdev.cookingqueen.R
import com.adeyds.noesdev.cookingqueen.ui.detail.DetailActivity
import com.adeyds.noesdev.cookingqueen.ui.model.Resep
import com.adeyds.noesdev.cookingqueen.utils.Constants.Companion.EXTRA_DETAIL
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.rview.*
import org.jetbrains.anko.support.v4.*


class HomeFragment :
        Fragment(),
        HomeView.MainView,
        HomeView.View,
        HomeAdapter.recipeClickListener,
        HomeAdapter.recipePageListener {


    private lateinit var resep: List<Resep.ResultsItem>
    private lateinit var mPresenter: HomeView.Presenter
    private var pageNumber: Int = 1
    private var queryRec: String = ""
    private var queryIng: String = ""
    var isLayoutAdvance: Boolean = false
    var isSearchBarHide: Boolean = false
    lateinit var layoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        HomePresenter(this, this)
//        setRetainInstance(true);

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val extras = Bundle()
        extras.putString("max_ad_content_rating", "G")
        val request = AdRequest.Builder()
                .addNetworkExtrasBundle(AdMobAdapter::class.java, extras)
                .build()
        adView.loadAd(request)
        adView2.loadAd(request)

        mPresenter.getRecipe("", "", "1")

        swp_layout.onRefresh {
            mPresenter.getRecipe(queryIng, queryRec, pageNumber.toString())
        }

        scrollViewNested.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY < oldScrollY) { // up
                animateSearchBar(false)
            }
            if (scrollY > oldScrollY) { // down
                animateSearchBar(true)
            }
        })

        btn_advance_search.setOnClickListener({ btn_advance_search ->
            getQueryRecipe()
        })

        edt_search.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                getQueryRecipe()
                true
            }

            false
        }


    }

    private fun getQueryRecipe() {
            queryRec = edt_search.text.toString()
            queryIng = edt_search.text.toString()

        pageNumber = 1
        mPresenter.getRecipe(queryIng, queryRec, pageNumber.toString())
    }



    private fun animateSearchBar(hide: Boolean) {
        if (isSearchBarHide && hide || !isSearchBarHide && !hide) return
        isSearchBarHide = hide
        val moveY = if (hide) -(2 * csbar.height) else 0
        csbar.animate().translationY(moveY.toFloat()).setStartDelay(100).setDuration(300).start()
    }

    override fun getItem(position: Int) {
//        toast("${resep.get(position).title}")
        startActivity<DetailActivity>(EXTRA_DETAIL to resep.get(position))
    }

    override fun page(isNext: Boolean) {
        if (isNext) pageNumber++ else pageNumber--
        if (pageNumber == 0) pageNumber = 1
        mPresenter.getRecipe(queryIng, queryRec, pageNumber.toString())
    }


    override fun setPresenter(presenter: HomeView.Presenter) {
        this.mPresenter = presenter
    }

    override fun showError(err: String) {
        longToast("Error : $err")
        rview_layout.visibility = View.INVISIBLE
        no_inet.visibility = View.VISIBLE    }

fun NoData(err: String){
    longToast("Error : $err")
    rview_layout.visibility = View.INVISIBLE
    no_data.visibility = View.VISIBLE
}

    fun findData(){
        no_inet.visibility = View.GONE
        no_data.visibility = View.GONE
        rview_layout.visibility = View.VISIBLE

    }
    override fun showLoading() {
        pbar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        pbar.visibility = View.GONE
    }

    override fun showTeamList(dataNew: List<Resep.ResultsItem>) {
        swp_layout.isRefreshing = false
        resep = dataNew
        if (resep.isEmpty()) NoData("No data found")
        else callRecipe(dataNew)
    }

    fun callRecipe(data: List<Resep.ResultsItem>) {
        findData()
            rview_layout.layoutManager = LinearLayoutManager(context)
            rview_layout.adapter = HomeAdapter(data, this, this, pageNumber)
            scrollViewNested.scrollTo(0, 0)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.rview, container, false)
    }


}


