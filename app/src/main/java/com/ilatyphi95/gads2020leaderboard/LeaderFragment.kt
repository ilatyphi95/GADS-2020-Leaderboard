package com.ilatyphi95.gads2020leaderboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.ilatyphi95.gads2020leaderboard.databinding.FragmentLeaderBinding

const val ARG_OBJECT = "object"
class LeaderFragment : Fragment() {

    private lateinit var binding : FragmentLeaderBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_leader, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            binding.textView.text = getInt(ARG_OBJECT).toString()
        }
    }
}