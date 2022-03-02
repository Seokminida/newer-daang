package com.proj.newer_daang

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_word_detail.*

class WordMoreActivity : AppCompatActivity(){

    lateinit var wname : String
    lateinit var wmean : String
    val db = Firebase.firestore
    var like = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_detail_3)

        val toolbar = findViewById<Toolbar>(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.logo_circle_40); //제목앞에 아이콘 넣기

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


        wname = intent.getSerializableExtra("name").toString()
        wmean = intent.getSerializableExtra("mean").toString()
        wmean = wmean.replace("\\n", "\n");
        wn.text = wname
        wm.text = wmean
        wm.setMovementMethod(ScrollingMovementMethod())

        val bookmark = findViewById<ImageButton>(R.id.bookmark)
        bookmark.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View){
                onLikeClicked(wname)
            }
        })

        val heart = findViewById<ImageButton>(R.id.heart)
        heart.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View){
                onLikeClicked(wname)
            }
        })
        val docRef = db.collection("user").document(Firebase.auth.uid.toString()).collection("좋아요")
        docRef.get()
            .addOnSuccessListener {
                    document ->
                for(result in document){
                    if(wname.equals(result["name"].toString())){
                        like = true
                        heart.setImageResource(R.drawable.heart_filled_50_2)
                        break
                    }
                }
                if(!like){
                    heart.setImageResource(R.drawable.heart_empty_50_2)
                }

            }
            .addOnFailureListener { exception ->
                Log.d("likesSetUp", "get failed with ", exception)
            }


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

    fun onLikeClicked(term: String, ): Boolean{
        if(like){
            db.collection("user").document(Firebase.auth.uid.toString()).collection("좋아요").document(term)
                .delete()
                .addOnSuccessListener { Log.d("firebasedeleted", "DocumentSnapshot successfully deleted!") }
                .addOnFailureListener { e -> Log.w("firebasedeletedError", "Error deleting document", e) }

            heart.setImageResource(R.drawable.heart_empty_50_2)
            like = false
        }
        else{
            val likes_info = hashMapOf(
                "like" to "true",
                "name" to term,
            )
            db.collection("user").document(Firebase.auth.uid.toString()).collection("좋아요").document(term)
                .set(likes_info)
                .addOnSuccessListener { Log.d("firebaseadded", "DocumentSnapshot successfully deleted!") }
                .addOnFailureListener { e -> Log.w("firebaseaddedError", "Error deleting document", e) }
            heart.setImageResource(R.drawable.heart_filled_50_2)
            like = true
        }
        return true
    }
}