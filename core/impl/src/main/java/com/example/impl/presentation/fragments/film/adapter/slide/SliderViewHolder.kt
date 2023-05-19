package com.example.impl.presentation.fragments.film.adapter.slide

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.impl.databinding.ItemSlideBinding
import com.example.impl.model.CurrentFilm

class SliderViewHolder(
    private val binding: ItemSlideBinding,
    private val trailerClickListener: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CurrentFilm) {
        with(binding) {
            Glide.with(itemView)
                .load(item.backdrop.url)
                .into(imageTrailer)
            tvTrailerTitle.text = item.name
        }

        binding.btnPlay.setOnClickListener {
            var url = ""
            item.videos.trailers.forEach {
                if(it.url.contains("youtube")){
                    url = it.url
                }
            }
            trailerClickListener.invoke(url)
        }
    }
}