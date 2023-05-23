package com.example.impl.presentation.fragments.film.adapter.castAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.impl.databinding.ItemCastBinding
import com.example.impl.model.Persons

class CastAdapter(
    private val castList: List<Persons>,
) : RecyclerView.Adapter<CastViewHolder>() {

    private lateinit var binding: ItemCastBinding

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CastViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        binding = ItemCastBinding.inflate(inflater, viewGroup, false)
        return CastViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: CastViewHolder, position: Int) {
        viewHolder.bind(castList[position])
    }

    override fun getItemCount(): Int {
        val sizeLimit = 20

        return if (castList.size > sizeLimit) {
            sizeLimit
        } else {
            castList.size
        }
    }
}