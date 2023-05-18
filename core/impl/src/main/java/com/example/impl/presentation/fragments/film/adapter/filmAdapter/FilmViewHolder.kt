package com.example.impl.presentation.fragments.film.adapter.filmAdapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.impl.databinding.ItemFilmBinding
import com.example.impl.model.CurrentFilm

class FilmViewHolder(
    private val binding: ItemFilmBinding,
    private val filmClickListener: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CurrentFilm) {
        with(binding) {
            Glide.with(itemView)
                .load(item.poster.url)
                .into(imageFilm)
            tvTitleFilm.text = item.name
        }

        itemView.setOnClickListener {
            filmClickListener.invoke(item.id)
        }
    }
}