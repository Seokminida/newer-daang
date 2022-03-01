package com.proj.newer_daang

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import com.google.firebase.Timestamp
import android.text.*
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview_ex.AfterReAdapter
import com.example.recyclerview_ex.RecentAdapter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_after_search.*
import kotlinx.android.synthetic.main.activity_after_search.searchBar
import kotlinx.android.synthetic.main.activity_search_2.*


class AfterRe : AppCompatActivity() {
    lateinit var reAdapter: AfterReAdapter
    lateinit var wordAdapter: WordAdapter
    lateinit var recentAdapter: RecentAdapter
    var datas = ArrayList<ItemData>()
    var datas2 = ArrayList<ItemData>()
    var datas3 = ArrayList<ItemData>()
    var redatas = ArrayList<ItemData>()
    var check_recnet = 0
    var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_search)

        var st = intent.getStringExtra("search").toString()
        var spannableString = SpannableString("\"$st\"" + " 에 대한 결과가 없습니다.")
        spannableString.setSpan(ForegroundColorSpan(getResources().getColor(R.color.orange_point)), 0, st.length+2, 0)
        no_search.text = spannableString



        initRecycler()
        initRecycler2()
        initRecycler3()


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
        val economy = db.collection("economy")
        economy.get()
            .addOnSuccessListener { document ->
                for (result in document) {
                    val insertD =
                        ItemData(result["name"].toString(), result["meaning"].toString(), result["hashtag"].toString(),result["article"].toString(),result["link"].toString())
                    datas3.add(insertD)
                }
            }
            .addOnFailureListener { exception ->
                Log.d("asd", "get failed with ", exception)
            }

        clearB2.setOnClickListener {
            searchBar.setText(null)
        }

        search2.setOnClickListener {
            val tmp = searchBar.text.toString()
            var date = Timestamp.now()
            val na = hashMapOf(
                "name" to tmp,
                "time" to date
            )
            //db에 최근검색어 추가
            if(tmp!="") {
                db.collection("user").document(Firebase.auth.uid.toString()).collection("최근검색어")
                    .document(tmp).set(na)
                    .addOnSuccessListener { documentReference ->

                    }
                    .addOnFailureListener { e ->
                        Log.w("firebaseadded", "Error adding document", e)
                    }
            }
            // 불러오기
            val docRecent = db.collection("user").document(Firebase.auth.uid.toString()).collection("최근검색어").orderBy("time",
                Query.Direction.DESCENDING)
            docRecent.get()
                .addOnSuccessListener {
                        document ->
                    redatas.clear()

                    var re_st: String
                    for(result in document){
                        re_st = result["name"].toString()
                        redatas.add(ItemData(result["name"].toString(), result["meaning"].toString(), result["hashtag"].toString(),result["article"].toString(),result["link"].toString()))
                    }


                    recentAdapter.recentList(redatas)

                }
                .addOnFailureListener { exception ->
                    Log.d("likesListSetUp", "get failed with ", exception)
                }
            
            Intent(this, AfterRe::class.java).apply {
                putExtra("afterdata", datas2)
                putExtra("search", searchBar.text.toString())
                startActivity(this)
            }
        }

        val politics = db.collection("politics")
        politics.get()
            .addOnSuccessListener { document ->
                for (result in document) {
                    val insertD =
                        ItemData(result["name"].toString(), result["meaning"].toString(), result["hashtag"].toString(),result["article"].toString(),result["link"].toString())
                    datas3.add(insertD)
                }
            }
            .addOnFailureListener { exception ->
                Log.d("asd", "get failed with ", exception)
            }

        val society = db.collection("society")
        society.get()
            .addOnSuccessListener { document ->
                for (result in document) {
                    val insertD =
                        ItemData(result["name"].toString(), result["meaning"].toString(), result["hashtag"].toString(),result["article"].toString(),result["link"].toString())
                    datas3.add(insertD)
                }
            }
            .addOnFailureListener { exception ->
                Log.d("asd", "get failed with ", exception)
            }

        recent_close_button2.setOnClickListener {
            if(recentRe2.visibility == View.GONE) {
                recentRe2.setVisibility(View.VISIBLE)
                recent_close_button2.text="최근 검색 닫기"
                check_recnet = 0
            }
            else if(recentRe2.visibility == View.VISIBLE)
            {
                recent_close_button2.text="최근 검색 열기"
                recentRe2.setVisibility(View.GONE)
                check_recnet = 1
            }
        }

        var check = 0
        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var searchT: String = p0.toString()
                if (searchT.length == 0) {
                    wordRe2.setVisibility(View.GONE)
                    clearB2.setVisibility(View.GONE)
                    lineView2.setVisibility(View.GONE)
                    scalableLayout3.setVisibility(View.VISIBLE)
                    recentRe2.setVisibility(View.VISIBLE)
                    if(check == 0)
                        sca.setVisibility(View.VISIBLE)

                    if(check_recnet == 0)
                        recentRe2.setVisibility(View.VISIBLE)
                    else
                        recentRe2.setVisibility(View.GONE)

                } else {
                    check = 1
                    scalableLayout3.setVisibility(View.GONE)
                    sca.setVisibility(View.GONE)
                    clearB2.setVisibility(View.VISIBLE)
                    wordRe2.setVisibility(View.VISIBLE)
                    lineView2.setVisibility(View.VISIBLE)
                    recentRe2.setVisibility(View.GONE)

                }
                searchFilter(searchT)
            }

            override fun afterTextChanged(p0: Editable?) {
                var searchT: String = p0.toString()
                searchFilter(searchT)
            }
        })

        searchBar.setOnFocusChangeListener(object : View.OnFocusChangeListener{
            override fun onFocusChange(view: View, hasFocus: Boolean) {
                if(hasFocus){
                    check = 1
                    scalableLayout3.setVisibility(View.VISIBLE)
                    recent_textView2.setVisibility(View.VISIBLE)
                    recent_close_button2.setVisibility(View.VISIBLE)
                    recentRe2.setVisibility(View.VISIBLE)
                    sca.setVisibility(View.GONE)
                } else
                {
                    check = 0
                    scalableLayout3.setVisibility(View.GONE)
                    recent_textView2.setVisibility(View.GONE)
                    recent_close_button2.setVisibility(View.GONE)
                    recentRe2.setVisibility(View.GONE)
                    sca.setVisibility(View.VISIBLE)
                }
            }
        })
    }

    override fun onResume(){
        super.onResume()
        initRecycler2()

    }


    @SuppressLint("ResourceAsColor")
    private fun initRecycler() {
        var ch = intent.getIntExtra("ch", 0)
        if (ch == 0) { // 검색 후 아이템 클릭
            reAdapter = AfterReAdapter(this)
            val afterrec: RecyclerView = findViewById(R.id.after_search)
            afterrec.adapter = reAdapter
            datas = intent.getSerializableExtra("afterdata") as ArrayList<ItemData>
            reAdapter.datas = datas

            if (reAdapter.datas.size == 0) {
                search2.setVisibility(View.VISIBLE)
                back_icon2.setVisibility(View.VISIBLE)
                sca.setVisibility(View.VISIBLE)
                after_search.setVisibility(View.GONE)
                no_search.setVisibility(View.VISIBLE)
                birdI.setVisibility(View.VISIBLE)
                toolbar.setVisibility(View.GONE)
                searchBar.setVisibility(View.VISIBLE)
            } else {
                val toolbar = findViewById<Toolbar>(R.id.toolbar)
                setSupportActionBar(toolbar)
                toolbar.setNavigationIcon(R.drawable.logo_circle_40) //제목앞에 아이콘 넣기
                toolbar.setNavigationOnClickListener(View.OnClickListener {
                    finish()
                })
                toolbar.title = intent.getStringExtra("search")
                search2.setVisibility(View.GONE)
                back_icon2.setVisibility(View.GONE)
                searchBar.setVisibility(View.GONE)
                toolbar.setVisibility(View.VISIBLE)
                sca.setVisibility(View.GONE)
                after_search.setVisibility(View.VISIBLE)
                no_search.setVisibility(View.GONE)
                birdI.setVisibility(View.GONE)
                lay1.setBackgroundResource(R.color.white)
            }
            reAdapter.notifyDataSetChanged()
        } else //최근검색 아이템 클릭
        {
            var click_name = intent.getStringExtra("name2").toString()
            val toolbar = findViewById<Toolbar>(R.id.toolbar)
            setSupportActionBar(toolbar)
            toolbar.setNavigationIcon(R.drawable.logo_circle_40) //제목앞에 아이콘 넣기
            toolbar.setNavigationOnClickListener(View.OnClickListener {
                finish()
            })
            toolbar.title = click_name
            reAdapter = AfterReAdapter(this)
            val afterrec: RecyclerView = findViewById(R.id.after_search)
            afterrec.adapter = reAdapter
            datas = intent.getSerializableExtra("array") as ArrayList<ItemData>
            reAdapter.datas = datas

            if (reAdapter.datas.size == 0) {
                search2.setVisibility(View.VISIBLE)
                back_icon2.setVisibility(View.VISIBLE)
                sca.setVisibility(View.VISIBLE)
                after_search.setVisibility(View.GONE)
                no_search.setVisibility(View.VISIBLE)
                birdI.setVisibility(View.VISIBLE)
                toolbar.setVisibility(View.GONE)
                searchBar.setVisibility(View.VISIBLE)
                var spannableString = SpannableString("\"$click_name\"" + " 에 대한 결과가 없습니다.")
                spannableString.setSpan(ForegroundColorSpan(getResources().getColor(R.color.orange_point)), 0, click_name.length+2, 0)
                no_search.text = (spannableString)
            } else {

                search2.setVisibility(View.GONE)
                back_icon2.setVisibility(View.GONE)
                searchBar.setVisibility(View.GONE)
                toolbar.setVisibility(View.VISIBLE)
                sca.setVisibility(View.GONE)
                after_search.setVisibility(View.VISIBLE)
                no_search.setVisibility(View.GONE)
                birdI.setVisibility(View.GONE)
                lay1.setBackgroundResource(R.color.white)
            }
            reAdapter.notifyDataSetChanged()
        }

    }

    private fun initRecycler2() {
        recentAdapter = RecentAdapter(this)
        val recentrec: RecyclerView = findViewById(R.id.recentRe2)
        recentrec.adapter = recentAdapter
        val customDecoration = RecyclerDecoration(3f, 25f, Color.parseColor("#D7EBEA")) //aqua 색으로 바꾸기
        recentrec.addItemDecoration(customDecoration)

        val docRecent = db.collection("user").document(Firebase.auth.uid.toString()).collection("최근검색어").orderBy("time",
            Query.Direction.DESCENDING)
        docRecent.get()
            .addOnSuccessListener {
                    document ->
                redatas.clear()

                var re_st: String
                for(result in document){
                    re_st = result["name"].toString()
                    redatas.add(ItemData(result["name"].toString(), result["meaning"].toString(), result["hashtag"].toString(),result["article"].toString(),result["link"].toString()))
                }
                recentAdapter.recentList(redatas)

            }
            .addOnFailureListener { exception ->
                Log.d("likesListSetUp", "get failed with ", exception)
            }

    }

    private fun initRecycler3() { //연관검색
        wordAdapter = WordAdapter(this)
        val wordrec: RecyclerView = findViewById(R.id.wordRe2)
        wordrec.adapter = wordAdapter
        val customDecoration = RecyclerDecoration(3f, 25f, Color.parseColor("#D7EBEA")) //aqua 색으로 바꾸기

        wordrec.addItemDecoration(customDecoration)

        datas3.apply {
            wordAdapter.datas = datas3
            wordAdapter.notifyDataSetChanged()
        }

    }


    fun searchFilter(str: String) {
        datas2.clear()

        if (str.length != 0) {
            for (i in 0 until datas3.size) {
                if (str.length > datas3[i].name.length)
                    break
                var check = 1
                if (str[0] == '#') {
                    if (str == datas3[i].hashT)
                        datas2.add(datas3[i])
                } else {
                    for (j in 0 until str.length) {
                        if (str[j] != datas3[i].name[j]) {
                            check = 0
                            break
                        }
                    }
                    if (check == 1)
                        datas2.add(datas3[i])
                }
            }
        }
        if (str.length > 1) {
            for (i in 0 until datas3.size) {
                if (datas3[i].name
                        .contains(str)
                ) {
                    if (datas2.contains(datas3[i]))
                        continue
                    datas2.add(datas3[i])
                }
            }
        }

        wordAdapter.filterList(datas2)
    }

    fun back(v: View) {
        /*
        val intentSearch = Intent(this, SearchActivity::class.java)
        startActivity(intentSearch)
         */
        finish()
    }

    fun search(v: View) {
        val intentSearch = Intent(this, SearchActivity::class.java)
        startActivity(intentSearch)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 클릭된 메뉴 아이템의 아이디 마다 when 구절로 클릭시 동작을 설정한다.
        when (item!!.itemId) {
            R.id.app_bar_search -> { // 메뉴 버튼
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



