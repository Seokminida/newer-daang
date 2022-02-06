package com.proj.newer_daang

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.word_detail.*

class WordDetail : AppCompatActivity() {

    lateinit var wname: String
    lateinit var wmean: String

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.word_detail)

        val toolbar = findViewById<Toolbar>(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.logo_60); //제목앞에 아이콘 넣기
        toolbar.setTitle("단어");

        wname = intent.getSerializableExtra("name").toString()
        wmean = intent.getSerializableExtra("mean").toString()

        wn.text = wname
        wm.text = wmean

        more.setOnClickListener {
            Intent(this, WordMore::class.java).apply {
                putExtra("name", wn.text)
                putExtra("mean", wm.text)
                startActivity(this)
            }
        }



    }
}