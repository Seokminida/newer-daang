package com.proj.newer_daang

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class LoadingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        try {
            Thread.sleep(500) //loading page is shown
            //splash display waiting time
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        startActivity(Intent(this, StartActivity::class.java))
        //shows main page if user have already logged in, else show start page
        finish()
    }

}