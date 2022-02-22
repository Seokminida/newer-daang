package com.proj.newer_daang

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ssomai.android.scalablelayout.ScalableLayout

class MainActivity : AppCompatActivity() {

    lateinit var quizPanelAdapter: QuizPanelAdapter
    var quizitemList = mutableListOf<TermData>()
    var answer = -1
    var refreshed = 0
    lateinit var question : TermData
    lateinit var tv_question : TextView
    var db = Firebase.firestore
    private var lastClickTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_2_1)



        val toolbar = findViewById<Toolbar>(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.logo_circle_40); //제목앞에 아이콘 넣기


        //bottom bar: onclick intent move
        val btnBookmark = findViewById<ImageButton>(R.id.bottombar_bookmark);
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

        //

        val tvBestWord = findViewById<TextView>(R.id.bestword);
        tvBestWord.setOnClickListener {
            val intentBestWord = Intent(this, WordDetailActivity::class.java)
            /*
            intentBestWord.apply{
                putExtra("name", data.name)
                putExtra("mean",data.meaning)
                startActivity(this)
            }
             */

            startActivity(intentBestWord)
        }

        //category: onclick intent move
        val tvCate_politics = findViewById<TextView>(R.id.cate_politics);
        tvCate_politics.setOnClickListener {
            val intentTermsList = Intent(this, TermsListActivity::class.java)
            intentTermsList.putExtra("category","0")
            startActivity(intentTermsList)
        }
        val tvCate_social = findViewById<TextView>(R.id.cate_social);
        tvCate_social.setOnClickListener {
            val intentTermsList = Intent(this, TermsListActivity::class.java)
            intentTermsList.putExtra("category","1")
            startActivity(intentTermsList)
        }
        val tvCate_economy = findViewById<TextView>(R.id.cate_economy);
        tvCate_economy.setOnClickListener {
            val intentTermsList = Intent(this, TermsListActivity::class.java)
            intentTermsList.putExtra("category","4")
            startActivity(intentTermsList)
        }
        val tvCate_culture = findViewById<TextView>(R.id.cate_culture);
        tvCate_culture.setOnClickListener {
            val intentTermsList = Intent(this, TermsListActivity::class.java)
            intentTermsList.putExtra("category","3")
            startActivity(intentTermsList)
        }
        val tvCate_military = findViewById<TextView>(R.id.cate_military);
        tvCate_military.setOnClickListener {
            val intentTermsList = Intent(this, TermsListActivity::class.java)
            intentTermsList.putExtra("category","2")
            startActivity(intentTermsList)
        }
        val tvCate_IT = findViewById<TextView>(R.id.cate_IT);
        tvCate_IT.setOnClickListener {
            val intentTermsList = Intent(this, TermsListActivity::class.java)
            intentTermsList.putExtra("category","5")
            startActivity(intentTermsList)
        }

        val slCate_politics = findViewById<ScalableLayout>(R.id.scalable_politics);
        slCate_politics.setOnClickListener {
            val intentTermsList = Intent(this, TermsListActivity::class.java)
            intentTermsList.putExtra("category","0")
            startActivity(intentTermsList)
        }
        val slCate_social = findViewById<ScalableLayout>(R.id.scalable_social);
        slCate_social.setOnClickListener {
            val intentTermsList = Intent(this, TermsListActivity::class.java)
            intentTermsList.putExtra("category","1")
            startActivity(intentTermsList)
        }
        val slCate_economy = findViewById<ScalableLayout>(R.id.scalable_economy);
        slCate_economy.setOnClickListener {
            val intentTermsList = Intent(this, TermsListActivity::class.java)
            intentTermsList.putExtra("category","4")
            startActivity(intentTermsList)
        }
        val slCate_culture = findViewById<ScalableLayout>(R.id.scalable_culture);
        slCate_culture.setOnClickListener {
            val intentTermsList = Intent(this, TermsListActivity::class.java)
            intentTermsList.putExtra("category","3")
            startActivity(intentTermsList)
        }
        val slCate_military = findViewById<ScalableLayout>(R.id.scalable_military);
        slCate_military.setOnClickListener {
            val intentTermsList = Intent(this, TermsListActivity::class.java)
            intentTermsList.putExtra("category","2")
            startActivity(intentTermsList)
        }
        val slCate_IT = findViewById<ScalableLayout>(R.id.scalable_IT);
        slCate_IT.setOnClickListener {
            val intentTermsList = Intent(this, TermsListActivity::class.java)
            intentTermsList.putExtra("category","5")
            startActivity(intentTermsList)
        }

        val imgCate_politics = findViewById<ImageView>(R.id.img_politics);
        imgCate_politics.setOnClickListener {
            val intentTermsList = Intent(this, TermsListActivity::class.java)
            intentTermsList.putExtra("category","0")
            startActivity(intentTermsList)
        }
        val imgCate_social = findViewById<ImageView>(R.id.img_social);
        imgCate_social.setOnClickListener {
            val intentTermsList = Intent(this, TermsListActivity::class.java)
            intentTermsList.putExtra("category","1")
            startActivity(intentTermsList)
        }
        val imgCate_economy = findViewById<ImageView>(R.id.img_economy);
        imgCate_economy.setOnClickListener {
            val intentTermsList = Intent(this, TermsListActivity::class.java)
            intentTermsList.putExtra("category","4")
            startActivity(intentTermsList)
        }
        val imgCate_culture = findViewById<ImageView>(R.id.img_culture);
        imgCate_culture.setOnClickListener {
            val intentTermsList = Intent(this, TermsListActivity::class.java)
            intentTermsList.putExtra("category","3")
            startActivity(intentTermsList)
        }
        val imgCate_military = findViewById<ImageView>(R.id.img_military);
        imgCate_military.setOnClickListener {
            val intentTermsList = Intent(this, TermsListActivity::class.java)
            intentTermsList.putExtra("category","2")
            startActivity(intentTermsList)
        }
        val imgCate_IT = findViewById<ImageView>(R.id.img_IT);
        imgCate_IT.setOnClickListener {
            val intentTermsList = Intent(this, TermsListActivity::class.java)
            intentTermsList.putExtra("category","5")
            startActivity(intentTermsList)
        }

        //get summary from firebase and set on listView


        val recyclerView_quiz = findViewById<RecyclerView>(R.id.quiz_panel);
        quizPanelAdapter = QuizPanelAdapter(this)
        recyclerView_quiz.adapter = quizPanelAdapter
        recyclerView_quiz.addItemDecoration(VerticalItemDecorator_rv(5))
        recyclerView_quiz.addItemDecoration(HorizontalItemDecorator_rv(5))

        quizPanelAdapter.answer = answer
        tv_question = findViewById<TextView>(R.id.question);
        quizPanelAdapter.setOnItemClickListener(object: QuizPanelAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: TermData, pos: Int) {

                //if (SystemClock.elapsedRealtime() - lastClickTime > 1500 || refreshed == 1) {
                if ( refreshed == 1) {
                    refreshed = 0

                    var beforePos = quizPanelAdapter.selectPos
                    quizPanelAdapter.selectPos = pos
                    quizPanelAdapter.clicked = 1
                    quizPanelAdapter.notifyItemChanged(beforePos)
                    quizPanelAdapter.notifyItemChanged(quizPanelAdapter.answer)
                    quizPanelAdapter.notifyItemChanged(quizPanelAdapter.selectPos)

                    //getQuizContents()
                    val handler = Handler()
                    handler.postDelayed(Runnable {
                        getQuizContents()
                        quizPanelAdapter.clicked = 0
                        quizPanelAdapter.selectPos = -1
                    }, 1000)
                }
                //lastClickTime = SystemClock.elapsedRealtime()

            }

            })

        getQuizContents()
        quizPanelAdapter.clicked = 0
        quizPanelAdapter.selectPos = -1

    }

    fun getQuizContents(){
        val docRef = db.collection("economy")
        docRef.get()
            .addOnSuccessListener {
                    document ->
                var count = 0
                quizitemList.clear()

                answer = (0..3).random()

                for(result in document){
                    if(count>=4) break
                    //아이템 뽑을때 랜덤으로 가져오기

                    val random = (1..2).random()
                    if(random == 1) {
                        val term_item = TermData(result["name"].toString(), result["meaning"].toString())
                        //Log.d("sampleshowshowshow",term_item.name)
                        if (count == answer){
                            question = term_item
                            //tv_question.text = (answer+1).toString() + term_item.meaning  //term_item.meaning
                            quizPanelAdapter.answer = answer
                        }
                        quizitemList.add(term_item)
                        count++
                    }
                }

                quizitemList.apply {
                    val handler = Handler()
                    handler.postDelayed(Runnable {
                        /*

                        if(!(tv_question.text.equals(quizitemList[answer].meaning))){
                            tv_question.text = quizitemList[answer].meaning
                        }
                         */
                        tv_question.text = question.meaning
                        quizPanelAdapter.optionList = quizitemList
                        quizPanelAdapter.notifyDataSetChanged()
                        refreshed = 1
                    }, 500)

                    //quizitemList.clear()
                }

            }
            .addOnFailureListener { exception ->
                Log.d("asd", "get failed with ", exception)
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

    override fun onBackPressed() {
        finishAffinity()
    }
}


//main_page




//하단바 액션(시작페이지, 로그인페이지 말고는 모두 등장)
