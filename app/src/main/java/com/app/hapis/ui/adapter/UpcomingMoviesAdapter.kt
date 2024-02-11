package com.app.hapis.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.hapis.databinding.ItemUpcomingMovieBinding
import com.app.hapis.response.GenreResponse
import com.app.hapis.response.ResultsComingItem
import com.bumptech.glide.Glide

class UpcomingMoviesAdapter(var genreResponse: GenreResponse) :
    ListAdapter<ResultsComingItem, UpcomingMoviesAdapter.UpcomingMovieViewHolder>(UpcomingMovieDiffCallback) {

    inner class UpcomingMovieViewHolder(private val binding: ItemUpcomingMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: ResultsComingItem) {
            binding.apply {
                titleText.text = movie.title
                arrivalDateText.text = "Coming on ${movie.releaseDate}"
                overviewText.text = movie.overview
                val ratingTextValue = String.format("%.1f/10", movie.voteAverage)
                ratingText.text = ratingTextValue

                val genreNames = getGenreNamesByIds(movie.genreIds)
                genresText.text = genreNames.joinToString(" • ")

                val posterUrl = "https://image.tmdb.org/t/p/original/${movie.posterPath}"
                Glide.with(posterImage).load(posterUrl).into(posterImage)

                val backdropUrl = "https://image.tmdb.org/t/p/original/${movie.backdropPath}"
                Glide.with(backdropImage).load(backdropUrl).into(backdropImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingMovieViewHolder {
        val binding =
            ItemUpcomingMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UpcomingMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UpcomingMovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun getGenreNamesByIds(genreIds: List<Int>?): List<String> {
        return genreIds?.mapNotNull { id ->
            genreResponse.genres?.find { it.id == id }?.name
        } ?: emptyList()
    }
}

object UpcomingMovieDiffCallback : DiffUtil.ItemCallback<ResultsComingItem>() {
    override fun areItemsTheSame(oldItem: ResultsComingItem, newItem: ResultsComingItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ResultsComingItem, newItem: ResultsComingItem): Boolean {
        return oldItem == newItem
    }
}
