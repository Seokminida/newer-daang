package com.proj.newer_daang

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview_ex.AfterReAdapter
import kotlinx.android.synthetic.main.activity_after_search.*

class AfterRe : AppCompatActivity() {
    lateinit var reAdapter: AfterReAdapter
    var datas = ArrayList<ItemData>()
    var datas2 = ArrayList<ItemData>()
    var str = String()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_search)
        var st = intent.getStringExtra("search")
        textView3.text = intent.getStringExtra("search")
        no_search.text = ("\"$st\"" +" 에 대한 결과가 없습니다.")
        initRecycler()

        val btnHome = findViewById<ImageButton>(R.id.bottombar_home)
        btnHome.setOnClickListener {
            val intentHome = Intent(this, MainActivity::class.java)
            startActivity(intentHome)
        }
        val btnBookmark = findViewById<ImageButton>(R.id.bottombar_bookmark)
        btnBookmark.setOnClickListener {
            val intentBookmark = Intent(this, BookmarkActivity::class.java)
            startActivity(intentBookmark)
        }
        val btnMypage = findViewById<ImageButton>(R.id.bottombar_mypage)
        btnMypage.setOnClickListener {
            val intentMypage = Intent(this, MyPageActivity::class.java)
            startActivity(intentMypage)
        }
        val btnInfo = findViewById<ImageButton>(R.id.bottombar_info)
        btnInfo.setOnClickListener {
            val intentInfo = Intent(this, InfoActivity::class.java)
            startActivity(intentInfo)
        }

    }


    private fun initRecycler() {
        var ch = intent.getIntExtra("ch",0)
        if(ch == 0) { // 검색 후 아이템 클릭
            reAdapter = AfterReAdapter(this)
            val afterrec: RecyclerView = findViewById(R.id.after_search)
            afterrec.adapter = reAdapter
            datas = intent.getSerializableExtra("afterdata") as ArrayList<ItemData>
            reAdapter.datas = datas

            if (reAdapter.datas.size == 0) {
                sca.setVisibility(View.VISIBLE)
                after_search.setVisibility(View.GONE)
                no_search.setVisibility(View.VISIBLE)
                birdI.setVisibility(View.VISIBLE)
            } else {
                sca.setVisibility(View.GONE)
                after_search.setVisibility(View.VISIBLE)
                no_search.setVisibility(View.GONE)
                birdI.setVisibility(View.GONE)
            }
            reAdapter.notifyDataSetChanged()
        }
        else //최근검색 아이템 클릭
        {
            var click_name = intent.getStringExtra("name2")
            textView3.text = click_name
            reAdapter = AfterReAdapter(this)
            val afterrec: RecyclerView = findViewById(R.id.after_search)
            afterrec.adapter = reAdapter
            datas = intent.getSerializableExtra("array") as ArrayList<ItemData>
            reAdapter.datas = datas

            if (reAdapter.datas.size == 0) {
                sca.setVisibility(View.VISIBLE)
                after_search.setVisibility(View.GONE)
                no_search.setVisibility(View.VISIBLE)
                birdI.setVisibility(View.VISIBLE)
                no_search.text=("\"$click_name\"" +" 에 대한 결과가 없습니다.")
            } else {
                sca.setVisibility(View.GONE)
                after_search.setVisibility(View.VISIBLE)
                no_search.setVisibility(View.GONE)
                birdI.setVisibility(View.GONE)
            }
            reAdapter.notifyDataSetChanged()
        }

    }
    fun back(v : View) {
        /*
        val intentSearch = Intent(this, SearchActivity::class.java)
        startActivity(intentSearch)
         */
        finish()
    }

    fun search(v : View) {
        val intentSearch = Intent(this, SearchActivity::class.java)
        startActivity(intentSearch)
    }
}