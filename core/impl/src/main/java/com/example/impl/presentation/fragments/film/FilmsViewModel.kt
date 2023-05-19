package com.example.impl.presentation.fragments.film

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.viewpager.widget.ViewPager
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

    private val _bestFilmList = MutableLiveData<List<CurrentFilm>>()

    val bestFilmList: LiveData<List<CurrentFilm>> get() = _bestFilmList

    init {
        initFilmList(ACTION)
        initFilmList(HORROR)
        initTrailerList()
    }

    private fun initFilmList(genre: String) {
        _actionFilmList.value = listOf()
        _horrorFilmList.value = listOf()

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val genreRequest = serviceApi.getFilmByGenre(genre)
                if (genreRequest.isSuccessful) {
                    when (genre) {
                        ACTION -> _actionFilmList.postValue(genreRequest.body()?.docs)
                        HORROR -> _horrorFilmList.postValue(genreRequest.body()?.docs)
                        else -> {}
                    }
                }
            }
        }
    }

    private fun initTrailerList(){
        _bestFilmList.value = listOf()

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val bestRequest = serviceApi.test()

                if (bestRequest.isSuccessful){
                    _bestFilmList.postValue(bestRequest.body()?.docs)
                }
            }
        }
    }
}