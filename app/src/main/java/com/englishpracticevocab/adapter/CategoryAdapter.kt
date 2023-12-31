package com.englishpracticevocab.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.englishpracticevocab.CategoryActivity
import com.englishpracticevocab.R
import com.englishpracticevocab.model.ListCategoryData
import kotlinx.android.synthetic.main.row_category.view.cardQuestionTitle
import kotlinx.android.synthetic.main.row_category.view.txtContext
import kotlinx.android.synthetic.main.row_note.view.txtTitle

class CategoryAdapter(
    val context: Context,
    val arrayList: ArrayList<ListCategoryData>
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_category,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = arrayList[position]
        holder.itemView.txtTitle.text = model.title
        holder.itemView.txtContext.text = model.context

        holder.itemView.cardQuestionTitle.setOnClickListener {
            (context as CategoryActivity).goToNextExamList(model,position)
        }

    }

    override fun getItemCount(): Int {
       return arrayList.size
    }
}