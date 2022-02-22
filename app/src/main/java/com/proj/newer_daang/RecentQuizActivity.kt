package com.proj.newer_daang

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView

class RecentQuizActivity : AppCompatActivity() {

    lateinit var recentQuizAdapter: RecentQuizAdapter
    val quizList = mutableListOf<TermData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val toolbar = findViewById<Toolbar>(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.logo_circle_40); //제목앞에 아이콘 넣기

        val btnHome = findViewById<ImageButton>(R.id.bottombar_home);
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




        val recyclerView_quiz = findViewById<RecyclerView>(R.id.recyclerView_bookmark)
        recentQuizAdapter = RecentQuizAdapter(this)
        recyclerView_quiz.adapter = recentQuizAdapter
        recyclerView_quiz.addItemDecoration(VerticalItemDecorator_rv(3))
        recyclerView_quiz.addItemDecoration(HorizontalItemDecorator_rv(5))

        val intentWordDetail = Intent(this, WordDetailActivity::class.java)

        recentQuizAdapter.setOnItemClickListener(object: RecentQuizAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: TermData, pos: Int) {
                intentWordDetail.apply{
                    putExtra("name", data.name)
                    putExtra("mean",data.meaning)
                    startActivity(this)
                }
                //Toast.makeText( App.ApplicationContext(), "북마크에서 용어 상세 설명 페이지로 전환됩니다.", Toast.LENGTH_SHORT ).show()
            }
        })

        quizList.apply {
            add(TermData( name = "용어북마크1", meaning = "근로자는 근로조건의 향상을 위하여 자주적인 단결권·단체교섭권 및 단체행동권을 가진다. "))
            add(TermData( name = "용어북마크2입니다", meaning = "이 헌법공포 당시의 국회의원의 임기는 제1항에 의한 국회의 최초의 집회일 전일까지로 한다."))
            add(TermData( name = "용어북마크3", meaning = "광물 기타 중요한 지하자원·수산자원·수력과 경제상 이용할 수 있는 자연력은 법률이 정하는 바에 의하여 일정한 기간 그 채취·개발 또는 이용을 특허할 수 있다."))
            add(TermData( name = "term_sample_length_check", meaning = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum a dolor luctus, fringilla est sed, volutpat eros. Vivamus tristique gravida."))
            add(TermData( name = "term_sample", meaning = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum a dolor luctus."))
            add(TermData( name = "term_sample_length_check", meaning = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum a dolor luctus, fringilla est sed, volutpat eros. Vivamus tristique gravida."))
            add(TermData( name = "term_sample", meaning = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum a dolor luctus."))
            add(TermData( name = "term_sample_length_check", meaning = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum a dolor luctus, fringilla est sed, volutpat eros. Vivamus tristique gravida."))

            recentQuizAdapter.quizList = quizList
            recentQuizAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.func_nothing, menu)
        return true
    }

}

//bookmark_page

