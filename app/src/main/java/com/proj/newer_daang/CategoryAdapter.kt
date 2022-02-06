package com.proj.newer_daang

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssomai.android.scalablelayout.ScalableLayout


class CategoryAdapter(private val context: Context) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>(){
    var cateList = mutableListOf<CateData>()
    var selected = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_category,parent,false)
        return ViewHolder(view)


    }

    override fun getItemCount(): Int = cateList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cateList[position])
        if(selected == position) {
            holder.item?.setBackgroundResource(R.drawable.aqua_darker_background_round_sharp)
        }
        else {
            holder.item?.setBackgroundResource(R.drawable.aqua_background_round_sharp)
        }


    }


    interface OnItemClickListener{
        fun onItemClick(v:View, data: CateData, pos: Int)
    }
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener

    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val item : ScalableLayout? = itemView?.findViewById(R.id.catebox)

        private val tvTerm: TextView = itemView.findViewById(R.id.term_name)

        fun bind(item: CateData) {
            tvTerm.text = item.name
            val pos = adapterPosition
            if(pos!= RecyclerView.NO_POSITION)
            {
                itemView.setOnClickListener {
                    listener?.onItemClick(itemView,item,pos)
                    selected = pos
                    notifyDataSetChanged()
                }
            }

        }



    }
    data class CateData (
        val name : String
    )

}