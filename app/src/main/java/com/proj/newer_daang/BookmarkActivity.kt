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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_word_detail_3.*

class BookmarkActivity : AppCompatActivity() {

    lateinit var bookmarkAdapter: BookmarkAdapter
    val bookmarks = mutableListOf<ItemData>()
    val likes = ArrayList<String>()
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
            override fun onItemClick(v: View, data: ItemData, pos: Int) {
                intentWordDetail.apply{
                    putExtra("name", data.name)
                    putExtra("mean",data.meaning)
                    putExtra("category",data.category)
                    putExtra("hash",data.hashT)
                    putExtra("article",data.article)
                    putExtra("link",data.link)
                    startActivity(this)
                }
                //Toast.makeText( App.ApplicationContext(), "북마크에서 용어 상세 설명 페이지로 전환됩니다.", Toast.LENGTH_SHORT ).show()
            }
        })

        /*
        val docRef = db.collection("user").document(Firebase.auth.uid.toString()).collection("북마크")
        docRef.whereEqualTo().get()
         */


        val docBookmark = db.collection("user").document(Firebase.auth.uid.toString()).collection("북마크")
        docBookmark.get()
            .addOnSuccessListener {
                    document ->
                bookmarks.clear()
                for(result in document){
                    var data =ItemData(result["name"].toString(), result["meaning"].toString(), result["category"].toString(), result["hashtag"].toString(),result["article"].toString(), result["link"].toString())
                    bookmarks.add(data)

                }
                bookmarks.apply {
                    bookmarkAdapter.bookmarkList = bookmarks
                    bookmarkAdapter.notifyDataSetChanged()

                }
            }
            .addOnFailureListener { exception ->
                Log.d("bookmarksSetUp", "get failed with ", exception)
            }

        val docLikes = db.collection("user").document(Firebase.auth.uid.toString()).collection("좋아요")
        docLikes.get()
            .addOnSuccessListener {
                    document ->
                likes.clear()
                for(result in document){
                    val term_item = result["name"].toString()
                    likes.add(term_item)
                }

                likes.apply {
                    bookmarkAdapter.likesList = likes
                    bookmarkAdapter.notifyDataSetChanged()
                }
            }
            .addOnFailureListener { exception ->
                Log.d("likesListSetUp", "get failed with ", exception)
            }







    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.func_nothing, menu)
        return true
    }

}

//bookmark_page

