package com.proj.newer_daang

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_after_search.*
import kotlinx.android.synthetic.main.activity_word_detail_3.*

class BookmarkAdapter(private val context: Context) : RecyclerView.Adapter<BookmarkAdapter.ViewHolder>(){
    var bookmarkList = mutableListOf<ItemData>()
    var likesList = ArrayList<String>()
    val db = Firebase.firestore

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkAdapter.ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_bookmark_3,parent,false)
        //val view = LayoutInflater.from(context).inflate(R.layout.item_bookmark_2,parent,false)
        return ViewHolder(view)


    }

    override fun getItemCount(): Int = bookmarkList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(bookmarkList[position])

        holder.imgLike.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v:View){
                var tmp = bookmarkList[holder.adapterPosition].name
                Log.d("likes?", "test on clicked "+ " "+ tmp)

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

        holder.imgBin.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v:View) {
                var tmp = bookmarkList[holder.adapterPosition].name
                db.collection("user").document(Firebase.auth.uid.toString()).collection("북마크")
                    .document(tmp)
                    .delete()
                    .addOnSuccessListener {
                        Log.d(
                            "firebasedeleted",
                            "DocumentSnapshot successfully deleted!"
                        )
                    }
                    .addOnFailureListener { e ->
                        Log.w(
                            "firebasedeleted",
                            "Error deleting document",
                            e
                        )
                    }

                bookmarkList.removeAt(holder.adapterPosition)
                notifyDataSetChanged()
            }
        })


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
        private val tvTerm_meaning: TextView = itemView.findViewById(R.id.term_meaning)
        private val tvCategory: TextView = itemView.findViewById(R.id.term_cate)
        private val tvTag: TextView = itemView.findViewById(R.id.term_tag)

        val imgLike: ImageButton = itemView.findViewById(R.id.heart)
        val imgBin: ImageButton = itemView.findViewById(R.id.bin)

        fun bind(item: ItemData) {
            tvTerm.text = item.name
            tvTerm_meaning.text = item.meaning
            tvTag.text = item.hashT
            when(item.category){
                "politics" -> tvCategory.text = "정치"
                "society" -> tvCategory.text = "사회"
                "military" -> tvCategory.text = "군사"
                "culture" -> tvCategory.text = "문화"
                "economy" -> tvCategory.text = "경제"
                "IT" -> tvCategory.text = "IT/과학"
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