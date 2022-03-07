package com.proj.newer_daang

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_word_detail_3.*

class WordDetailActivity : AppCompatActivity() {

    lateinit var wname: String
    lateinit var wmean: String
    lateinit var category: String
    lateinit var hash: String
    lateinit var article: String
    lateinit var link: String
    lateinit var cate: String
    val db = Firebase.firestore
    var like = false
    var bookmarked = false
    private lateinit var sharedPreferences : SharedPreferences
    private lateinit var editor : SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {

        val pref = this.getPreferences(0)
        val editor = pref.edit()



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
        category = intent.getSerializableExtra("category").toString()
        hash = intent.getSerializableExtra("hash").toString()
        article = intent.getSerializableExtra("article").toString()
        link = intent.getSerializableExtra("link").toString()
        cate = intent.getSerializableExtra("category").toString()

        word_name.text = wname
        word_meaning.text = wmean
        hashtag.text = hash
        news_headline.text  = article


        category_box.text = "카테고리"
        when(category){
            "politics" -> category_box.text = "정치"
            "society" -> category_box.text = "사회"
            "military" -> category_box.text = "군사"
            "culture" -> category_box.text = "문화"
            "economy" -> category_box.text = "경제"
            "IT" -> category_box.text = "IT/과학"
        }



        news_headline.setOnClickListener{
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(intent)
        }

        textsize.setOnClickListener{
            if(textCardV.getVisibility().toString() == "0") {
                textCardV.setVisibility(View.GONE)
            }
            else{
                textCardV.setVisibility(View.VISIBLE)
            }

        }
        var n_size = pref.getString("text_size","27.0f").toString()
        var m_size = pref.getString("mean_size","25.0f").toString()
        var h_size = pref.getString("hash_size","25.0f").toString()
        var c_size = pref.getString("cate_size","25.0f").toString()
        word_name.setTextSize(TypedValue.COMPLEX_UNIT_SP,n_size.toFloat())
        word_meaning.setTextSize(TypedValue.COMPLEX_UNIT_SP,m_size.toFloat())
        hashtag.setTextSize(TypedValue.COMPLEX_UNIT_SP,h_size.toFloat())
        category_box.setTextSize(TypedValue.COMPLEX_UNIT_SP,c_size.toFloat())
        news.setTextSize(TypedValue.COMPLEX_UNIT_SP,n_size.toFloat())
        news_headline.setTextSize(TypedValue.COMPLEX_UNIT_SP,m_size.toFloat())

        plusButton.setOnClickListener{
            var name_size = (word_name.textSize / 3.5 + 3).toFloat()
            var mean_size = (word_meaning.textSize / 3.5 + 3).toFloat()
            var hash = (hashtag.textSize / 3.5 + 3).toFloat()
            var cate = (category_box.textSize / 3.5 + 3).toFloat()
            editor.putString("text_size",name_size.toString()).apply()
            editor.putString("mean_size",mean_size.toString()).apply()
            editor.putString("hash_size",hash.toString()).apply()
            editor.putString("cate_size",cate.toString()).apply()
            word_name.setTextSize(TypedValue.COMPLEX_UNIT_SP,name_size)
            word_meaning.setTextSize(TypedValue.COMPLEX_UNIT_SP,mean_size)
            hashtag.setTextSize(TypedValue.COMPLEX_UNIT_SP,hash)
            category_box.setTextSize(TypedValue.COMPLEX_UNIT_SP,cate)
            news.setTextSize(TypedValue.COMPLEX_UNIT_SP,name_size)
            news_headline.setTextSize(TypedValue.COMPLEX_UNIT_SP,mean_size)
        }

        minButton.setOnClickListener{
            var name_size = (word_name.textSize / 3.5 - 3).toFloat()
            var mean_size = (word_meaning.textSize / 3.5 - 3).toFloat()
            var hash = (hashtag.textSize / 3.5 - 3).toFloat()
            var cate = (category_box.textSize / 3.5 - 3).toFloat()
            editor.putString("text_size",name_size.toString()).apply()
            editor.putString("mean_size",mean_size.toString()).apply()
            editor.putString("hash_size",hash.toString()).apply()
            editor.putString("cate_size",cate.toString()).apply()
            word_name.setTextSize(TypedValue.COMPLEX_UNIT_SP,name_size)
            word_meaning.setTextSize(TypedValue.COMPLEX_UNIT_SP,mean_size)
            hashtag.setTextSize(TypedValue.COMPLEX_UNIT_SP,hash)
            category_box.setTextSize(TypedValue.COMPLEX_UNIT_SP,cate)
            news.setTextSize(TypedValue.COMPLEX_UNIT_SP,name_size)
            news_headline.setTextSize(TypedValue.COMPLEX_UNIT_SP,mean_size)
        }


        val heart = findViewById<ImageButton>(R.id.heart)
        heart.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View){
                onLikeClicked(wname)
            }
        })
        val docLikes = db.collection("user").document(Firebase.auth.uid.toString()).collection("좋아요")
        docLikes.get()
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


        val bookmark = findViewById<ImageButton>(R.id.bookmark)
        bookmark.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View){
                var meaning = wmean.replace("\n", "\\n");
                onBookmarkClicked(ItemData(wname,meaning,category, hash, article, link))
            }
        })
        val docBookmark = db.collection("user").document(Firebase.auth.uid.toString()).collection("북마크")
        docBookmark.get()
            .addOnSuccessListener {
                    document ->
                for(result in document){
                    if(wname.equals(result["name"].toString())){
                        bookmarked = true
                        bookmark.setImageResource(R.drawable.bookmark_filled_50_2)
                        break
                    }
                }
                if(!bookmarked){
                    bookmark.setImageResource(R.drawable.bookmark_empty_50_2)
                }

            }
            .addOnFailureListener { exception ->
                Log.d("bookmarksSetUp", "get failed with ", exception)
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

    fun onLikeClicked(term: String): Boolean{
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

    fun onBookmarkClicked(term: ItemData): Boolean{
        if(bookmarked){
            db.collection("user").document(Firebase.auth.uid.toString()).collection("북마크").document(term.name)
                .delete()
                .addOnSuccessListener { Log.d("firebasedeleted", "DocumentSnapshot successfully deleted!") }
                .addOnFailureListener { e -> Log.w("firebasedeletedError", "Error deleting document", e) }

            bookmark.setImageResource(R.drawable.bookmark_empty_50_2)
            bookmarked = false
        }
        else{


            val bookmarks_info = hashMapOf(
                "name" to term.name,
                "meaning" to term.meaning,
                "category" to term.category,
                "hashtag" to term.hashT,
                "article" to term.article,
                "link" to term.link,
            )
            db.collection("user").document(Firebase.auth.uid.toString()).collection("북마크").document(term.name)
                .set(bookmarks_info)
                .addOnSuccessListener { Log.d("firebaseadded", "DocumentSnapshot successfully added!") }
                .addOnFailureListener { e -> Log.w("firebaseaddedError", "Error adding document", e) }
            bookmark.setImageResource(R.drawable.bookmark_filled_50_2)
            bookmarked = true
        }
        return true
    }

}