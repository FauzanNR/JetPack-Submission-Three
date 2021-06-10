package com.app.jetpacksubmissionthree.data.remote.network


import com.app.jetpacksubmissionthree.BuildConfig
import com.app.jetpacksubmissionthree.data.remote.network.response.FilmResponse
import com.app.jetpacksubmissionthree.data.remote.network.response.ResultMovie
import com.app.jetpacksubmissionthree.data.remote.network.response.ResultTv
import com.app.jetpacksubmissionthree.data.remote.network.response.TvResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    //https://api.themoviedb.org/3/movie/popular?api_key=0d2f033f7aaa01af4e9a30f3f7cea50d&language=en-US&page=1
    @GET("movie/popular")
    suspend fun getPopularMovie(
        @Query("api_key") api_key: String = BuildConfig.TMDB_TOKEN,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): Response<FilmResponse>

    @GET("tv/popular")
    suspend fun getPopularTv(
        @Query("api_key") api_key: String = BuildConfig.TMDB_TOKEN,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): Response<TvResponse>


    @GET("movie/{movie_id}")
    suspend fun getDetailMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_TOKEN
    ): Response<ResultMovie>

    @GET("tv/{tv_id}")
    suspend fun getDetailTv(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_TOKEN
    ): Response<ResultTv>


}