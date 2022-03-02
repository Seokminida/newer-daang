package com.example.recyclerview_ex

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.proj.newer_daang.AfterRe
import com.proj.newer_daang.ItemData
import com.proj.newer_daang.R
import com.proj.newer_daang.WordDetailActivity
import kotlinx.android.synthetic.main.item_recent.view.*

class RecentAdapter(private val context: Context) : RecyclerView.Adapter<RecentAdapter.ViewHolder>() {

    var datas = ArrayList<ItemData>()
    var datas2 = ArrayList<ItemData>()
    var datas3 = ArrayList<ItemData>()
    var db = Firebase.firestore
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recent,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val recentW: TextView = itemView.findViewById(R.id.recent_word)

        fun bind(item: ItemData) {
            var pos = adapterPosition
            recentW.text = item.name

            itemView.setOnClickListener { //최근검색 아이템 클릭 리스너
                Log.d("asdasd2","$datas2")
                datas3.clear()
                if(datas[pos].name.length != 0) {
                    for (i in 0 until datas2.size) {
                        if(datas[pos].name.length > datas2[i].name.length)
                            break
                        var check = 1
                        if(datas[pos].name[0] == '#'){
                            if(datas[pos].name == datas2[i].hashT)
                                datas3.add(datas2[i])
                        }
                        for (j in 0 until datas[pos].name.length){
                            if(datas[pos].name[j] != datas2[i].name[j]){
                                check = 0
                                break
                            }
                        }
                        if(check == 1 )
                            datas3.add(datas2[i])
                    }
                }
                if(datas[pos].name.length > 1) {
                    for (i in 0 until datas2.size) {
                        if (datas2[i].name
                                .contains(datas[pos].name)
                        ) {
                            if (datas3.contains(datas2[i]))
                                continue
                            datas3.add(datas2[i])
                        }
                    }
                }
                Intent(context, AfterRe::class.java).apply {
                        putExtra("array", datas3)
                        putExtra("name2", datas[pos].name)
                        putExtra("ch",1)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }.run { context.startActivity(this) }

            }

                itemView.recent_delete.setOnClickListener {
                    db.collection("user").document(Firebase.auth.uid.toString()).collection("최근검색어").document(datas[pos].name)
                        .delete()

                    datas.removeAt(pos)
                    notifyDataSetChanged()
                }
            }
        }

    var ite = ArrayList<ItemData>()
    fun recentList(recentList: ArrayList<ItemData>) {
        ite = recentList
        datas = ite
        notifyDataSetChanged()
    }

    fun listUpdate(){
        datas2.clear()
        val economy = db.collection("economy")
        economy.get()
            .addOnSuccessListener { document ->
                for(result in document){
                    val insertD = ItemData(result["name"].toString(), result["meaning"].toString(), result["hashtag"].toString(),result["article"].toString(),result["link"].toString())
                    datas2.add(insertD)
                }
            }
            .addOnFailureListener { exception ->
                Log.d("asd", "get failed with ", exception)
            }

        val politics = db.collection("politics")
        politics.get()
            .addOnSuccessListener { document ->
                for(result in document){
                    val insertD = ItemData(result["name"].toString(), result["meaning"].toString(), result["hashtag"].toString(),result["article"].toString(),result["link"].toString())
                    datas2.add(insertD)
                }
            }
            .addOnFailureListener { exception ->
                Log.d("asd", "get failed with ", exception)
            }

        val society = db.collection("society")
        society.get()
            .addOnSuccessListener { document ->
                for(result in document){
                    val insertD = ItemData(result["name"].toString(), result["meaning"].toString(), result["hashtag"].toString(),result["article"].toString(),result["link"].toString())
                    datas2.add(insertD)
                }
            }
            .addOnFailureListener { exception ->
                Log.d("asd", "get failed with ", exception)
            }
    }

}
