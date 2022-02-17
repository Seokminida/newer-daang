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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class TermsListActivity : AppCompatActivity() {

    lateinit var termAdapter: TermAdapter
    lateinit var categoryAdapter_: CategoryAdapter
    val terms = mutableListOf<TermData>()
    val categories = mutableListOf<CategoryAdapter.CateData>()

    var db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_termslist)

        val pressed_category = intent.getStringExtra("category")
        val cates = listOf("정치", "사회", "군사","문화","경제","IT/과학")

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.logo_60) //제목앞에 아이콘 넣기
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

        val recyclerView_termsList = findViewById<RecyclerView>(R.id.recyclerView)
        termAdapter = TermAdapter(this)
        recyclerView_termsList.adapter = termAdapter
        recyclerView_termsList.addItemDecoration(VerticalItemDecorator_rv(5))
        recyclerView_termsList.addItemDecoration(HorizontalItemDecorator_rv(5))

        val intentWordDetail = Intent(this, WordDetailActivity::class.java)

        termAdapter.setOnItemClickListener(object: TermAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: TermData, pos: Int) {
                startActivity(intentWordDetail)
                //Toast.makeText( App.ApplicationContext(), "용어목록에서 용어 상세 설명 페이지로 전환됩니다.", Toast.LENGTH_SHORT ).show()
                /*
                Intent(this@MainActivity, ProfileDetailActivity::class.java).apply {
                    putExtra("data", data)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { startActivity(this) }
                 */
            }
        })

        terms.apply {
            add(TermData( name = "용어1입니다", meaning = "sampletext"))
            add(TermData( name = "term_sample_length_check", meaning = "sampletext"))
            add(TermData( name = "term_sample", meaning = "sampletext"))
            add(TermData( name = "term_sample_length_check", meaning = "sampletext"))
            add(TermData( name = "term_sample", meaning = "sampletext"))
            add(TermData( name = "term_sample_length_check", meaning = "sampletext"))
            add(TermData( name = "term_sample", meaning = "sampletext"))
            add(TermData( name = "term_sample_length_check", meaning = "sampletext"))
            termAdapter.termsList = terms
            termAdapter.notifyDataSetChanged()

        }


        //initRecycler()

        /*

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView_termsList.setLayoutManager(layoutManager)
        //val menuAdapter = MenuAdapter(menuArrayList,this)
        */



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
                Toast.makeText( App.ApplicationContext(), "용어목록의 카테고리가 변경됩니다", Toast.LENGTH_SHORT ).show()
                /*
                Intent(this@MainActivity, ProfileDetailActivity::class.java).apply {
                    putExtra("data", data)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { startActivity(this) }
                 */
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

//terms_list_page (by category)

