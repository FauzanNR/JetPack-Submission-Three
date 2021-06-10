package com.app.jetpacksubmissionthree.ui.favorite

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.jetpacksubmissionthree.ui.favorite.movie.MovieFavoriteFragment
import com.app.jetpacksubmissionthree.ui.favorite.tv.TvShowFavoriteFragment

class SectionPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = MovieFavoriteFragment()
            1 -> fragment = TvShowFavoriteFragment()
        }
        return fragment as Fragment
    }
}