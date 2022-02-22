package com.proj.newer_daang

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class LoadingActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT:Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            startActivity(Intent(this, StartActivity::class.java))

            // close this activity
            finish()
        }, SPLASH_TIME_OUT)
    }

}