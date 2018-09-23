package vn.linh.vqherokuapp.feature.user.adapter

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import vn.linh.vqherokuapp.R
import vn.linh.vqherokuapp.feature.base.recyclerview.RecyclerViewItem
import vn.linh.vqherokuapp.feature.model.LoadingState
import vn.linh.vqherokuapp.feature.model.NetworkState
import vn.linh.vqherokuapp.feature.model.SuccessState
import vn.linh.vqherokuapp.feature.user.adapter.model.ItemItem
import vn.linh.vqherokuapp.feature.user.adapter.model.UserItem
import vn.linh.vqherokuapp.feature.user.adapter.viewholder.ItemViewHolder
import vn.linh.vqherokuapp.feature.user.adapter.viewholder.NetworkStateViewHolder
import vn.linh.vqherokuapp.feature.user.adapter.viewholder.UserViewHolder

class UserAdapter : ListAdapter<RecyclerViewItem, RecyclerView.ViewHolder>(
    DiffCallback) {
    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.USER.value -> UserViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent,
                    false))
            ViewType.ITEM.value -> ItemViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_user_item, parent,
                    false))
            ViewType.STATE.value -> NetworkStateViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_network_state, parent,
                    false))
            else -> throw IllegalArgumentException("unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserViewHolder -> getItem(position)?.let {
                holder.bind(it as UserItem)
            }
            is NetworkStateViewHolder -> networkState?.let {
                holder.bind(it)
            }
            is ItemViewHolder -> holder.bind(getItem(position) as ItemItem)
        }
    }

    private fun hasNetworkStateRow(): Boolean {
        return networkState != null
    }

    override fun getItemViewType(position: Int): Int {
        if (position == itemCount - 1 && hasNetworkStateRow()) {
            return ViewType.STATE.value
        }
        if (getItem(position) is UserItem) {
            return ViewType.USER.value
        }
        return ViewType.ITEM.value
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasNetworkStateRow()) 1 else 0
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        if (newNetworkState is SuccessState) {
            if (hasNetworkStateRow()) {
                this.networkState = null
                notifyItemRemoved(super.getItemCount() - 1)
            }
        }

        val networkStateRowExisted = hasNetworkStateRow()
        if (newNetworkState is LoadingState) {
            this.networkState = newNetworkState
            if (networkStateRowExisted) {
                notifyItemChanged(itemCount - 1)
            } else {
                notifyItemInserted(super.getItemCount())
            }
        }
    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<RecyclerViewItem>() {
            override fun areItemsTheSame(oldItem: RecyclerViewItem,
                newItem: RecyclerViewItem): Boolean {
                return true
            }

            override fun areContentsTheSame(oldItem: RecyclerViewItem,
                newItem: RecyclerViewItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    enum class ViewType(val value: Int) {
        USER(0),
        ITEM(1),
        STATE(2)
    }
}