package com.englishpracticevocab.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.englishpracticevocab.R
import com.englishpracticevocab.model.QuestionData
import kotlinx.android.synthetic.main.row_note.view.txtTitle

class ResultAdapter(
    val context: Context,
    val arrayList: ArrayList<QuestionData>
) : RecyclerView.Adapter<ResultAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_results,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = arrayList[position]
        holder.itemView.txtTitle.text = model.testCategory

    }

    override fun getItemCount(): Int {
       return arrayList.size
    }
}