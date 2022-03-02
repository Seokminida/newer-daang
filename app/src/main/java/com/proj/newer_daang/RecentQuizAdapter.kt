package com.proj.newer_daang

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RecentQuizAdapter(private val context: Context) : RecyclerView.Adapter<RecentQuizAdapter.ViewHolder>(){
    var quizList = mutableListOf<QuizData>()
    val db = Firebase.firestore
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
        fun onItemClick(v:View, data: QuizData, pos: Int)
    }
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvTerm: TextView = itemView.findViewById(R.id.term)
        private val ivAnswer: ImageView = itemView.findViewById(R.id.answer)

        fun bind(item: QuizData) {
            tvTerm.text = item.name
            if(item.answer.equals("true")){
                ivAnswer.setImageResource(R.drawable.quiz_o)
            }
            else{
                ivAnswer.setImageResource(R.drawable.quiz_x)
            }


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