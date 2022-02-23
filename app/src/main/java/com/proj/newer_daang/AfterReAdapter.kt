package com.example.recyclerview_ex

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.proj.newer_daang.ItemData
import com.proj.newer_daang.R
import com.proj.newer_daang.WordDetailActivity

class AfterReAdapter(private val context: Context) : RecyclerView.Adapter<AfterReAdapter.ViewHolder>() {

    var datas = ArrayList<ItemData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_after_search_2,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val wna: TextView = itemView.findViewById(R.id.wna)
        private val wme: TextView = itemView.findViewById(R.id.wme)
        private val whash: TextView = itemView.findViewById(R.id.hashT)


        fun bind(item: ItemData) {
            wna.text = item.name
            wme.text = item.mean
            whash.text = item.hashT
            itemView.setOnClickListener {
                Intent(context, WordDetailActivity::class.java).apply {
                    putExtra("name", item.name)
                    putExtra("mean", item.mean)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { context.startActivity(this) }
            }
        }

    }


}