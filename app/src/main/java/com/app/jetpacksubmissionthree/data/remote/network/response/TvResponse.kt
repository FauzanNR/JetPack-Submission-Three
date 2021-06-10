package com.app.jetpacksubmissionthree.data.remote.network.response

data class TvResponse(
    val page: Int,
    val results: List<ResultTv>,
    val total_pages: Int,
    val total_results: Int
)