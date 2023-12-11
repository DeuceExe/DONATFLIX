package com.example.impl.presentation.fragments.filmDetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.uikit.R
import com.example.impl.databinding.FragmentFilmDetailBinding
import com.example.impl.model.Persons
import com.example.impl.presentation.fragments.adapter.castAdapter.CastAdapter
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

        addFilmAtFavorite()
        loadTrailer()
        initObserver()
        initUi()

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

    private fun setAdapter(
        castList: List<Persons>,
        recyclerView: RecyclerView
    ) {
        val castAdapter = CastAdapter(castList)
        recyclerView.adapter = castAdapter
        castAdapter.notifyDataSetChanged()
    }

    private fun setUiData() {
        with(binding) {
            Glide.with(this@FilmDetailFragment)
                .load(viewModel.currentFilm.value?.backdrop?.url)
                .into(detailMovieCover)
            tvDetailTitle.text = viewModel.currentFilm.value?.name
            tvDetailGenre.text = resources.getString(R.string.genre, viewModel.genres.value)
            tvDetailYear.text = resources.getString(
                R.string.year, viewModel.currentFilm.value?.year.toString()
            )
            tvDetailCountry.text = resources.getString(R.string.country, viewModel.countries.value)
            tvDetailDescription.text = viewModel.currentFilm.value?.description
        }
    }

    private fun loadTrailer() {
        binding.btnPlay.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(viewModel.currentFilm.value?.videos?.trailers?.get(0)?.url)
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("force_fullscreen", true)
            intent.setClassName(
                "com.google.android.youtube",
                "com.google.android.youtube.WatchActivity"
            )

            try {
                startActivity(intent)
            } catch (e: Exception) {
                val webIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(viewModel.currentFilm.value?.videos?.trailers?.get(0)?.url)
                )
                startActivity(webIntent)
            }
        }
    }

    private fun initUi() {
        binding.castFilmRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun initObserver() {
        viewModel.currentFilm.observe(viewLifecycleOwner) {
            setUiData()
            loadPoster()
        }

        viewModel.castList.observe(viewLifecycleOwner) { castList ->
            setAdapter(castList, binding.castFilmRv)
        }
    }

    private fun addFilmAtFavorite() {
        var isFavorite = false
        binding.btnFavorite.setOnClickListener {
            isFavorite = if (isFavorite) {

                binding.btnFavorite.setImageResource(com.example.impl.R.drawable.favorite_unselected)
                false
            } else {
                binding.btnFavorite.setImageResource(com.example.impl.R.drawable.favorite_selected)
                true
            }
        }
    }

    companion object {

        const val BUNDLE = "bundle"
    }
}