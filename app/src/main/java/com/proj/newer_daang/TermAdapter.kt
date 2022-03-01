package com.proj.newer_daang

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.extensions.LayoutContainer

class TermAdapter(private val context: Context) : RecyclerView.Adapter<TermAdapter.ViewHolder>(){
    var termsList = ArrayList<TermData>()
    var likesList = ArrayList<String>()
    val db = Firebase.firestore

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TermAdapter.ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_termslist,parent,false)


        return ViewHolder(view)


    }

    override fun getItemCount(): Int = termsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(termsList[position])

        holder.imgLike.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v:View){
                var tmp = termsList[holder.adapterPosition].name
                Log.d("likes?", "test on clicked "+ " "+ tmp)

                //if already in likes
                var intheList = false
/*
                val docRef = db.collection("user").document(Firebase.auth.uid.toString()).collection("좋아요")
                docRef.get()
                    .addOnSuccessListener {
                            document ->
                        likesList.clear()
                        for(result in document){
                            val term_item = result["name"].toString()
                            likesList.add(term_item)
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d("likesListSetUp", "get failed with ", exception)
                    }
 */

                //해당 아이템의 필드값이 좋아요 = true 이면 ㄱㄱ
                for(i in likesList.indices){
                    if(tmp.equals(likesList[i])){
                        intheList = true
                        likesList.removeAt(i)
                        break;
                    }
                }

                if(intheList){
                    Toast.makeText(App.ApplicationContext(), "삭제됨 "+tmp + " "+ holder.adapterPosition, Toast.LENGTH_SHORT).show()
                    holder.imgLike.setImageResource(R.drawable.heart_empty)
                    db.collection("user").document(Firebase.auth.uid.toString()).collection("좋아요").document(tmp)
                        .delete()
                        .addOnSuccessListener { Log.d("firebasedeleted", "DocumentSnapshot successfully deleted!") }
                        .addOnFailureListener { e -> Log.w("firebasedeleted", "Error deleting document", e) }

                }
                //if not in likes yet
                else{
                    Toast.makeText(App.ApplicationContext(), "추가됨 "+tmp + " "+ holder.adapterPosition, Toast.LENGTH_SHORT).show()
                    holder.imgLike.setImageResource(R.drawable.heart_filled)
                    val user = hashMapOf(
                        "like" to "true",
                        "name" to tmp,
                    )
                    // Add a new document with a generated ID
                    db.collection("user").document(Firebase.auth.uid.toString()).collection("좋아요")
                        .document(tmp).set(user)
                        .addOnSuccessListener { documentReference ->
                            Log.d("firebaseadded", "DocumentSnapshot added with "+tmp)
                            //Log.d("firebaseadded", "DocumentSnapshot added with ID: ${documentReference.id}")
                        }
                        .addOnFailureListener { e ->
                            Log.w("firebaseadded", "Error adding document", e)
                        }
                    likesList.add(tmp)

                }





            }
        })
    }


    interface OnItemClickListener{
        fun onItemClick(v:View, data: TermData, pos : Int)
    }
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTerm: TextView = itemView.findViewById(R.id.term)
        val imgLike: ImageButton = itemView.findViewById(R.id.heart)

        fun bind(item: TermData) {
            tvTerm.text = item.name
            var intheList = false

            //해당 아이템의 필드값이 좋아요 = true 이면 ㄱㄱ
            for(i in likesList.indices){
                if(item.name.equals(likesList[i])){
                    intheList = true
                    break;
                }
            }
            if(intheList){
                imgLike.setImageResource(R.drawable.heart_filled)
            }
            else{
                imgLike.setImageResource(R.drawable.heart_empty)
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

