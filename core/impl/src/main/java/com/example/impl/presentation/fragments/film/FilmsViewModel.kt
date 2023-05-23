package com.example.impl.presentation.fragments.film

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.impl.rest.IFilmsApi
import com.example.impl.presentation.fragments.film.FilmsFragment.Companion.ACTION
import com.example.impl.presentation.fragments.film.FilmsFragment.Companion.HORROR
import com.example.impl.model.CurrentFilm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

class FilmViewModel(private val serviceApi: IFilmsApi) : ViewModel(), KoinComponent {

    private val _actionFilmList = MutableLiveData<List<CurrentFilm>>(listOf())
    val actionFilmList: LiveData<List<CurrentFilm>> get() = _actionFilmList

    private val _horrorFilmList = MutableLiveData<List<CurrentFilm>>(listOf())
    val horrorFilmList: LiveData<List<CurrentFilm>> get() = _horrorFilmList

    private val _bestFilmList = MutableLiveData<List<CurrentFilm>>(listOf())

    val bestFilmList: LiveData<List<CurrentFilm>> get() = _bestFilmList

    private val _searchFilmList = MutableLiveData<List<CurrentFilm>>()

    val searchFilmList: LiveData<List<CurrentFilm>> get() = _searchFilmList

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                initFilmList(ACTION)
                initFilmList(HORROR)
                initTrailerList()
            }
        }
    }

    private suspend fun initFilmList(genre: String) {
        val genreRequest = serviceApi.getFilmByGenre(genre)

        if (genreRequest.isSuccessful) {
            when (genre) {
                ACTION -> _actionFilmList.postValue(genreRequest.body()?.docs)
                HORROR -> _horrorFilmList.postValue(genreRequest.body()?.docs)
                else -> {}
            }
        }
    }

    fun searchFilmAsync(query: String) {
        _searchFilmList.value = listOf()

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val searchRequest = serviceApi.searchFilm(query)

                if (searchRequest.isSuccessful) {
                    _searchFilmList.postValue(searchRequest.body()?.docs)
                }
            }
        }
    }

    private suspend fun initTrailerList() {
        val bestRequest = serviceApi.getBestFilms()

        if (bestRequest.isSuccessful) {
            _bestFilmList.postValue(bestRequest.body()?.docs)
        }
    }
}