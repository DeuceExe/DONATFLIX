package com.example.impl.presentation.fragments.adapter.slideAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.impl.databinding.ItemSlideBinding
import com.example.impl.model.CurrentFilm

class SliderPagerAdapter(
    private val slideList: List<CurrentFilm>,
    private val trailerClickListener: (String) -> Unit
) : RecyclerView.Adapter<SliderViewHolder>() {

    private lateinit var binding: ItemSlideBinding

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): SliderViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        binding = ItemSlideBinding.inflate(inflater, viewGroup, false)
        return SliderViewHolder(binding, trailerClickListener)
    }

    override fun onBindViewHolder(viewHolder: SliderViewHolder, position: Int) {
        viewHolder.bind(slideList[position])
    }

    override fun getItemCount() = slideList.size
}