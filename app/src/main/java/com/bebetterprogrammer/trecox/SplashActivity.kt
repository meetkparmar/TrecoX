package com.bebetterprogrammer.trecox

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler

class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed(
            {
                val i = Intent(this, OnBoardingActivity::class.java)
                startActivity(i)
                finish()
            }, 2000)
    }
}