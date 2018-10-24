package com.adeyds.noesdev.cookingqueen.ui.favorite


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.adeyds.noesdev.cookingqueen.R
import com.adeyds.noesdev.cookingqueen.db.FavoriteRecipe
import com.adeyds.noesdev.cookingqueen.db.database
import com.adeyds.noesdev.cookingqueen.ui.detail.DetailActivity
import com.adeyds.noesdev.cookingqueen.ui.model.Resep
import com.adeyds.noesdev.cookingqueen.utils.Constants.Companion.EXTRA_DETAIL
import com.adeyds.noesdev.cookingqueen.utils.Constants.Companion.REQUEST_FAV
import com.adeyds.noesdev.jadwaltandingbola.main.FavAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.*


class FavoritesFragment : Fragment() {

    private var favorites: MutableList<Resep.ResultsItem> = mutableListOf()
    private lateinit var adapter: FavAdapter
    private lateinit var listFav: RecyclerView
    private lateinit var noFeed: ImageView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = FavAdapter(context, favorites) {
            //toast(it.homeTeam + " vs " + it.awayTeam)
//            startActivity<DetailActivity>(Constants.EXTRA_DETAIL to it)
            startActivityForResult<DetailActivity>(REQUEST_FAV, EXTRA_DETAIL to it)
        }
        listFav.adapter = adapter
        showFavorite()
        swipeRefresh.onRefresh {
            favorites.clear()
            showFavorite()
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return UI {
            linearLayout {
                lparams(width = matchParent, height = matchParent)
                orientation = LinearLayout.VERTICAL
                topPadding = dip(16)
                leftPadding = dip(16)
                rightPadding = dip(16)


                swipeRefresh = swipeRefreshLayout {
                    setColorSchemeResources(R.color.colorAccent,
                            android.R.color.holo_green_light,
                            android.R.color.holo_orange_light,
                            android.R.color.holo_red_light)

                    relativeLayout {
                        lparams(width = matchParent, height = matchParent)
                        noFeed = imageView {
                            setImageResource(R.drawable.no_feed)
                            visibility = View.GONE
                        }.lparams {
                            height = matchParent
                            width = matchParent
                            gravity = Gravity.CENTER
                        }

                        listFav = recyclerView {
                            lparams(width = matchParent, height = matchParent)
                            layoutManager = LinearLayoutManager(ctx)
                        }

                    }
                }
            }

        }.view
    }

    private fun showFavorite() {
        favorites.clear()
        context?.database?.use {
            Log.e("back show", "here")
            val result = select(FavoriteRecipe.TABLE_FAVORITE_RESEP)
            val favorite = result.parseList(classParser<Resep.ResultsItem>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
        if (favorites.size==0){
            noFeed.visibility = View.VISIBLE
        }else noFeed.visibility = View.GONE
        swipeRefresh.isRefreshing = false

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_FAV) {
            showFavorite()
        }

    }

}
