package com.example.impl.presentation.fragments.film.adapter.filmAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.impl.databinding.ItemFilmBinding
import com.example.impl.databinding.ItemSearchFilmBinding
import com.example.impl.model.CurrentFilm

class FilmAdapter(
    private val filmList: List<CurrentFilm>,
    private val isSearch: Boolean,
    private val filmClickListener: (Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        return if (isSearch) {
            SearchViewHolder(
                ItemSearchFilmBinding.inflate(inflater, viewGroup, false),
                filmClickListener
            )
        } else {
            FilmViewHolder(
                ItemFilmBinding.inflate(inflater, viewGroup, false),
                filmClickListener
            )
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (isSearch)
            (viewHolder as SearchViewHolder).bind(filmList[position])
        else
            (viewHolder as FilmViewHolder).bind(filmList[position])
    }

    override fun getItemCount() = filmList.size
}