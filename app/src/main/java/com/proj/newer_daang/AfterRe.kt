package com.proj.newer_daang

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview_ex.AfterReAdapter
import kotlinx.android.synthetic.main.after.*

class AfterRe : AppCompatActivity() {
    lateinit var reAdapter: AfterReAdapter
    var datas = ArrayList<ItemData>()


    override fun onCreate(savedInstanceState: Bundle?) {
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.after)
        var st = intent.getStringExtra("search")
        textView3.text = intent.getStringExtra("search")
        no_search.text = ("\"$st\"" +" 에 대한 결과가 없습니다.")
        initRecycler()
    }


    private fun initRecycler() {
        reAdapter = AfterReAdapter(this)
        val afterrec: RecyclerView = findViewById(R.id.after_search)
        afterrec.adapter = reAdapter
        datas = intent.getSerializableExtra("afterdata") as ArrayList<ItemData>
        reAdapter.datas = datas
        if(reAdapter.datas.size == 0){
            sca.setVisibility(View.VISIBLE)
            after_search.setVisibility(View.GONE)
            no_search.setVisibility(View.VISIBLE)
            birdI.setVisibility(View.VISIBLE)
        }
        else{
            sca.setVisibility(View.GONE)
            after_search.setVisibility(View.VISIBLE)
            no_search.setVisibility(View.GONE)
            birdI.setVisibility(View.GONE)
        }
        reAdapter.notifyDataSetChanged()


    }
}