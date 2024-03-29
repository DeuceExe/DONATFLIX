package com.example.impl.presentation.fragments.filmDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.impl.model.CategoryNames
import com.example.impl.model.CurrentFilm
import com.example.impl.model.Persons
import com.example.impl.rest.IFilmsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

class FilmDetailViewModel(private val serviceApi: IFilmsApi) : ViewModel(), KoinComponent {

    private val _currentFilm = MutableLiveData<CurrentFilm>()
    val currentFilm: LiveData<CurrentFilm> get() = _currentFilm

    private val _genres = MutableLiveData("")
    val genres: LiveData<String> get() = _genres

    private val _countries = MutableLiveData("")
    val countries: LiveData<String> get() = _countries

    private val _castList = MutableLiveData<List<Persons>>()
    val castList: LiveData<List<Persons>> get() = _castList

    suspend fun getFilmById(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = serviceApi.getFilmById(id)
                if (result.isSuccessful) {
                    with(result.body()?.docs?.get(0)) {
                        this?.let {
                            _genres.postValue(initCategoryNames(this.genres))
                            _countries.postValue(initCategoryNames(this.countries))
                            _currentFilm.postValue(this)
                            _castList.postValue(this.persons)
                        }
                    }
                }
            }
        }
    }

    private fun initCategoryNames(categoryNames: List<CategoryNames>): String {
        var dataField = ""
        categoryNames.forEach {
            dataField = dataField.plus("${it.name}, ")
        }
        return dataField.dropLast(2)
    }
}