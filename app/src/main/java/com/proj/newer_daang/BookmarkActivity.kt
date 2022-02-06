package com.proj.newer_daang

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class BookmarkActivity : AppCompatActivity() {

    lateinit var bookmarkAdapter: BookmarkAdapter
    val bookmarks = mutableListOf<BookmarkData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark)

        val toolbar = findViewById<Toolbar>(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.logo_60); //제목앞에 아이콘 넣기
        toolbar.setTitle("북마크");

        val btnHome = findViewById<ImageButton>(R.id.bottombar_home);
        btnHome.setOnClickListener {
            val intentHome = Intent(this, MainActivity::class.java)
            startActivity(intentHome)
        }




        val recyclerView_bookmark = findViewById<RecyclerView>(R.id.recyclerView_bookmark)
        bookmarkAdapter = BookmarkAdapter(this)
        recyclerView_bookmark.adapter = bookmarkAdapter
        recyclerView_bookmark.addItemDecoration(VerticalItemDecorator_rv(3))
        recyclerView_bookmark.addItemDecoration(HorizontalItemDecorator_rv(5))
        bookmarkAdapter.setOnItemClickListener(object: BookmarkAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: BookmarkData, pos: Int) {
                Toast.makeText( App.ApplicationContext(), "북마크에서 용어 상세 설명 페이지로 전환됩니다.", Toast.LENGTH_SHORT ).show()
            }
        })

        bookmarks.apply {
            add(BookmarkData( name = "용어북마크1", meaning = "근로자는 근로조건의 향상을 위하여 자주적인 단결권·단체교섭권 및 단체행동권을 가진다. ", category = "IT/과학", tag1 = "#취미생활 #원예생활 #플랜트", tag2 = "해시2"))
            add(BookmarkData( name = "용어북마크2입니다", meaning = "이 헌법공포 당시의 국회의원의 임기는 제1항에 의한 국회의 최초의 집회일 전일까지로 한다.", category = "정치", tag1 = "해시샘플", tag2 = "해시또"))
            add(BookmarkData( name = "용어북마크3", meaning = "광물 기타 중요한 지하자원·수산자원·수력과 경제상 이용할 수 있는 자연력은 법률이 정하는 바에 의하여 일정한 기간 그 채취·개발 또는 이용을 특허할 수 있다.", category = "IT/과학", tag1 = "해시", tag2 = "해시2"))
            add(BookmarkData( name = "term_sample_length_check", meaning = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum a dolor luctus, fringilla est sed, volutpat eros. Vivamus tristique gravida.", category = "정치", tag1 = "해시샘플", tag2 = "해시또"))
            add(BookmarkData( name = "term_sample", meaning = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum a dolor luctus.", category = "IT/과학", tag1 = "해시", tag2 = "해시2"))
            add(BookmarkData( name = "term_sample_length_check", meaning = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum a dolor luctus, fringilla est sed, volutpat eros. Vivamus tristique gravida.", category = "정치", tag1 = "해시샘플", tag2 = "해시또"))
            add(BookmarkData( name = "term_sample", meaning = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum a dolor luctus.", category = "IT/과학", tag1 = "해시", tag2 = "해시2"))
            add(BookmarkData( name = "term_sample_length_check", meaning = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum a dolor luctus, fringilla est sed, volutpat eros. Vivamus tristique gravida.", category = "정치", tag1 = "해시샘플", tag2 = "해시또"))

            bookmarkAdapter.bookmarkList = bookmarks
            bookmarkAdapter.notifyDataSetChanged()

        }






    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.func_nothing, menu)
        return true
    }

}

//bookmark_page

