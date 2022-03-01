package com.proj.newer_daang

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_search_2.*


class TermsListActivity : AppCompatActivity() {

    lateinit var termAdapter: TermAdapter
    lateinit var categoryAdapter_: CategoryAdapter
    val terms = ArrayList<TermData>()
    val likes = ArrayList<String>()
    var categories = ArrayList<CategoryAdapter.CateData>()

    var db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_termslist)

        val pressed_category = intent.getStringExtra("category")
        var selected_category = ""


        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.logo_circle_40) //제목앞에 아이콘 넣기
        toolbar.setTitle("카테고리")


        //bottom bar: onclick intent move
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


        val recyclerView_category = findViewById<RecyclerView>(R.id.recyclerView_category)
        categoryAdapter_ = CategoryAdapter(this)
        recyclerView_category.adapter = categoryAdapter_
        recyclerView_category.addItemDecoration(VerticalItemDecorator_rv(5))
        recyclerView_category.addItemDecoration(HorizontalItemDecorator_rv(5))

        if (pressed_category != null) {
            categoryAdapter_.selected = pressed_category.toInt()
        }

        categoryAdapter_.setOnItemClickListener(object: CategoryAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: CategoryAdapter.CateData, pos: Int) {
                terms.apply {
                    terms.clear()
                    termAdapter.termsList = terms
                    termAdapter.notifyDataSetChanged()
                }


                when(pos){
                    0 -> selected_category="politics"
                    1 -> selected_category="society"
                    2 -> selected_category="military"
                    3 -> selected_category="culture"
                    4 -> selected_category="economy"
                    5 -> selected_category="IT"
                }

                val docRef = db.collection(selected_category)
                docRef.get()
                    .addOnSuccessListener {
                            document ->
                        terms.clear()
                        for(result in document){
                            val term_item = TermData(result["name"].toString(),result["meaning"].toString())
                            //Log.d("jonnjoon",selected_category)
                            terms.add(term_item)
                        }

                        terms.apply {
                            termAdapter.termsList = terms
                            termAdapter.notifyDataSetChanged()
                        }

                    }
                    .addOnFailureListener { exception ->
                        Log.d("asd", "get failed with ", exception)
                    }

            }
        })

        categories.apply {
            add(CategoryAdapter.CateData(name = "정치"))
            add(CategoryAdapter.CateData( name = "사회"))
            add(CategoryAdapter.CateData( name = "군사"))
            add(CategoryAdapter.CateData( name = "문화"))
            add(CategoryAdapter.CateData( name = "경제"))
            add(CategoryAdapter.CateData( name = "IT/과학"))
            categoryAdapter_.cateList = categories
            categoryAdapter_.notifyDataSetChanged()
        }
        categoryAdapter_.notifyDataSetChanged()

        //if pressed_category == cate, cate의 인덱스에 해당하는 recyclerview background 변경


        val recyclerView_termsList = findViewById<RecyclerView>(R.id.recyclerView)
        termAdapter = TermAdapter(this)
        recyclerView_termsList.adapter = termAdapter
        recyclerView_termsList.addItemDecoration(VerticalItemDecorator_rv(5))
        recyclerView_termsList.addItemDecoration(HorizontalItemDecorator_rv(5))

        val intentWordDetail = Intent(this, WordDetailActivity::class.java)

        termAdapter.setOnItemClickListener(object: TermAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: TermData, pos: Int) {
                intentWordDetail.apply{
                    putExtra("name", data.name)
                    putExtra("mean",data.meaning)
                    startActivity(this)
                }

                // startActivity(intentWordDetail)
                //Toast.makeText( App.ApplicationContext(), "용어목록에서 용어 상세 설명 페이지로 전환됩니다.", Toast.LENGTH_SHORT ).show()
                /*
                Intent(this@MainActivity, ProfileDetailActivity::class.java).apply {
                    putExtra("data", data)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { startActivity(this) }
                 */
            }
        })

        when(categoryAdapter_.selected){
            0 -> selected_category="politics"
            1 -> selected_category="society"
            2 -> selected_category="military"
            3 -> selected_category="culture"
            4 -> selected_category="economy"
            5 -> selected_category="IT"
        }

        val docRef = db.collection(selected_category)
        docRef.get()
            .addOnSuccessListener {
                    document ->
                terms.clear()
                for(result in document){
                    val term_item = TermData(result["name"].toString(),result["meaning"].toString())
                    //Log.d("jonnjoon",selected_category)
                    terms.add(term_item)
                }
                terms.apply {
                    termAdapter.termsList = terms
                    termAdapter.notifyDataSetChanged()
                }
            }
            .addOnFailureListener { exception ->
                Log.d("asd", "get failed with ", exception)
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
                    termAdapter.likesList = likes
                    termAdapter.notifyDataSetChanged()
                }
            }
            .addOnFailureListener { exception ->
                Log.d("likesListSetUp", "get failed with ", exception)
            }

    }




/*
    private fun initRecycler(){
        termAdapter = TermAdapter(this)
        val recyclerView_termsList = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView_termsList.adapter = termAdapter

        terms.apply{
            termAdapter.termsList = terms
            termAdapter.notifyDataSetChanged()
        }
*/


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

//terms_list_page (by category)

