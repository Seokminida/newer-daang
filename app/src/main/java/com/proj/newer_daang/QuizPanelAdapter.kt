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
import android.graphics.Color
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.ssomai.android.scalablelayout.ScalableLayout


class QuizPanelAdapter(private val context: Context) : RecyclerView.Adapter<QuizPanelAdapter.ViewHolder>() {
    var optionList = mutableListOf<ItemData>()
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

            //being clicked
            if(answer!=selectPos) {
                //wrong answer
                holder.itemView.setBackgroundResource(R.color.orange_point)

                val tvTerm: TextView = holder.itemView.findViewById(R.id.term)
                tvTerm.setTextColor(ContextCompat.getColor(context!!, R.color.darker_gray))
                val user = hashMapOf(
                    "ans" to "false",
                    "name" to optionList[answer].name,
                    "time" to Timestamp.now()
                )
                db.collection("user").document(Firebase.auth.uid.toString()).collection("최근 푼 퀴즈").document(optionList[answer].name)
                    .set(user)
                    .addOnSuccessListener { Log.d("firebasedeleted", "DocumentSnapshot successfully deleted!") }
                    .addOnFailureListener { e -> Log.w("firebasedeleted", "Error deleting document", e) }


            }

            else{
                //correct answer
                holder.itemView.setBackgroundResource(R.color.aqua)
                val tvTerm: TextView = holder.itemView.findViewById(R.id.term)
                tvTerm.setTextColor(ContextCompat.getColor(context!!, R.color.darker_gray))
                val user = hashMapOf(
                    "ans" to "true",
                    "name" to tvTerm.text,
                    "time" to Timestamp.now()
                )
                db.collection("user").document(Firebase.auth.uid.toString()).collection("최근 푼 퀴즈").document(optionList[answer].name)
                    .set(user)
                    .addOnSuccessListener { Log.d("firebasedeleted", "DocumentSnapshot successfully deleted!") }
                    .addOnFailureListener { e -> Log.w("firebasedeleted", "Error deleting document", e) }



            }
        } else {
            //not being clicked
            if(answer == position && clicked==1 ){
                holder.itemView.setBackgroundResource(R.color.aqua)
                val tvTerm: TextView = holder.itemView.findViewById(R.id.term)
                tvTerm.setTextColor(ContextCompat.getColor(context!!, R.color.darker_gray))
            }

            else {
                holder.itemView.setBackgroundResource(R.color.white_box)
                val tvTerm: TextView = holder.itemView.findViewById(R.id.term)
                tvTerm.setTextColor(ContextCompat.getColor(context!!, R.color.darker_gray_to_white))
            }
        }
    }


    interface OnItemClickListener{
        fun onItemClick(v:View, data: ItemData, pos : Int)
    }
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvTerm: TextView = itemView.findViewById(R.id.term)

        fun bind(item: ItemData) {
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