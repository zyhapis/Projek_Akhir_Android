package com.app.hapis.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.hapis.databinding.ItemPagerBinding
import com.app.hapis.response.ResultsItem
import com.bumptech.glide.Glide

class ViewPagerAdapter : ListAdapter<ResultsItem, ViewPagerAdapter.ViewPagerVh>(ViewPagerCallBack) {

    inner class ViewPagerVh(private val binding: ItemPagerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ResultsItem) {
            binding.apply {
                titleTv.text = data.title
                val ratingTextValue = String.format("Rating: %.1f/10", data.voteAverage)
                ratingTv.text = ratingTextValue
                Glide.with(root.context).load("https://image.tmdb.org/t/p/original/${data.posterPath}")
                    .into(imageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerVh {
        val binding = ItemPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPagerVh(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerVh, position: Int) {
        holder.onBind(getItem(position))
    }

    object ViewPagerCallBack : DiffUtil.ItemCallback<ResultsItem>() {
        override fun areItemsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
            return oldItem == newItem
        }
    }
}
