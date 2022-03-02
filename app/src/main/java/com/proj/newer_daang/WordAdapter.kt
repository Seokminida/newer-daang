package com.proj.newer_daang

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WordAdapter(private val context: Context):
    RecyclerView.Adapter<WordAdapter.ViewHolder>() {
    var datas = mutableListOf<ItemData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item,parent,false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val wordname: TextView = itemView.findViewById(R.id.wordname)

        fun bind(item: ItemData){
            wordname.text = item.name

            itemView.setOnClickListener {
                Intent(context, WordDetailActivity::class.java).apply {
                    putExtra("name", item.name)
                    putExtra("mean", item.meaning)
                    putExtra("category", item.category)
                    putExtra("hash",item.hashT)
                    putExtra("link",item.link)
                    putExtra("article",item.article)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { context.startActivity(this) }
            }
        }
    }

    var ite = ArrayList<ItemData>()
    fun filterList(filteredList: ArrayList<ItemData>) {
        ite = filteredList
        datas = ite;
        notifyDataSetChanged()
    }



}