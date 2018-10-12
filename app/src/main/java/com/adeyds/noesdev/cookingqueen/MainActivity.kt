package com.adeyds.noesdev.cookingqueen

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import android.view.MenuItem
import android.support.v4.widget.NestedScrollView
import android.os.Build
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.util.Log
import android.view.WindowManager
import com.adeyds.noesdev.cookingqueen.ui.home.HomeFragment
import kotlinx.android.synthetic.main.custom_search_bar.*
import org.jetbrains.anko.toast


class MainActivity : AppCompatActivity() {


    var isNavigationHide: Boolean = false
    var isSearchBarHide: Boolean = false
    var isLayoutAdvance: Boolean = false
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        navigation.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.navigation_home -> {
                        val homeFragment = HomeFragment()
                        openFragment(homeFragment)
                        return true
                    }
                    R.id.navigation_favorites -> {
                        val favFragment = FavoritesFragment()
                        openFragment(favFragment)
                        return true
                    }

                }
                return false
            }
        })

        nested_scroll_view.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY < oldScrollY) { // up
                animateNavigation(false)
                animateSearchBar(false)
            }
            if (scrollY > oldScrollY) { // down
                animateNavigation(true)
                animateSearchBar(true)
            }
        })

        btn_advance_search.setOnClickListener({ btn_advance_search ->
            hideAdvanceSearch(isLayoutAdvance)
            Log.e("tot", isLayoutAdvance.toString())
        })
        navigation.selectedItemId = R.id.navigation_home
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
        //toast("Press 2 times for exit")
        toast("Klik 2 kali untuk keluar")
        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)


    }

    private fun animateNavigation(hide: Boolean) {
        if (isNavigationHide && hide || !isNavigationHide && !hide) return
        isNavigationHide = hide
        val moveY = if (hide) 2 * navigation.height else 0
        navigation.animate().translationY(moveY.toFloat()).setStartDelay(100).setDuration(300).start()
    }

    private fun hideAdvanceSearch(show: Boolean) {
        if (!show) {
            Log.e("tot", "vsi")
            layout_advance_search.visibility = View.VISIBLE
        } else {
            layout_advance_search.visibility = View.GONE
            Log.e("tot", "gon")
        }
        isLayoutAdvance = !show
        Log.e("tot", isLayoutAdvance.toString())
    }

    private fun animateSearchBar(hide: Boolean) {
        if (isSearchBarHide && hide || !isSearchBarHide && !hide) return
        isSearchBarHide = hide
        val moveY = if (hide) -(2 * search_bar.height) else 0
        search_bar.animate().translationY(moveY.toFloat()).setStartDelay(100).setDuration(300).start()
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
