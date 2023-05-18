package com.example.impl.presentation.fragments.film.adapter.slide

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.impl.databinding.SlideItemBinding
import com.example.impl.model.CurrentFilm

class SliderViewHolder(
    private val binding: SlideItemBinding,
    private val trailerClickListener: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CurrentFilm) {
        with(binding) {
            Glide.with(itemView)
                .load(item.poster.url)
                .into(imageTrailer)
            tvTrailerTitle.text = item.name
        }

        binding.btnPlay.setOnClickListener {
            trailerClickListener.invoke(item.id)
        }
    }
}