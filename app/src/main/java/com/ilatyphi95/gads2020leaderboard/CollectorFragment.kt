package com.ilatyphi95.gads2020leaderboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.ilatyphi95.gads2020leaderboard.databinding.FragmentCollectorBinding

class CollectorFragment() : Fragment() {
    lateinit var binding: FragmentCollectorBinding

    val stringArray by lazy {
        resources.getStringArray(R.array.tabs_names)
    }

    val collectorAdapter by lazy {
        CollectorAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_collector, container, false)

        binding.submit.setOnClickListener { startActivity(Intent(requireContext(), SubmitActivity::class.java)) }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.pager.adapter = collectorAdapter

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = stringArray[position]
        }.attach()
    }
}