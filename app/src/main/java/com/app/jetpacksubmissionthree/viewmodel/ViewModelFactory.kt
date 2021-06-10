package com.app.jetpacksubmissionthree.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.jetpacksubmissionthree.data.repository.DataRepository
import com.app.jetpacksubmissionthree.di.Injection
import com.app.jetpacksubmissionthree.ui.detail.DetailViewModel
import com.app.jetpacksubmissionthree.ui.favorite.movie.MovieFavoriteViewModel
import com.app.jetpacksubmissionthree.ui.favorite.tv.TvShowFavoriteViewModel
import com.app.jetpacksubmissionthree.ui.movie.MovieViewModel
import com.app.jetpacksubmissionthree.ui.tv.TvViewModel

class ViewModelFactory private constructor(private val dataRepository: DataRepository) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory = instance ?: synchronized(this) {
            ViewModelFactory(Injection.provideRepository(context)).apply {
                instance = this
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                MovieViewModel(dataRepository) as T
            }
            modelClass.isAssignableFrom(TvViewModel::class.java) -> {
                TvViewModel(dataRepository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(dataRepository) as T
            }
            modelClass.isAssignableFrom(MovieFavoriteViewModel::class.java) -> {
                MovieFavoriteViewModel(dataRepository) as T
            }
            modelClass.isAssignableFrom(TvShowFavoriteViewModel::class.java) -> {
                TvShowFavoriteViewModel(dataRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}