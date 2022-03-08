package com.example.recyclerview_ex

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.proj.newer_daang.ItemData
import com.proj.newer_daang.R
import com.proj.newer_daang.WordDetailActivity
import kotlinx.android.synthetic.main.activity_word_detail_3.*

class AfterReAdapter(private val context: Context) : RecyclerView.Adapter<AfterReAdapter.ViewHolder>() {
    var likesList = ArrayList<String>()
    var bookmarkList = ArrayList<String>()
    val db = Firebase.firestore
    var datas = ArrayList<ItemData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_after_search_2,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])

        holder.imgLike.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v:View){
                var tmp = datas[holder.adapterPosition].name

                //if already in likes
                var intheList = false

                //해당 아이템의 필드값이 좋아요 = true 이면 ㄱㄱ
                for(i in likesList.indices){
                    if(tmp.equals(likesList[i])){
                        intheList = true
                        likesList.removeAt(i)
                        break;
                    }
                }

                if(intheList){
                    //Toast.makeText(App.ApplicationContext(), "삭제됨 "+tmp + " "+ holder.adapterPosition, Toast.LENGTH_SHORT).show()
                    holder.imgLike.setImageResource(R.drawable.heart_empty_50_2)
                    db.collection("user").document(Firebase.auth.uid.toString()).collection("좋아요").document(tmp)
                        .delete()
                        .addOnSuccessListener { Log.d("firebasedeleted", "DocumentSnapshot successfully deleted!") }
                        .addOnFailureListener { e -> Log.w("firebasedeleted", "Error deleting document", e) }

                }
                //if not in likes yet
                else{
                    holder.imgLike.setImageResource(R.drawable.heart_filled_50_2)
                    val likes_info = hashMapOf(
                        "like" to "true",
                        "name" to tmp,
                    )
                    // Add a new document with a generated ID
                    db.collection("user").document(Firebase.auth.uid.toString()).collection("좋아요")
                        .document(tmp).set(likes_info)
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


        holder.imgBookmark.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v:View) {
                var term = datas[holder.adapterPosition]

                //if already in likes
                var intheList = false

                for(i in bookmarkList.indices){
                    if(term.name.equals(bookmarkList[i])){
                        intheList = true
                        bookmarkList.removeAt(i)
                        break;
                    }
                }

                if(intheList){
                    holder.imgBookmark.setImageResource(R.drawable.bookmark_empty_50_2)
                    db.collection("user").document(Firebase.auth.uid.toString()).collection("북마크").document(term.name)
                        .delete()
                        .addOnSuccessListener { Log.d("firebasedeleted", "DocumentSnapshot successfully deleted!") }
                        .addOnFailureListener { e -> Log.w("firebasedeleted", "Error deleting document", e) }
                }
                //if not in likes yet
                else{
                    holder.imgBookmark.setImageResource(R.drawable.bookmark_filled_50_2)
                    val bookmarks_info = hashMapOf(
                        "name" to term.name,
                        "meaning" to term.meaning,
                        "category" to term.category,
                        "hashtag" to term.hashT,
                        "article" to term.article,
                        "link" to term.link,
                    )
                    // Add a new document with a generated ID
                    db.collection("user").document(Firebase.auth.uid.toString()).collection("북마크")
                        .document(term.name).set(bookmarks_info)
                        .addOnSuccessListener { documentReference ->
                            Log.d("firebaseadded", "DocumentSnapshot added with "+term.name)
                            //Log.d("firebaseadded", "DocumentSnapshot added with ID: ${documentReference.id}")
                        }
                        .addOnFailureListener { e ->
                            Log.w("firebaseadded", "Error adding document", e)
                        }
                    bookmarkList.add(term.name)

                }

            }
        })

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val wna: TextView = itemView.findViewById(R.id.wna)
        private val wme: TextView = itemView.findViewById(R.id.wme)
        private val whash: TextView = itemView.findViewById(R.id.hashT)
        private val cate: TextView = itemView.findViewById(R.id.cate)

        val imgLike: ImageButton = itemView.findViewById(R.id.heart)
        val imgBookmark: ImageButton = itemView.findViewById(R.id.bookmark)

        fun bind(item: ItemData) {
            wna.text = item.name
            wme.text = item.meaning
            whash.text = item.hashT
            when(item.category){
                "politics" -> cate.text = "정치"
                "society" -> cate.text = "사회"
                "military" -> cate.text = "군사"
                "culture" -> cate.text = "문화"
                "economy" -> cate.text = "경제"
                "it_science" -> cate.text = "IT/과학"
            }
            itemView.setOnClickListener {
                Intent(context, WordDetailActivity::class.java).apply {
                    putExtra("name", item.name)
                    putExtra("mean", item.meaning)
                    putExtra("category",item.category)
                    putExtra("hash",item.hashT)
                    putExtra("link",item.link)
                    putExtra("article",item.article)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { context.startActivity(this) }
            }

            var intheList = false

            //해당 아이템의 필드값이 좋아요 = true 이면 ㄱㄱ
            for(i in likesList.indices){
                if(item.name.equals(likesList[i])){
                    intheList = true
                    break;
                }
            }
            if(intheList){
                imgLike.setImageResource(R.drawable.heart_filled_50_2)
            }
            else{
                imgLike.setImageResource(R.drawable.heart_empty_50_2)
            }


            intheList = false

            //해당 아이템의 필드값이 좋아요 = true 이면 ㄱㄱ
            for(i in bookmarkList.indices){
                if(item.name.equals(bookmarkList[i])){
                    intheList = true
                    break;
                }
            }
            if(intheList){
                imgBookmark.setImageResource(R.drawable.bookmark_filled_50_2)
            }
            else{
                imgBookmark.setImageResource(R.drawable.bookmark_empty_50_2)
            }

        }

    }


}