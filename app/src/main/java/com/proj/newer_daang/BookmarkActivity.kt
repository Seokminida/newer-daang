package com.proj.newer_daang

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BookmarkActivity : AppCompatActivity() {

    lateinit var bookmarkAdapter: BookmarkAdapter
    val bookmarks = mutableListOf<BookmarkData>()
    var db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark)

        val toolbar = findViewById<Toolbar>(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.logo_circle_40); //제목앞에 아이콘 넣기

        val btnHome = findViewById<ImageButton>(R.id.bottombar_home);
        btnHome.setOnClickListener {
            val intentHome = Intent(this, MainActivity::class.java)
            startActivity(intentHome)
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




        val recyclerView_bookmark = findViewById<RecyclerView>(R.id.recyclerView_bookmark)
        bookmarkAdapter = BookmarkAdapter(this)
        recyclerView_bookmark.adapter = bookmarkAdapter
        recyclerView_bookmark.addItemDecoration(VerticalItemDecorator_rv(3))

        val intentWordDetail = Intent(this, WordDetailActivity::class.java)

        bookmarkAdapter.setOnItemClickListener(object: BookmarkAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: BookmarkData, pos: Int) {
                intentWordDetail.apply{
                    putExtra("name", data.name)
                    putExtra("mean",data.meaning)
                    startActivity(this)
                }
                //Toast.makeText( App.ApplicationContext(), "북마크에서 용어 상세 설명 페이지로 전환됩니다.", Toast.LENGTH_SHORT ).show()
            }
        })



        /*
        val docRef = db.collection("user")
        docRef.get()
            .addOnSuccessListener {
                    document ->

                //bookmarks.clear()
                for(result in document){

                    /*
                    if result == uid
                        then collection "북마크"
                            then 북마크의 문서 중 단어 > 의 필드 pk에 단어 key값 넣기(name에 단어 이름) & get해서 불러오기
                     */

                    val term_item = BookmarkData(result["name"].toString(),result["meaning"].toString())
                    //Log.d("jonnjoon",selected_category)
                    bookmarks.add(term_item)
                }
                bookmarks.apply {
                    bookmarkAdapter.bookmarkList = bookmarks
                    bookmarkAdapter.notifyDataSetChanged()
                }
            }
            .addOnFailureListener { exception ->
                Log.d("asd", "get failed with ", exception)
            }

        */

        bookmarks.apply {
            add(BookmarkData( name = "용어북마크1", meaning = "근로자는 근로조건의 향상을 위하여 자주적인 단결권·단체교섭권 및 단체행동권을 가진다. ", category = "IT/과학", tag1 = "#취미생활 #원예생활 #플랜트", tag2 = "해시2"))
            add(BookmarkData( name = "용어북마크2입니다", meaning = "이 헌법공포 당시의 국회의원의 임기는 제1항에 의한 국회의 최초의 집회일 전일까지로 한다.", category = "정치", tag1 = "해시샘플", tag2 = "해시또"))
            add(BookmarkData( name = "용어북마크3", meaning = "광물 기타 중요한 지하자원·수산자원·수력과 경제상 이용할 수 있는 자연력은 법률이 정하는 바에 의하여 일정한 기간 그 채취·개발 또는 이용을 특허할 수 있다.", category = "IT/과학", tag1 = "해시", tag2 = "해시2"))
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

