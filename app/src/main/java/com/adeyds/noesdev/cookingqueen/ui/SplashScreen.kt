package com.adeyds.noesdev.cookingqueen.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.adeyds.noesdev.cookingqueen.R
import org.jetbrains.anko.startActivity

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            startActivity<MainActivity>()
            finish()
        }, 3000)
    }
}
