package com.example.a12.ui.home

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a12.R
import java.util.*

class RecViewAdapter : RecyclerView.Adapter<RecViewAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private var recViewList = listOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        return MyViewHolder(layoutInflater.inflate(R.layout.row_layout,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = recViewList[position]
        val textView = holder.itemView.findViewById<TextView>(R.id.textView)
        textView.text = currentItem
        holder.itemView.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val list = listOf(R.style.MyTextViewStyle1,R.style.MyTextViewStyle2)
                textView.setTextAppearance(list[Random().nextInt(2)])
            }
        }

    }

    override fun getItemCount(): Int {
        return recViewList.size
    }
    fun setData(item: List<String>){
        this.recViewList = item
        notifyDataSetChanged()
    }
}