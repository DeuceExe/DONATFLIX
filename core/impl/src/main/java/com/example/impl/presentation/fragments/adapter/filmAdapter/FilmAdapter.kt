package com.example.impl.presentation.fragments.adapter.filmAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.impl.databinding.ItemCastBinding
import com.example.impl.databinding.ItemFilmBinding
import com.example.impl.databinding.ItemSearchFilmBinding
import com.example.impl.model.CurrentFilm
import com.example.impl.presentation.fragments.adapter.castAdapter.CastViewHolder

class FilmAdapter(
    private val filmList: List<CurrentFilm>,
    private val filmElement: FilmElement,
    private val filmClickListener: (Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        return when (filmElement) {
            FilmElement.FILM -> {
                FilmViewHolder(
                    ItemFilmBinding.inflate(inflater, viewGroup, false),
                    filmClickListener
                )
            }

            FilmElement.SEARCH -> {
                SearchViewHolder(
                    ItemSearchFilmBinding.inflate(inflater, viewGroup, false),
                    filmClickListener
                )
            }
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (filmElement.name) {
            FilmElement.SEARCH.name -> (viewHolder as SearchViewHolder).bind(filmList[position])
            FilmElement.FILM.name -> (viewHolder as FilmViewHolder).bind(filmList[position])
            else -> (viewHolder as CastViewHolder).bind(filmList[0].persons[position])
        }
    }

    override fun getItemCount() = filmList.size
}

enum class FilmElement {

    FILM,
    SEARCH
}