package com.proj.newer_daang

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import androidx.core.content.ContextCompat.startActivity

import android.content.Intent
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ssomai.android.scalablelayout.ScalableLayout


class QuizPanelAdapter(private val context: Context) : RecyclerView.Adapter<QuizPanelAdapter.ViewHolder>() {
    var optionList = mutableListOf<TermData>()
    var selectPos = -1
    var answer = -1
    var clicked = 0

    var db = Firebase.firestore

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizPanelAdapter.ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_quizpanel,parent,false)
        return ViewHolder(view)


    }

    override fun getItemCount(): Int = optionList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(optionList[position])
        if(selectPos == position) {
            if(answer!=selectPos)
                holder.itemView.setBackgroundResource(R.color.orange_point)

            else{
                holder.itemView.setBackgroundResource(R.color.aqua)
            }
        } else {
            if(answer == position && clicked==1 ){
                holder.itemView.setBackgroundResource(R.color.aqua)
                //clicked = 0
            }

            else
                holder.itemView.setBackgroundResource(R.color.white)
        }
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

        fun bind(item: TermData) {
            tvTerm.text = item.name

            val pos = adapterPosition
            if(pos!= RecyclerView.NO_POSITION)
            {
                itemView.setOnClickListener {
                    listener?.onItemClick(itemView,item,pos)
                    notifyDataSetChanged()
                }
            }

        }



    }

}