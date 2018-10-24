package com.adeyds.noesdev.cookingqueen.ui.detail


import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.adeyds.noesdev.cookingqueen.R
import com.adeyds.noesdev.cookingqueen.db.FavoriteRecipe
import com.adeyds.noesdev.cookingqueen.db.FavoriteRecipe.Companion.HREF
import com.adeyds.noesdev.cookingqueen.db.FavoriteRecipe.Companion.INGREDIENT
import com.adeyds.noesdev.cookingqueen.db.FavoriteRecipe.Companion.THUMBNAIL
import com.adeyds.noesdev.cookingqueen.db.FavoriteRecipe.Companion.TITLE
import com.adeyds.noesdev.cookingqueen.db.database
import com.adeyds.noesdev.cookingqueen.ui.model.Resep
import com.adeyds.noesdev.cookingqueen.utils.Constants.Companion.EXTRA_DETAIL
import com.adeyds.noesdev.cookingqueen.utils.ViewAnimation

import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import android.content.Intent
import android.util.Log
import android.webkit.*
import kotlinx.android.synthetic.main.content_detail.*
import android.app.Dialog
import android.view.Window
import android.widget.TextView
import android.widget.ImageView
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class DetailActivity : AppCompatActivity() {
    private lateinit var resep: Resep.ResultsItem
    private var rotate = false
    private lateinit var hrefUnik: String
    private var isFavorite: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)

        resep = intent.getParcelableExtra(EXTRA_DETAIL)
        hrefUnik = resep.href

        supportActionBar?.title = resep.title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        ViewAnimation.initShowOut(fab_share)
        ViewAnimation.initShowOut(fab_save)
        ViewAnimation.initShowOut(fab_more)

        configWebview()

        fab_add.setOnClickListener {
            rotate = ViewAnimation.rotateFab(it, !rotate)
            if (rotate) {
                ViewAnimation.showIn(fab_share)
                ViewAnimation.showIn(fab_save)
                ViewAnimation.showIn(fab_more)
            } else {
                ViewAnimation.showOut(fab_share)
                ViewAnimation.showOut(fab_more)
                ViewAnimation.showOut(fab_save)
            }
        }
        favoriteState()
        fab_save.setOnClickListener {
            if (isFavorite) removeFromFavorite() else addToFavorite()

            isFavorite = !isFavorite
            setFavorite()

            true
        }

        fab_share.setOnClickListener {
            val sharingIntent = Intent(android.content.Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            val shareBody = getString(R.string.share_body) + " ${resep.title} ?" +
                    getString(R.string.share_body_2) +
                    "\n" + resep.href
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.share_subject))
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody)

            startActivity(Intent.createChooser(sharingIntent, "Share via"))
        }

        fab_more.setOnClickListener {
            showDialog(resep.title, resep.ingredients, resep.thumbnail)
        }
    }

    private fun favoriteState() {
        database.use {
            val result = select(FavoriteRecipe.TABLE_FAVORITE_RESEP)
                    .whereArgs("($HREF = {id})",
                            "id" to hrefUnik)
            val favorite = result.parseList(classParser<FavoriteRecipe>())
            if (!favorite.isEmpty()) isFavorite = true
        }
        setFavorite()
    }

    private fun setFavorite() {
        if (isFavorite)
            fab_save.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite))
        else
            fab_save.setImageDrawable(resources.getDrawable(R.drawable.ic_unfavorite))
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(FavoriteRecipe.TABLE_FAVORITE_RESEP, "($HREF = {id})",
                        "id" to hrefUnik)
            }
            snackbar(coordinator_lyt, getString(R.string.removed)).show()
        } catch (e: SQLiteConstraintException) {
            snackbar(coordinator_lyt, e.localizedMessage).show()
        }
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(FavoriteRecipe.TABLE_FAVORITE_RESEP,
                        THUMBNAIL to resep.thumbnail,
                        INGREDIENT to resep.ingredients,
                        HREF to hrefUnik,
                        TITLE to resep.title
                )
            }
            snackbar(coordinator_lyt, getString(R.string.added)).show()
        } catch (e: SQLiteConstraintException) {
            snackbar(coordinator_lyt, e.localizedMessage).show()
        }
    }


    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> false
    }


    private fun configWebview() {
        webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: String?): Boolean {
                view?.loadUrl(request)
                return true
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                Log.e("errWeb", error.toString())
            }
        }
        webview.settings.javaScriptEnabled = true
        webview.loadUrl(resep.href)
    }

    private fun showDialog(title: String, ing: String, img: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_more)
        val titleMore = dialog.findViewById(R.id.tv_title_more) as TextView
        val ingMore = dialog.findViewById(R.id.tv_ing_more) as TextView
        val imgMore= dialog.findViewById(R.id.img_more) as ImageView
        titleMore.text = title
        ingMore.text = ing
        Glide.with(applicationContext).apply {
            RequestOptions.fitCenterTransform()
                    .error(R.drawable.food)
                    .placeholder(R.drawable.food)
        }.load(img).into(imgMore)
        val cancelZ = dialog.findViewById(R.id.cancel_zone) as RelativeLayout
        cancelZ.setOnClickListener { dialog.dismiss() }

        dialog.show()

    }
}
