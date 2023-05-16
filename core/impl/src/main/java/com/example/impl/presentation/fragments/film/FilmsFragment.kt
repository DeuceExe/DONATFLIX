package com.example.impl.presentation.fragments.film

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.api.IFilmFragment
import com.example.impl.R
import com.example.impl.databinding.FragmentFilmsBinding
import com.example.impl.presentation.fragments.film.adapter.FilmAdapter
import com.example.impl.model.CurrentFilm
import com.example.impl.presentation.fragments.film.filmDetails.FilmDetailFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent

class FilmsFragment : Fragment(), IFilmFragment, KoinComponent {

    private val viewModel by viewModel<FilmViewModel>()

    private var _binding: FragmentFilmsBinding? = null
    val binding get() = requireNotNull(_binding)



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        initObserver()
    }

    private fun initObserver() {
        viewModel.actionFilmList.observe(viewLifecycleOwner) { actionFilmList ->
            setAdapter(actionFilmList, binding.bestFilmRv)
        }

        viewModel.horrorFilmList.observe(viewLifecycleOwner) { horrorFilmList ->
            setAdapter(horrorFilmList, binding.horrorFilmRv)
        }
    }

    private fun setAdapter(filmList: List<CurrentFilm>, recyclerView: RecyclerView) {
        val filmAdapter = FilmAdapter(filmList) { onFilmClick(it) }
        recyclerView.adapter = filmAdapter
        filmAdapter.notifyDataSetChanged()
    }

    private fun onFilmClick(id: Int) {
        val fragment = FilmDetailFragment()

        fragment.arguments = Bundle().apply {
            putInt(FilmDetailFragment.BUNDLE, id)
        }
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container, fragment, "findThisFragment")
            ?.addToBackStack(null)
            ?.commit()

        (requireActivity() as MainActivity).replaceFragment(fragment)
    }

    private fun initUi() {
        binding.horrorFilmRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.bestFilmRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    companion object {

        fun build() = FilmsFragment().apply {
            arguments = bundleOf()
        }

        const val ACTION = "боевик"
        const val HORROR = "ужасы"
    }
}