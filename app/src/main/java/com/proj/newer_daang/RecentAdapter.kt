package com.example.recyclerview_ex

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.proj.newer_daang.ItemData
import com.proj.newer_daang.R
import com.proj.newer_daang.WordDetailActivity
import kotlinx.android.synthetic.main.item_recent.view.*

class RecentAdapter(private val context: Context) : RecyclerView.Adapter<RecentAdapter.ViewHolder>() {

    var datas = ArrayList<ItemData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recent,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val recentW: TextView = itemView.findViewById(R.id.recent_word)


        fun bind(item: ItemData) {
            recentW.text = item.name
            itemView.recent_delete.setOnClickListener{

            }
        }
    }
    var ite = ArrayList<ItemData>()
    fun recentList(recentList: ArrayList<ItemData>) {
        ite = recentList
        datas = ite;
        notifyDataSetChanged()
    }



}