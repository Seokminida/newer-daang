package com.proj.newer_daang

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecentQuizAdapter(private val context: Context) : RecyclerView.Adapter<RecentQuizAdapter.ViewHolder>(){
    var quizList = mutableListOf<TermData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentQuizAdapter.ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_quiz,parent,false)
        //val view = LayoutInflater.from(context).inflate(R.layout.item_bookmark_2,parent,false)
        return ViewHolder(view)


    }

    override fun getItemCount(): Int = quizList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(quizList[position])
    }


    interface OnItemClickListener{
        fun onItemClick(v:View, data: TermData, pos : Int)
    }
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvTerm: TextView = itemView.findViewById(R.id.term)
        private val ivAnswer: ImageView = itemView.findViewById(R.id.answer)

        fun bind(item: TermData) {
            tvTerm.text = item.name

            /*
            if(item.answer == true){
                ivAnswer.setBackgroundResource(R.drawable.quiz_o)
            }
            else{
                ivAnswer.setBackgroundResource(R.drawable.quiz_x)
            }
            */

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