package com.ilatyphi95.gads2020leaderboard

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("items")
fun setRecyclerViewItems(
    recyclerView: RecyclerView,
    items: List<RecyclerItem>?
) {
    var adapter = (recyclerView.adapter as? UniversalListAdapter)
    if (adapter == null) {
        adapter = UniversalListAdapter()
        recyclerView.adapter = adapter
    }

    adapter.submitList(items.orEmpty())
}