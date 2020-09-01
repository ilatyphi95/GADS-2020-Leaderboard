package com.ilatyphi95.gads2020leaderboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ilatyphi95.gads2020leaderboard.databinding.FragmentLeaderBinding

const val ARG_OBJECT = "object"
class LeaderFragment : Fragment() {

    private lateinit var binding : FragmentLeaderBinding
    private val viewModel: LeaderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_leader, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recycler = binding.recycler
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            val viewType = getInt(ARG_OBJECT)

            val adapter = UniversalListAdapter()

            viewModel.apply {
                binding.viewmodel = this

                learningList.observe(viewLifecycleOwner, { list ->
                    if(viewType == 0) {
                        adapter.submitList(list)
                    }
                })

                iqList.observe(viewLifecycleOwner, { list ->
                    if(viewType == 1) {
                        adapter.submitList(list)
                    }
                })

            }
            recycler.adapter = adapter

            when(viewType) {
                0 -> adapter.submitList(viewModel.learningList.value)
                1 -> adapter.submitList(viewModel.iqList.value)
            }
        }
    }
}