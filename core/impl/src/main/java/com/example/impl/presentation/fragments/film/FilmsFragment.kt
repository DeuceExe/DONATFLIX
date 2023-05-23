package com.example.impl.presentation.fragments.film

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.isNotEmpty
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.api.IFilmFragment
import com.example.api.IFilmFragmentReplace
import com.example.impl.databinding.FragmentFilmsBinding
import com.example.impl.model.CurrentFilm
import com.example.impl.presentation.fragments.film.adapter.filmAdapter.FilmAdapter
import com.example.impl.presentation.fragments.film.adapter.filmAdapter.FilmElement
import com.example.impl.presentation.fragments.film.adapter.slideAdapter.SliderPagerAdapter
import com.example.impl.presentation.fragments.film.filmDetails.FilmDetailFragment
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent
import java.util.Timer
import java.util.TimerTask

class FilmsFragment : Fragment(), IFilmFragment, KoinComponent {

    private val viewModel by viewModel<FilmViewModel>()

    private var _binding: FragmentFilmsBinding? = null
    val binding get() = requireNotNull(_binding)

    private var fragmentChangeListener: IFilmFragmentReplace? = null

    private lateinit var timer: Timer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchView.searchFilmRv.bringToFront()
        searchProcessing()
        initUi()
        initObserver()
    }

    private fun initObserver() {
        viewModel.actionFilmList.observe(viewLifecycleOwner) { actionFilmList ->
            setAdapter(actionFilmList, binding.bestFilmRv, FilmElement.FILM)
        }

        viewModel.horrorFilmList.observe(viewLifecycleOwner) { horrorFilmList ->
            setAdapter(horrorFilmList, binding.horrorFilmRv, FilmElement.FILM)
        }

        viewModel.searchFilmList.observe(viewLifecycleOwner) { searchFilmList ->
            binding.searchView.searchFilmRv.isVisible = true
            setAdapter(searchFilmList, binding.searchView.searchFilmRv, FilmElement.SEARCH)
        }

        viewModel.bestFilmList.observe(viewLifecycleOwner) { bestFilmList ->
            setSlideAdapter(bestFilmList, binding.sliderPager)
        }
    }

    private fun setAdapter(
        filmList: List<CurrentFilm>,
        recyclerView: RecyclerView,
        filmElement: FilmElement
    ) {
        val filmAdapter = FilmAdapter(filmList, filmElement) {
            onFilmClick(it)
            binding.searchFilmView.setQuery("", false)
        }
        recyclerView.adapter = filmAdapter
        filmAdapter.notifyDataSetChanged()
    }

    private fun setSlideAdapter(slideList: List<CurrentFilm>, viewPager: ViewPager2) {
        val sliderAdapter = SliderPagerAdapter(slideList) { onTrailerClick(it) }
        val tabLayout = binding.indicator
        viewPager.adapter = sliderAdapter

        TabLayoutMediator(tabLayout, viewPager) { _, _ ->
        }.attach()

        timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                Handler(Looper.getMainLooper()).post {
                    if (sliderAdapter.itemCount != 0) {
                        viewPager.currentItem =
                            (viewPager.currentItem + 1) % sliderAdapter.itemCount
                    }
                }
            }
        }, 5000, 5000)
    }

    private fun onFilmClick(id: Int) {
        binding.searchFilmView.isIconified = true
        binding.searchFilmView.clearFocus()

        val fragment = FilmDetailFragment()

        fragment.arguments = Bundle().apply {
            putInt(FilmDetailFragment.BUNDLE, id)
        }

        fragmentChangeListener?.replaceFragment(fragment)
    }

    private fun onTrailerClick(trailerLink: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(trailerLink))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("force_fullscreen", true)
        intent.setClassName(
            "com.google.android.youtube",
            "com.google.android.youtube.WatchActivity"
        )

        try {
            startActivity(intent)
        } catch (e: Exception) {
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(trailerLink))
            startActivity(webIntent)
        }
    }

    private fun initUi() {
        binding.horrorFilmRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.bestFilmRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.searchView.searchFilmRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun searchProcessing() {
        if (binding.searchFilmView.isNotEmpty()) {
            binding.searchFilmView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    if (newText.isEmpty()) {
                        binding.searchView.cardViewSearch.isVisible = false
                    } else {
                        binding.searchView.cardViewSearch.isVisible = true
                        viewModel.searchFilmAsync(newText)
                    }
                    return true
                }
            })
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is IFilmFragmentReplace) {
            fragmentChangeListener = context
        } else {
            throw RuntimeException("$context")
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        timer.cancel()
    }

    companion object {

        fun build() = FilmsFragment().apply {
            arguments = bundleOf()
        }

        const val ACTION = "боевик"
        const val HORROR = "ужасы"
    }
}