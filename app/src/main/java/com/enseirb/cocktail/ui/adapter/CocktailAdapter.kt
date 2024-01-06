package com.enseirb.cocktail.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.enseirb.cocktail.R
import com.enseirb.cocktail.core.model.Cocktail
import com.enseirb.cocktail.databinding.CocktailCardBinding
import com.google.android.material.button.MaterialButton
import com.squareup.picasso.Picasso

class CocktailAdapter(private var cocktailList: List<Cocktail?>) : RecyclerView.Adapter<CocktailAdapter.ViewHolder>() {

    var onButtonClicked: ( (String?) -> Unit)? = null

    class ViewHolder(binding: CocktailCardBinding) : RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.cocktailCardTextViewTitle
        val thumb: ImageView = binding.cocktailCardImageView
        val category: TextView = binding.cocktailCardTextViewSecondaryText
        val recipe: TextView = binding.cocktailCardTextViewSupportingText
        val button: MaterialButton = binding.button
    }

    fun updateData(newData: List<Cocktail?>) {
        cocktailList = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CocktailCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cocktail = cocktailList[position]
        holder.title.text = cocktail?.name
        holder.category.text = cocktail?.category
        holder.recipe.text = cocktail?.recipe
        Picasso.get()
            .load(cocktail?.thumb)
            .placeholder(R.drawable.ic_cocktail)
            .into(holder.thumb)
        holder.button.setOnClickListener{ onButtonClicked?.invoke(cocktail?.name) }
    }

    override fun getItemCount(): Int {
        return cocktailList.size
    }
}