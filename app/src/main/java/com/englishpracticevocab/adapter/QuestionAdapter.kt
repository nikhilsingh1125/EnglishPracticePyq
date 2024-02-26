package com.englishpracticevocab.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.englishpracticevocab.MainActivity
import com.englishpracticevocab.R
import com.englishpracticevocab.model.ExamCategory
import kotlinx.android.synthetic.main.row_note.view.*

class QuestionAdapter(
    val context: Context,
    val arrayList: List<ExamCategory>
) : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_note,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = arrayList[position]
        holder.itemView.txtTitle.text = model.name
        holder.itemView.cardQuestionTitle.setOnClickListener {
            (context as MainActivity).goToQuizActi(model.id,position)
        }

    }

    override fun getItemCount(): Int {
       return arrayList.size
    }
}