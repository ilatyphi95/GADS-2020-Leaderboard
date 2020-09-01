package com.ilatyphi95.gads2020leaderboard

import android.view.View
import android.widget.ProgressBar
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

@BindingAdapter("resourceVisibility")
fun setVisibility(progressBar: ProgressBar, resource: Resource<Any>?) {
    if(resource == null) {
        progressBar.visibility = View.GONE
        return
    }

    when(resource.status) {
        Status.LOADING -> progressBar.visibility = View.VISIBLE
        Status.ERROR -> progressBar.visibility = View.GONE
        Status.SUCCESS -> progressBar.visibility = View.GONE
    }
}

@BindingAdapter("resourceData")
fun showData(recyclerView: RecyclerView, resource: Resource<List<RecyclerItem>?>?) {

    var adapter = (recyclerView.adapter as? UniversalListAdapter)
    if (adapter == null) {
        adapter = UniversalListAdapter()
        recyclerView.adapter = adapter
    }

    when(resource?.status) {
        Status.LOADING -> {
            recyclerView.visibility = View.GONE
            adapter.submitList(emptyList())
        }

        Status.ERROR -> {
            recyclerView.visibility = View.GONE
            adapter.submitList(emptyList())
        }

        Status.SUCCESS -> {
            recyclerView.visibility = View.VISIBLE
            adapter.submitList(resource.data.orEmpty())
        }
    }
}