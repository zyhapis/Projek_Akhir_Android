package com.app.hapis.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.hapis.network.ApiConfig
import com.app.hapis.response.ComingSoonResponse
import com.app.hapis.response.GenreResponse
import com.app.hapis.response.ResultsComingItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class UpcomingViewModel : ViewModel() {

    private val _listMovies = MutableLiveData<List<ResultsComingItem>>()
    open val listMovies: LiveData<List<ResultsComingItem>> = _listMovies

    private val _genreResponse = MutableLiveData<GenreResponse>()
    open val genreResponse: LiveData<GenreResponse> = _genreResponse

    private var currentPage = 1
    private var isFetching = false

    open fun getUpcomingMovies(page: Int) {
        if (isFetching) return

        isFetching = true

        val client = ApiConfig.getApiService().getComingSoon(page)
        client.enqueue(object : Callback<ComingSoonResponse> {
            override fun onResponse(
                call: Call<ComingSoonResponse>,
                response: Response<ComingSoonResponse>
            ) {
                isFetching = false
                if (response.isSuccessful) {
                    val results = response.body()?.results
                    _listMovies.value = (_listMovies.value ?: emptyList()) + (results ?: emptyList())
                    currentPage = page
                } else {
                    Log.e("Error", "Failed to fetch data: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ComingSoonResponse>, t: Throwable) {
                isFetching = false
                Log.e("Error", "Network error: ${t.message}")
                t.printStackTrace()
            }
        })
    }

    open fun getGenreResponse() {
        val client = ApiConfig.getApiService().getComingGenre()
        client.enqueue(object : Callback<GenreResponse> {
            override fun onResponse(
                call: Call<GenreResponse>,
                response: Response<GenreResponse>
            ) {
                if (response.isSuccessful) {
                    _genreResponse.value = response.body()
                } else {
                    Log.e("Error", "Failed to fetch genre data: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<GenreResponse>, t: Throwable) {
                Log.e("Error", "Network error: ${t.message}")
                t.printStackTrace()
            }
        })
    }

    open fun loadMoreUpcomingMovies() {
        val nextPage = currentPage + 1
        getUpcomingMovies(nextPage)
    }
}
