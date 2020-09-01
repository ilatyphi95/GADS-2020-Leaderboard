package com.ilatyphi95.gads2020leaderboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


class CollectorAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val itemCount: Array<String> by lazy {
        fragment.resources.getStringArray(R.array.tabs_names)
    }

    override fun getItemCount() = itemCount.size

    override fun createFragment(position: Int): Fragment {

        val fragment = LeaderFragment()
        fragment.arguments = Bundle().apply {
            putInt(
                ARG_OBJECT,
                when (position) {
                      0, 1 -> position
                    else -> 0
                }
            )
        }
        return fragment
    }
}
