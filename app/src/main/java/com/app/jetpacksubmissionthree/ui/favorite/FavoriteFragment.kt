package com.app.jetpacksubmissionthree.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.app.jetpacksubmissionthree.R
import com.app.jetpacksubmissionthree.databinding.FavoriteFragmentBinding
import com.google.android.material.tabs.TabLayoutMediator

class FavoriteFragment : Fragment() {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.movie_tab_title,
            R.string.tv_tab_title
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FavoriteFragmentBinding.bind(
            inflater.inflate(
                R.layout.favorite_fragment,
                container,
                false
            )
        )
        val secPagerAdapter = SectionPagerAdapter(this)
        binding.apply {
            idViewPagerFav.adapter = secPagerAdapter
            TabLayoutMediator(idTabFav, idViewPagerFav) { tab, pos ->
                tab.text = resources.getString(TAB_TITLES[pos])
            }.attach()
        }
        return binding.root.rootView
    }
}