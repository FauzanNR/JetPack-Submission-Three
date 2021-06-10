package com.app.jetpacksubmissionthree.data.remote.network.response

data class FilmResponse(
    val page: Int,
    val results: List<ResultMovie>,
    val total_pages: Int,
    val total_results: Int
)