package com.proj.newer_daang

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val toolbar = findViewById<Toolbar>(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.logo_60); //제목앞에 아이콘 넣기
        //toolbar.setTitle("뉴어당");


        //bottom bar: onclick intent move
        val btnBookmark = findViewById<ImageButton>(R.id.bottombar_bookmark);
        btnBookmark.setOnClickListener {
            val intentBookmark = Intent(this, BookmarkActivity::class.java)
            startActivity(intentBookmark)
        }

        //

        val tvBestWord = findViewById<TextView>(R.id.bestword);
        tvBestWord.setOnClickListener {
            val intentBestWord = Intent(this, WordDetail::class.java)
            startActivity(intentBestWord)
        }

        //category: onclick intent move
        val tvCate_politics = findViewById<TextView>(R.id.cate_politics);
        tvCate_politics.setOnClickListener {
            val intentTermsList = Intent(this, TermsListActivity::class.java)
            intentTermsList.putExtra("category","0")
            startActivity(intentTermsList)
        }
        val tvCate_social = findViewById<TextView>(R.id.cate_social);
        tvCate_social.setOnClickListener {
            val intentTermsList = Intent(this, TermsListActivity::class.java)
            intentTermsList.putExtra("category","1")
            startActivity(intentTermsList)
        }
        val tvCate_economy = findViewById<TextView>(R.id.cate_economy);
        tvCate_economy.setOnClickListener {
            val intentTermsList = Intent(this, TermsListActivity::class.java)
            intentTermsList.putExtra("category","4")
            startActivity(intentTermsList)
        }
        val tvCate_culture = findViewById<TextView>(R.id.cate_culture);
        tvCate_culture.setOnClickListener {
            val intentTermsList = Intent(this, TermsListActivity::class.java)
            intentTermsList.putExtra("category","3")
            startActivity(intentTermsList)
        }
        val tvCate_military = findViewById<TextView>(R.id.cate_military);
        tvCate_military.setOnClickListener {
            val intentTermsList = Intent(this, TermsListActivity::class.java)
            intentTermsList.putExtra("category","2")
            startActivity(intentTermsList)
        }
        val tvCate_IT = findViewById<TextView>(R.id.cate_IT);
        tvCate_IT.setOnClickListener {
            val intentTermsList = Intent(this, TermsListActivity::class.java)
            intentTermsList.putExtra("category","5")
            startActivity(intentTermsList)
        }
        //




    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 클릭된 메뉴 아이템의 아이디 마다 when 구절로 클릭시 동작을 설정한다.
        when(item!!.itemId){
            R.id.app_bar_search->{ // 메뉴 버튼
                val intentSearch = Intent(this, SearchActivity::class.java)
                startActivity(intentSearch)
                //Snackbar.make(toolbar,"Menu pressed",Snackbar.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.func_search, menu)
        return true
    }
}


//main_page




//하단바 액션(시작페이지, 로그인페이지 말고는 모두 등장)
