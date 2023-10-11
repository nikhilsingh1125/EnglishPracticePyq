package com.englishpracticevocab.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.englishpracticevocab.HomeActivity
import com.englishpracticevocab.R
import com.englishpracticevocab.model.CategoryModel
import kotlinx.android.synthetic.main.row_home_category.view.*

class HomeAdapter(
    val context: Context,
    val arrayList: ArrayList<CategoryModel>
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    val TAG = "HomeActivity"
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_home_category,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = arrayList[position]
        holder.itemView.txtCatTitle.text = model.title
        Glide.with(context).load(model.image).into(holder.itemView.catImg);

      /*  if (position ==0){
            holder.itemView.llLock.visibility = View.VISIBLE
        }
        else if (position ==1){
            holder.itemView.llLock.visibility = View.VISIBLE
        }
        else if (position ==2){
            holder.itemView.llLock.visibility = View.GONE
        }
        else if (position ==3){
            holder.itemView.llLock.visibility = View.GONE
        }*/
/*
        holder.itemView.llLock.setOnClickListener {
            if (position ==0){
                rewardedAd?.let { ad ->
                    ad.show(context as Activity, OnUserEarnedRewardListener {
                        Toast.makeText(context, "UnLocked", Toast.LENGTH_SHORT).show()
                        holder.itemView.llLock.visibility = View.GONE
                        holder.itemView.catCV.visibility = View.VISIBLE
                    })
                } ?: run {
                    holder.itemView.llLock.visibility = View.GONE
                    holder.itemView.catCV.visibility = View.VISIBLE
                }
            }
           else if (position == 1){
                rewardedAd?.let { ad ->
                    ad.show(context as Activity, OnUserEarnedRewardListener {
                        Toast.makeText(context, "UnLocked", Toast.LENGTH_SHORT).show()
                        holder.itemView.llLock.visibility = View.GONE
                        holder.itemView.catCV.visibility = View.VISIBLE
                    })
                } ?: run {
                    holder.itemView.llLock.visibility = View.GONE
                    holder.itemView.catCV.visibility = View.VISIBLE
                }
            }

        }*/



        holder.itemView.catCV.setOnClickListener {
            (context as HomeActivity).goToCategory(model,position)
        }

    }

    override fun getItemCount(): Int {
       return arrayList.size
    }

}