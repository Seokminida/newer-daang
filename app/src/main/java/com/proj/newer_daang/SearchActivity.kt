package com.proj.newer_daang

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview_ex.RecentAdapter
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.item_recent.*

class SearchActivity : AppCompatActivity(){
    lateinit var wordAdapter: WordAdapter
    lateinit var recentAdapter: RecentAdapter
    val datas = ArrayList<ItemData>()
    val redatas = ArrayList<ItemData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initRecycler()
        initRecycler2()
        recentAdapter.listUpdate(datas)
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

        clearB.setOnClickListener{
            searchBar.setText(null)
        }

        recent_close_button.setOnClickListener {
            if(recent.visibility == View.GONE) {
                recent.setVisibility(View.VISIBLE)
                recent_close_button.text="최근 검색 닫기"
            }
            else if(recent.visibility == View.VISIBLE)
            {
                recent_close_button.text="최근 검색 열기"
                recent.setVisibility(View.GONE)
            }
        }

        search.setOnClickListener{
            var check = 0
            var re_st: String
            re_st = searchBar.text.toString()
            for (i in 0 until redatas.size){
                if(redatas[i].name == re_st)
                {
                    check = 1
                }
            }
            if(check == 0 && re_st.length > 0){
                redatas.apply{
                    add(0, ItemData("$re_st",""))
                }

            }
            recentAdapter.recentList(redatas)

            Intent(this, AfterRe::class.java).apply{
                putExtra("afterdata",datas2)
                putExtra("search",searchBar.text.toString())
                startActivity(this)
            }
        }

        searchBar.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var searchT: String = p0.toString()
                if(searchT.length == 0)
                {
                    scalableLayout2.setVisibility(View.VISIBLE)
                    clearB.setVisibility(View.GONE)
                    lineView.setVisibility(View.GONE)
                    wordRe.setVisibility(View.GONE)
                    recent.setVisibility(View.VISIBLE)
                }
                else {
                    scalableLayout2.setVisibility(View.GONE)
                    clearB.setVisibility(View.VISIBLE)
                    wordRe.setVisibility(View.VISIBLE)
                    lineView.setVisibility(View.VISIBLE)
                    recent.setVisibility(View.GONE)
                }
                searchFilter(searchT)
            }

            override fun afterTextChanged(p0: Editable?) {
                var searchT: String = p0.toString()
                searchFilter(searchT)
            }
        })

    }
    private fun initRecycler(){
        wordAdapter = WordAdapter(this)
        val wordrec: RecyclerView = findViewById(R.id.wordRe)
        wordrec.adapter = wordAdapter

        datas.apply{
            add(ItemData("가나다라","예시11231231231231231231asdasdasdasdsad23"))
            add(ItemData("나다라마바","예시2"))
            add(ItemData("가다마아자","예시3"))
            add(ItemData("abcd","예시3"))
            wordAdapter.datas = datas
            wordAdapter.notifyDataSetChanged()
        }


    }

    private fun initRecycler2(){
        recentAdapter = RecentAdapter(this)
        val recentrec: RecyclerView = findViewById(R.id.recent)
        recentrec.adapter = recentAdapter
        recent.setVisibility(View.VISIBLE)

    }

    var datas2 = ArrayList<ItemData>()

    fun searchFilter(str : String){
        datas2.clear()

        if(str.length != 0) {
            for (i in 0 until datas.size) {
                if(str.length > datas[i].name.length)
                    break
                var check = 1
                for (j in 0 until str.length){
                    if(str[j] != datas[i].name[j]){
                        check = 0
                        break
                    }
                }
                if(check == 1 )
                    datas2.add(datas[i])
            }
        }
        if(str.length > 1) {
            for (i in 0 until datas.size) {
                if (datas[i].name
                        .contains(str)
                ) {
                    if (datas2.contains(datas[i]))
                        continue
                    datas2.add(datas[i])
                }
            }
        }

        wordAdapter.filterList(datas2)
    }
    fun back(v : View) {
        val intent = Intent()
        finish()
    }

}