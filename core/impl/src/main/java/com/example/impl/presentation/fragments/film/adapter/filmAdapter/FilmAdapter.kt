package com.example.impl.presentation.fragments.film.adapter.filmAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.impl.databinding.ItemFilmBinding
import com.example.impl.model.CurrentFilm

class FilmAdapter(
    private val filmList: List<CurrentFilm>,
    private val filmClickListener: (Int) -> Unit
) : RecyclerView.Adapter<FilmViewHolder>() {

    private lateinit var binding: ItemFilmBinding

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FilmViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        binding = ItemFilmBinding.inflate(inflater, viewGroup, false)
        return FilmViewHolder(binding, filmClickListener)
    }

    override fun onBindViewHolder(viewHolder: FilmViewHolder, position: Int) {
        viewHolder.bind(filmList[position])
    }

    override fun getItemCount() = filmList.size
}