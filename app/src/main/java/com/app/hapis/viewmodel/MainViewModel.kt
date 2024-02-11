package com.app.hapis.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.hapis.network.ApiConfig
import com.app.hapis.response.PopulerResponse
import com.app.hapis.response.ResultsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class MainViewModel : ViewModel() {
    private val _listMovies = MutableLiveData<List<ResultsItem>>()
    open val listMovies: LiveData<List<ResultsItem>> = _listMovies

    open fun getPopularMovies(page: Int) {
        val client = ApiConfig.getApiService().getPopularMovies(page)
        client.enqueue(object : Callback<PopulerResponse> {
            override fun onResponse(
                call: Call<PopulerResponse>,
                response: Response<PopulerResponse>
            ) {
                if (response.isSuccessful) {
                    val results = response.body()?.results
                    _listMovies.value = results ?: emptyList()
                } else {
                    Log.e("Error", "Failed to fetch data: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<PopulerResponse>, t: Throwable) {
                Log.e("Error", "Network error: ${t.message}")
                t.printStackTrace()
            }
        })
    }
}

