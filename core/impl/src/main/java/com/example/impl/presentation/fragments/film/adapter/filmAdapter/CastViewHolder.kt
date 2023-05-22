package com.example.impl.presentation.fragments.film.adapter.filmAdapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.impl.databinding.ItemCastBinding
import com.example.impl.model.Persons

class CastViewHolder(
    private val binding: ItemCastBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Persons) {
        with(binding) {
            Glide.with(itemView)
                .load(item.photo)
                .into(imageCast)
            tvCast.text = item.name
        }
    }
}