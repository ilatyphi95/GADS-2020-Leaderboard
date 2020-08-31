package com.ilatyphi95.gads2020leaderboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


class UniversalListAdapter :
    ListAdapter<RecyclerItem, BindingViewHolder>(DiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return getItem(position).layoutId
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, viewType, parent, false)
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: BindingViewHolder,
        position: Int
    ) {
        getItem(position).bind(holder.binding)
        holder.binding.executePendingBindings()
    }
}

data class RecyclerItem(val data: Any, @LayoutRes val layoutId: Int, val variableId: Int) {
    fun bind(binding: ViewDataBinding) {
        binding.setVariable(variableId, data)
    }
}

private class DiffCallback : DiffUtil.ItemCallback<RecyclerItem>() {
    override fun areItemsTheSame(
        oldItem: RecyclerItem,
        newItem: RecyclerItem
    ): Boolean {
        val oldData = oldItem.data
        val newData = newItem.data
        // Use appropriate comparator's method if both items implement the interface
        // and rely on plain 'equals' otherwise
        return if (oldData is RecyclerItemComparator
            && newData is RecyclerItemComparator
        ) {
            oldData.isSameItem(newData)
        } else oldData == newData
    }

    override fun areContentsTheSame(
        oldItem: RecyclerItem,
        newItem: RecyclerItem
    ): Boolean {
        val oldData = oldItem.data
        val newData = newItem.data
        return if (oldData is RecyclerItemComparator
            && newData is RecyclerItemComparator
        ) {
            oldData.isSameContent(newData)
        } else oldItem == newItem
    }
}

class BindingViewHolder(
    val binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root)

interface RecyclerItemComparator {
    fun isSameItem(other: Any): Boolean
    fun isSameContent(other: Any): Boolean
}