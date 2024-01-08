package com.enseirb.cocktail.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.enseirb.cocktail.core.model.StringResponse
import com.enseirb.cocktail.databinding.CategoryCardBinding

// Adapter used for categories and ingredients (text only)
class TextAdapter(private var textList: List<StringResponse?>) : RecyclerView.Adapter<TextAdapter.ViewHolder>() {

    var onItemClick: ( (String?) -> Unit)? = null
    class ViewHolder(binding: CategoryCardBinding) : RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.name
    }

    fun updateData(newData: List<StringResponse?>) {
        textList = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CategoryCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = textList[position]?.name
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(textList[position]?.name)
        }
    }

    override fun getItemCount(): Int {
        return textList.size
    }
}