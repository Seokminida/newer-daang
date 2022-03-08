package com.proj.newer_daang

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview_ex.RecentAdapter
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_search_2.*
import kotlinx.android.synthetic.main.activity_termslist.*
import kotlinx.android.synthetic.main.item_recent.*
import java.util.*
import kotlin.collections.ArrayList

class SearchActivity : AppCompatActivity(){
    lateinit var wordAdapter: WordAdapter
    lateinit var recentAdapter: RecentAdapter
    val datas = ArrayList<ItemData>()
    val redatas = ArrayList<ItemData>()
    var db = Firebase.firestore
    var check = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_2)
        initRecycler()
        initRecycler2()
        recentAdapter.listUpdate()
        val pref = this.getSharedPreferences("ch",0)
        val editor = pref.edit()

        val economy = db.collection("economy")
        economy.get()
            .addOnSuccessListener { document ->
                for(result in document){
                    val insertD = ItemData(result["name"].toString(), result["meaning"].toString(), "economy",result["hashtag"].toString(),result["article"].toString(),result["link"].toString())
                    datas.add(insertD)
                }
            }
            .addOnFailureListener { exception ->
                Log.d("asd", "get failed with ", exception)
            }

        val politics = db.collection("politics")
        politics.get()
            .addOnSuccessListener { document ->
                for(result in document){
                    val insertD = ItemData(result["name"].toString(), result["meaning"].toString(), "politics",result["hashtag"].toString(),result["article"].toString(),result["link"].toString())
                    datas.add(insertD)
                }
            }
            .addOnFailureListener { exception ->
                Log.d("asd", "get failed with ", exception)
            }

        val society = db.collection("society")
        society.get()
            .addOnSuccessListener { document ->
                for(result in document){
                    val insertD = ItemData(result["name"].toString(), result["meaning"].toString(), "society",result["hashtag"].toString(),result["article"].toString(),result["link"].toString())
                    datas.add(insertD)
                }
            }
            .addOnFailureListener { exception ->
                Log.d("asd", "get failed with ", exception)
            }

        val it_science = db.collection("it_science")
        it_science.get()
            .addOnSuccessListener { document ->
                for(result in document){
                    val insertD = ItemData(result["name"].toString(), result["meaning"].toString(), "society",result["hashtag"].toString(),result["article"].toString(),result["link"].toString())
                    datas.add(insertD)
                }
            }
            .addOnFailureListener { exception ->
                Log.d("asd", "get failed with ", exception)
            }

        val culture = db.collection("culture")
        culture.get()
            .addOnSuccessListener { document ->
                for(result in document){
                    val insertD = ItemData(result["name"].toString(), result["meaning"].toString(), "society",result["hashtag"].toString(),result["article"].toString(),result["link"].toString())
                    datas.add(insertD)
                }
            }
            .addOnFailureListener { exception ->
                Log.d("asd", "get failed with ", exception)
            }

        val military = db.collection("military")
        military.get()
            .addOnSuccessListener { document ->
                for(result in document){
                    val insertD = ItemData(result["name"].toString(), result["meaning"].toString(), "society",result["hashtag"].toString(),result["article"].toString(),result["link"].toString())
                    datas.add(insertD)
                }
            }
            .addOnFailureListener { exception ->
                Log.d("asd", "get failed with ", exception)
            }

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
            var check_re = pref.getString("recent_c","1").toString()
            Log.d("asdasd",check_re)
            if(check_re.toInt() == 1) {
                recent.setVisibility(View.VISIBLE)
                recent_close_button.text="최근 검색 닫기"
                editor.putString("recent_c","0").apply()
                check = 0
            }
            else if(check_re.toInt() == 0)
            {
                recent_close_button.text="최근 검색 열기"
                recent.setVisibility(View.GONE)
                editor.putString("recent_c","1").apply()
                check = 1
            }
        }

        search.setOnClickListener{
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
                        Log.d("firebaseaddedRecent", "succeccful")
                    }
                    .addOnFailureListener { e ->
                        Log.w("firebaseaddedRecent", "Error adding document", e)
                    }
            }
            // 불러오기
            //-----------------------------------------------------------------------------------------------------------------------------------------------------------------
            val docRecent = db.collection("user").document(Firebase.auth.uid.toString()).collection("최근검색어").orderBy("time",
                Query.Direction.DESCENDING)
            docRecent.get()
                .addOnSuccessListener {
                        document ->
                    redatas.clear()

                    var re_st: String
                    for(result in document){
                        re_st = result["name"].toString()
                        //----------------------------------------------------------------------------------ItemData 에 category 항목 추가함에 따라 일단 떼워놨습니다
                        redatas.add(ItemData(result["name"].toString(), result["meaning"].toString(), "",result["hashtag"].toString(),result["article"].toString(),result["link"].toString()))
                    }


                    recentAdapter.recentList(redatas)

                }
                .addOnFailureListener { exception ->
                    Log.d("likesListSetUp", "get failed with ", exception)
                }


            Intent(this, AfterRe::class.java).apply{
                Log.d("aaaaab","$datas2")
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
                    if(check == 0)
                        recent.setVisibility(View.VISIBLE)
                    else
                        recent.setVisibility(View.GONE)

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

        searchBar.setOnFocusChangeListener(object : View.OnFocusChangeListener{
            override fun onFocusChange(view: View, hasFocus: Boolean) {
                if(hasFocus){
                    recent_textView.setVisibility(View.VISIBLE)
                    recent_close_button.setVisibility(View.VISIBLE)
                    var check_re = pref.getString("recent_c","1").toString()
                    if(check_re.toInt() == 0) {
                        recent.setVisibility(View.VISIBLE)
                        recent_close_button.text="최근 검색 닫기"
                        check = 0
                    }
                    else if(check_re.toInt() == 1)
                    {
                        recent_close_button.text="최근 검색 열기"
                        recent.setVisibility(View.GONE)
                        check = 1
                    }
                } else
                {
                    recent_textView.setVisibility(View.GONE)
                    recent_close_button.setVisibility(View.GONE)
                    recent.setVisibility(View.GONE)
                }
            }
        })

    }
    override fun onResume(){
        super.onResume()
        initRecycler2()
        recentAdapter.listUpdate()
        searchBar.clearFocus()
        recent.setVisibility(View.GONE)

    }
    private fun initRecycler(){
        wordAdapter = WordAdapter(this)
        val wordrec: RecyclerView = findViewById(R.id.wordRe)
        wordrec.adapter = wordAdapter
        val customDecoration = RecyclerDecoration(3f, 25f, Color.parseColor("#D7EBEA")) //aqua색으로 바꾸기
        wordrec.addItemDecoration(customDecoration)

        datas.apply{
            wordAdapter.datas = datas
            wordAdapter.notifyDataSetChanged()
        }
    }


    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------
    private fun initRecycler2(){
        recentAdapter = RecentAdapter(this)
        val recentrec: RecyclerView = findViewById(R.id.recent)
        val customDecoration = RecyclerDecoration(3f, 25f, Color.parseColor("#D7EBEA"))
        recentrec.addItemDecoration(customDecoration)
        recentrec.adapter = recentAdapter
        val docRecent = db.collection("user").document(Firebase.auth.uid.toString()).collection("최근검색어").orderBy("time",
            Query.Direction.DESCENDING)
        docRecent.get()
            .addOnSuccessListener {
                    document ->
                redatas.clear()

                var re_st: String
                for(result in document){
                    re_st = result["name"].toString()
                    //----------------------------------------------------------------------------------ItemData 에 category 항목 추가함에 따라 일단 떼워놨습니다
                    redatas.add(ItemData(result["name"].toString(), result["meaning"].toString(),"category is needed", result["hashtag"].toString(),result["article"].toString(),result["link"].toString()))
                }

                recentAdapter.recentList(redatas)

            }
            .addOnFailureListener { exception ->
                Log.d("likesListSetUp", "get failed with ", exception)
            }

    }

    var datas2 = ArrayList<ItemData>()

    fun searchFilter(str : String){
        datas2.clear()

        if(str.length != 0) {
            for (i in 0 until datas.size) {
                if(str.length > datas[i].name.length)
                    break
                var check = 1
                if(str[0] == '#'){
                    if(str == datas[i].hashT)
                        datas2.add(datas[i])
                }
                else {
                    for (j in 0 until str.length) {
                        if (str[j] != datas[i].name[j]) {
                            check = 0
                            break
                        }
                    }
                    if (check == 1)
                        datas2.add(datas[i])
                }
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
        Log.d("aaaaa","$datas2")
        wordAdapter.filterList(datas2)
    }
    fun back(v : View) {
        val intent = Intent()
        finish()
    }


}