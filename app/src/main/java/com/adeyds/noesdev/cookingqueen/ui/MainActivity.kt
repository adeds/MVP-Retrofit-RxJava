package com.adeyds.noesdev.cookingqueen.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import android.os.Build
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import com.adeyds.noesdev.cookingqueen.ui.home.HomeFragment
import org.jetbrains.anko.toast
import com.adeyds.noesdev.cookingqueen.utils.BottomNavigationViewBehavior
import android.support.design.widget.CoordinatorLayout
import android.util.Log
import com.adeyds.noesdev.cookingqueen.R
import com.adeyds.noesdev.cookingqueen.ui.favorite.FavoritesFragment
import com.adeyds.noesdev.cookingqueen.utils.Constants
import com.google.android.gms.ads.MobileAds


class MainActivity : AppCompatActivity() {
    private var doubleBackToExitPressedOnce = false

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                supportActionBar?.title = "Home"

                val home = HomeFragment()
                openFragment(home)
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_favorites -> {
                supportActionBar?.title = "Favorite"

                val favFragment = FavoritesFragment()
                openFragment(favFragment)
                return@OnNavigationItemSelectedListener true

            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutParams = bottom_nav.layoutParams as CoordinatorLayout.LayoutParams
        layoutParams.behavior = BottomNavigationViewBehavior()

        setSupportActionBar(toolbar)

        bottom_nav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        bottom_nav.selectedItemId = R.id.navigation_home

        MobileAds.initialize(this, Constants.AD_APP_ID);
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity()
            } else {
                ActivityCompat.finishAffinity(this)
            }
            System.exit(0)

        }

        this.doubleBackToExitPressedOnce = true
        toast("Press again for exit")
        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)


    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("back main", "$requestCode here $resultCode")
    }


}
