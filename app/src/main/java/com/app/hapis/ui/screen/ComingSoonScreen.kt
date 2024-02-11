package com.app.hapis.ui.screen

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.hapis.R
import com.app.hapis.response.GenreResponse
import com.app.hapis.ui.adapter.UpcomingMoviesAdapter
import com.app.hapis.viewmodel.UpcomingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComingSoonScreen : Fragment(R.layout.coming_soon_screen) {

    private val upcomingViewModel: UpcomingViewModel by viewModels()
    private lateinit var adapter: UpcomingMoviesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.upcoming_movies_list)

        // Menampilkan ProgressBar saat aplikasi pertama kali dibuka
        view.findViewById<ProgressBar>(R.id.progress_bar_coming_soon).visibility = View.VISIBLE

        adapter = UpcomingMoviesAdapter(upcomingViewModel.genreResponse.value ?: GenreResponse())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        upcomingViewModel.listMovies.observe(viewLifecycleOwner) { movies ->
            adapter.submitList(movies)
        }

        upcomingViewModel.genreResponse.observe(viewLifecycleOwner) { genreResponse ->
            adapter.genreResponse = genreResponse ?: GenreResponse()
            adapter.notifyDataSetChanged()

            // Menghilangkan ProgressBar saat data selesai dimuat
            view.findViewById<ProgressBar>(R.id.progress_bar_coming_soon).visibility = View.GONE
        }

        upcomingViewModel.getUpcomingMovies(1)
        upcomingViewModel.getGenreResponse()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager
                val visibleItemCount = layoutManager?.childCount ?: 0
                val totalItemCount = layoutManager?.itemCount ?: 0
                val firstVisibleItemPosition =
                    (layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition() ?: 0

                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                    upcomingViewModel.loadMoreUpcomingMovies()
                }
            }
        })
    }
}
