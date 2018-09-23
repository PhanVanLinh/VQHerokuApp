package vn.linh.vqherokuapp.feature.user.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import vn.linh.vqherokuapp.R
import vn.linh.vqherokuapp.feature.base.recyclerview.RecyclerViewItem
import vn.linh.vqherokuapp.feature.model.LoadingState
import vn.linh.vqherokuapp.feature.model.NetworkState
import vn.linh.vqherokuapp.feature.user.adapter.model.DividerItem
import vn.linh.vqherokuapp.feature.user.adapter.model.ItemItem
import vn.linh.vqherokuapp.feature.user.adapter.model.UserItem
import vn.linh.vqherokuapp.feature.user.adapter.viewholder.DividerViewHolder
import vn.linh.vqherokuapp.feature.user.adapter.viewholder.ItemViewHolder
import vn.linh.vqherokuapp.feature.user.adapter.viewholder.NetworkStateViewHolder
import vn.linh.vqherokuapp.feature.user.adapter.viewholder.UserViewHolder

class UserAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var networkState: NetworkState? = null
    private val dataSet: MutableList<RecyclerViewItem> = arrayListOf()

    fun add(items: List<RecyclerViewItem>) {
        dataSet.addAll(items)
        notifyItemRangeInserted(dataSet.size - items.size, items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.USER.value -> UserViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent,
                    false))
            ViewType.ITEM.value -> ItemViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_user_item, parent,
                    false))
            ViewType.DIVIDER.value -> DividerViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_divider, parent,
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
        return dataSet.size > 0 && networkState != null
    }

    override fun getItemViewType(position: Int): Int {
        if (position == itemCount - 1 && hasNetworkStateRow()) {
            return ViewType.STATE.value
        }
        if (getItem(position) is UserItem) {
            return ViewType.USER.value
        }
        if (getItem(position) is DividerItem) {
            return ViewType.DIVIDER.value
        }
        return ViewType.ITEM.value
    }

    override fun getItemCount(): Int {
        return dataSet.size + if (hasNetworkStateRow()) 1 else 0
    }

    fun getItem(position: Int): RecyclerViewItem {
        return dataSet[position]
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        if (newNetworkState == null) {
            this.networkState = null
            notifyItemRemoved(itemCount)
            return
        }
        if (newNetworkState is LoadingState) {
            this.networkState = newNetworkState
        }
    }

    enum class ViewType(val value: Int) {
        USER(0),
        ITEM(1),
        DIVIDER(2),
        STATE(3)
    }
}