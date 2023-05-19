package com.example.impl.presentation.fragments.film.adapter.slide

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.impl.databinding.SlideItemBinding
import com.example.impl.model.CurrentFilm

class SliderPagerAdapter(
    private val slideList: List<CurrentFilm>,
    private val trailerClickListener: (String) -> Unit
) : RecyclerView.Adapter<SliderViewHolder>() {

    private lateinit var binding: SlideItemBinding

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): SliderViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        binding = SlideItemBinding.inflate(inflater, viewGroup, false)
        return SliderViewHolder(binding, trailerClickListener)
    }

    override fun onBindViewHolder(viewHolder: SliderViewHolder, position: Int) {
        viewHolder.bind(slideList[position])
    }

    override fun getItemCount() = slideList.size
}