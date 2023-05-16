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

    private val _actionFilmList = MutableLiveData<List<CurrentFilm>>()
    val actionFilmList: LiveData<List<CurrentFilm>> get() = _actionFilmList

    private val _horrorFilmList = MutableLiveData<List<CurrentFilm>>()
    val horrorFilmList: LiveData<List<CurrentFilm>> get() = _horrorFilmList

    init {
        initFilmList(ACTION)
        initFilmList(HORROR)
    }

    private fun initFilmList(genre: String) {
        _actionFilmList.value = listOf()
        _horrorFilmList.value = listOf()

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = serviceApi.getFilmByGenre(genre)
                if (result.isSuccessful) {
                    when (genre) {
                        ACTION -> _actionFilmList.postValue(result.body()?.docs)
                        HORROR -> _horrorFilmList.postValue(result.body()?.docs)
                        else -> {}
                    }
                }
            }
        }
    }
}