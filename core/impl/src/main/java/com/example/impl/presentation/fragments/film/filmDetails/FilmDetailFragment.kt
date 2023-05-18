package com.example.impl.presentation.fragments.film.filmDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.uikit.R
import com.example.impl.databinding.FragmentFilmDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent

class FilmDetailFragment : Fragment(), KoinComponent {

    private val viewModel by viewModel<FilmDetailViewModel>()

    private var _binding: FragmentFilmDetailBinding? = null

    val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
        arguments?.let {
            CoroutineScope(Job()).launch {
                viewModel.getFilmById(it.getInt(BUNDLE, 0))
            }
        }
    }

    private fun loadPoster() {
        Glide.with(this@FilmDetailFragment)
            .load(viewModel.currentFilm.value?.poster?.url)
            .into(binding.imageDetailFilm)
    }

    private fun setUiData() {
        with(binding) {
            tvDetailTitle.text = viewModel.currentFilm.value?.name
            tvDetailGenre.text = resources.getString(R.string.genre, viewModel.genres.value)
            tvDetailYear.text = resources.getString(
                R.string.year, viewModel.currentFilm.value?.year.toString()
            )
            tvDetailCountry.text = resources.getString(R.string.country, viewModel.countries.value)
            tvDetailDescription.text = viewModel.currentFilm.value?.description
        }
    }

    private fun initObserver() {
        viewModel.currentFilm.observe(viewLifecycleOwner) {
            setUiData()
            loadPoster()
        }
    }

    companion object {

        const val BUNDLE = "bundle"
    }
}