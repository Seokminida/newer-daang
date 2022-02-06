package com.proj.newer_daang

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.word_detail.*

class WordMore : AppCompatActivity(){

    lateinit var wname : String
    lateinit var wmean : String

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.word_more)

        val toolbar = findViewById<Toolbar>(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.logo_60); //제목앞에 아이콘 넣기
        toolbar.setTitle("Hello")


        wname = intent.getSerializableExtra("name").toString()
        wmean = intent.getSerializableExtra("mean").toString()
        wn.text = wname
        wm.text = wmean

    }


}