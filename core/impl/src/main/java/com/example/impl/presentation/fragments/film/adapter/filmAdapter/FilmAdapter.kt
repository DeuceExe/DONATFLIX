package com.example.impl.presentation.fragments.film.adapter.filmAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.impl.databinding.ItemCastBinding
import com.example.impl.databinding.ItemFilmBinding
import com.example.impl.databinding.ItemSearchFilmBinding
import com.example.impl.model.CurrentFilm
import com.example.impl.model.Persons

class FilmAdapter(
    private val filmList: List<CurrentFilm>,
    private val filmElement: FilmElement,
    private val filmClickListener: ((Int) -> Unit?)? = null
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

            FilmElement.CAST -> {
                CastViewHolder(ItemCastBinding.inflate(inflater, viewGroup, false))
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

    override fun getItemCount(): Int {
        val sizeLimit = 20

        return if (filmList.size > sizeLimit) {
            sizeLimit
        } else {
            filmList.size
        }
    }
}

enum class FilmElement {

    FILM,
    SEARCH,
    CAST
}