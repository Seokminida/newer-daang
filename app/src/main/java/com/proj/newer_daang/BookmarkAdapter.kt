package com.proj.newer_daang

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookmarkAdapter(private val context: Context) : RecyclerView.Adapter<BookmarkAdapter.ViewHolder>(){
    var bookmarkList = mutableListOf<BookmarkData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkAdapter.ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_bookmark_2,parent,false)
        //val view = LayoutInflater.from(context).inflate(R.layout.item_bookmark_2,parent,false)
        return ViewHolder(view)


    }

    override fun getItemCount(): Int = bookmarkList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(bookmarkList[position])
    }


    interface OnItemClickListener{
        fun onItemClick(v:View, data: BookmarkData, pos : Int)
    }
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvTerm: TextView = itemView.findViewById(R.id.term)
        private val tvTerm_meaning: TextView = itemView.findViewById(R.id.term_meaning)
        private val tvCategory: TextView = itemView.findViewById(R.id.term_cate)
        private val tvTag1: TextView = itemView.findViewById(R.id.term_tag1)
        //private val tvTag2: TextView = itemView.findViewById(R.id.term_tag2)

        fun bind(item: BookmarkData) {
            tvTerm.text = item.name
            tvTerm_meaning.text = item.meaning
            tvCategory.text = item.category
            tvTag1.text = item.tag1
            //tvTag2.text = item.tag2


            //Glide.with(itemView).load(item.img).into(imgProfile)
            val pos = adapterPosition
            if(pos!= RecyclerView.NO_POSITION)
            {
                itemView.setOnClickListener {
                    listener?.onItemClick(itemView,item,pos)
                }
            }

        }



    }

}