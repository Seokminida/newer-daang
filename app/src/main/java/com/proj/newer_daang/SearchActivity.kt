package com.proj.newer_daang

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.search_activity.*

class SearchActivity : AppCompatActivity(){
    lateinit var wordAdapter: WordAdapter
    val datas = ArrayList<ItemData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_activity)
        initRecycler()
        val actionBar = supportActionBar

        clearB.setOnClickListener{
            searchBar.setText(null)
        }

        search.setOnClickListener{
            Intent(this, AfterRe::class.java).apply{
                putExtra("afterdata",datas2)
                putExtra("search",searchBar.text.toString())
                startActivity(this)
            }
        }

        if (actionBar != null) {
            actionBar.hide()
        }
        searchBar.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var searchT: String = p0.toString()
                if(searchT.length == 0)
                {
                    clearB.setVisibility(View.GONE)
                    wordRe.setVisibility(View.GONE)
                    recent.setVisibility(View.VISIBLE)
                }
                else {
                    clearB.setVisibility(View.VISIBLE)
                    wordRe.setVisibility(View.VISIBLE)
                    recent.setVisibility(View.GONE)
                }
                searchFilter(searchT)
            }

            override fun afterTextChanged(p0: Editable?) {
                var searchT: String = p0.toString()
                searchFilter(searchT)
            }
        })

    }
    private fun initRecycler(){
        wordAdapter = WordAdapter(this)
        val wordrec: RecyclerView = findViewById(R.id.wordRe)
        wordrec.adapter = wordAdapter


        datas.apply{
            add(ItemData("가나다라","예시11231231231231231231asdasdasdasdsad23"))
            add(ItemData("나다라마바","예시2"))
            add(ItemData("가다마아자","예시3"))
            wordAdapter.datas = datas
            wordAdapter.notifyDataSetChanged()
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
                for (j in 0 until str.length){
                    if(str[j] != datas[i].name[j]){
                        check = 0
                        break
                    }
                }
                if(check == 1 )
                    datas2.add(datas[i])
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

        wordAdapter.filterList(datas2)
    }
    fun back(v : View) {
        val intent = Intent()
        finish()
    }



}