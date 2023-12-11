package com.example.impl.presentation.fragments.adapter.filmAdapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.impl.databinding.ItemSearchFilmBinding
import com.example.impl.model.CurrentFilm

class SearchViewHolder(
    private val binding: ItemSearchFilmBinding,
    private val filmClickListener: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CurrentFilm) {
        with(binding) {
            Glide.with(itemView)
                .load(item.poster.url)
                .into(searchImagePoster)
            searchTvTitle.text = item.name
        }

        itemView.setOnClickListener {
            filmClickListener.invoke(item.id)
        }
    }
}