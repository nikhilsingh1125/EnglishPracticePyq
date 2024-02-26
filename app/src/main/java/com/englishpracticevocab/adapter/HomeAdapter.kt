package com.englishpracticevocab.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.englishpracticevocab.HomeActivity
import com.englishpracticevocab.R
import kotlinx.android.synthetic.main.row_home_category.view.*

class HomeAdapter(
    val context: Context,
    val arrayList: MutableList<String>
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    val TAG = "HomeActivity"
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_home_category,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = arrayList[position]
        holder.itemView.txtCatTitle.text = arrayList[position]
        Glide.with(context).load(R.drawable.study).into(holder.itemView.catImg);

        holder.itemView.catCV.setOnClickListener {
            (context as HomeActivity).goToCategory(model,position)
        }

    }

    override fun getItemCount(): Int {
       return arrayList.size
    }

}