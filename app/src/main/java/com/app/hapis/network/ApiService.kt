package com.app.hapis.network

import com.app.hapis.response.ComingSoonResponse
import com.app.hapis.response.DetailResponse
import com.app.hapis.response.GenreResponse
import com.app.hapis.response.PopulerResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular?api_key=edf096334e751abfcadeb90d5a0eef63")
    fun getPopularMovies(@Query("page") page: Int): Call<PopulerResponse>

    @GET("movie/{movie_id}?api_key=edf096334e751abfcadeb90d5a0eef63")
    fun getDetailMovies(): Call<DetailResponse>

    @GET("movie/upcoming?api_key=edf096334e751abfcadeb90d5a0eef63")
    fun getComingSoon(@Query("page") page: Int): Call<ComingSoonResponse>

    @GET("genre/movie/list?api_key=edf096334e751abfcadeb90d5a0eef63")
    fun getComingGenre(): Call<GenreResponse>
}
