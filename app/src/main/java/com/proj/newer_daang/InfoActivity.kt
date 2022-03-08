package com.proj.newer_daang

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.TextView

class InfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)



        //bottom bar: onclick intent move
        val btnHome = findViewById<ImageButton>(R.id.bottombar_home)
        btnHome.setOnClickListener {
            val intentHome = Intent(this, MainActivity::class.java)
            startActivity(intentHome)
        }
        val btnMypage = findViewById<ImageButton>(R.id.bottombar_mypage)
        btnMypage.setOnClickListener {
            val intentMypage = Intent(this, MyPageActivity::class.java)
            startActivity(intentMypage)
        }
        val btnBookmark = findViewById<ImageButton>(R.id.bottombar_bookmark)
        btnBookmark.setOnClickListener {
            val intentBookmark = Intent(this, BookmarkActivity::class.java)
            startActivity(intentBookmark)
        }

        val link_button = findViewById<TextView>(R.id.link)
        link_button.setOnClickListener {
            val link = "https://newer-daang.notion.site/81375a5cfe8a4201bc743a6d52e2288f"
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(intent)
        }

    }


}


//main_page




//하단바 액션(시작페이지, 로그인페이지 말고는 모두 등장)
