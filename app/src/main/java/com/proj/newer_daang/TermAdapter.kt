package com.proj.newer_daang

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class TermAdapter(private val context: Context) : RecyclerView.Adapter<TermAdapter.ViewHolder>(){
    var termsList = ArrayList<TermData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TermAdapter.ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_termslist,parent,false)
        return ViewHolder(view)


    }

    override fun getItemCount(): Int = termsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(termsList[position])
    }


    interface OnItemClickListener{
        fun onItemClick(v:View, data: TermData, pos : Int)
    }
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvTerm: TextView = itemView.findViewById(R.id.term_name)
        //private val imgBookmark: ImageButton = itemView.findViewById(R.id.heart)

        fun bind(item: TermData) {
            tvTerm.text = item.name
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