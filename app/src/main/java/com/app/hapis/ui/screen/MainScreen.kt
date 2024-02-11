package com.app.hapis.ui.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.hapis.R
import com.app.hapis.databinding.HomeScreenBinding
import com.app.hapis.response.ResultsItem
import com.app.hapis.ui.adapter.ViewPagerAdapter
import com.app.hapis.utils.CardTransformer
import com.app.hapis.utils.HorizontalMarginItemDecoration
import com.app.hapis.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainScreen : Fragment(R.layout.home_screen) {

    private val viewModel: MainViewModel by viewModels()
    private val adapter by lazy { ViewPagerAdapter() }
    private lateinit var binding: HomeScreenBinding

    private var isLoading = false
    private var currentPage = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = HomeScreenBinding.bind(view)

        // Menampilkan ProgressBar saat aplikasi pertama kali dibuka
        binding.progressBar.visibility = View.VISIBLE

        viewModel.listMovies.observe(viewLifecycleOwner, movieDataObserver)
        viewModel.getPopularMovies(currentPage)
        setUpPager()
    }

    private val movieDataObserver = Observer<List<ResultsItem>> { data ->
        // Menghilangkan ProgressBar saat data selesai dimuat
        binding.progressBar.visibility = View.GONE
        // Hide ProgressBar when data is loaded
        binding.progressBar.visibility = View.GONE

        if (currentPage == 1) {
            adapter.submitList(data)
        } else {
            val currentList = adapter.currentList.toMutableList()
            currentList.addAll(data)
            adapter.submitList(currentList)
        }

        isLoading = false
    }

    private fun setUpPager() {
        binding.viewPagerHome.adapter = adapter
        binding.viewPagerHome.offscreenPageLimit = 1
        binding.viewPagerHome.setPageTransformer(CardTransformer(requireContext()))
        binding.searchIv.setOnClickListener {
            findNavController().navigate(R.id.searchScreen)
        }
        val itemDecoration = HorizontalMarginItemDecoration(
            requireContext(),
            R.dimen.viewpager_current_item_horizontal_margin
        )
        binding.viewPagerHome.addItemDecoration(itemDecoration)

        // Set up the RecyclerView scroll listener
        binding.viewPagerHome.viewTreeObserver.addOnPreDrawListener {
            // Check if recyclerView is ready
            val recyclerView = binding.viewPagerHome.getChildAt(0) as RecyclerView
            recyclerView.layoutManager?.let { layoutManager ->
                recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)

                        val totalItemCount = layoutManager.itemCount
                        val lastVisibleItem = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                        val endHasBeenReached = lastVisibleItem + 5 >= totalItemCount // arbitrary value, adjust as needed

                        if (totalItemCount > 0 && endHasBeenReached && !isLoading) {
                            // Show ProgressBar
                            binding.progressBar.visibility = View.VISIBLE

                            // Load more data here
                            loadMoreData()
                        }
                    }
                })
            }
            true
        }

        // Hapus blok kode itemClickListener
    }

    private fun loadMoreData() {
        isLoading = true
        currentPage++
        viewModel.getPopularMovies(currentPage)
    }
}
