package vn.linh.vqherokuapp.feature.user.adapter

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import vn.linh.vqherokuapp.R
import vn.linh.vqherokuapp.data.model.User
import vn.linh.vqherokuapp.feature.model.LoadingState
import vn.linh.vqherokuapp.feature.model.NetworkState
import vn.linh.vqherokuapp.feature.model.SuccessState

class UserAdapter : PagedListAdapter<User, RecyclerView.ViewHolder>(
    DiffCallback) {
    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_user -> UserViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent,
                    false))
            R.layout.item_network_state -> NetworkStateViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_network_state, parent,
                    false))
            else -> throw IllegalArgumentException("unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is UserViewHolder) {
            getItem(position)?.let {
                holder.bindTo(it)
            }
        } else if (holder is NetworkStateViewHolder) {
            networkState?.let {
                holder.bindTo(it)
            }
        }
    }

    private fun hasNetworkStateRow(): Boolean {
        return networkState != null
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1 && hasNetworkStateRow()) {
            R.layout.item_network_state
        } else {
            R.layout.item_user
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasNetworkStateRow()) 1 else 0
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        currentList?.let {
            if (newNetworkState is SuccessState) {
                if (hasNetworkStateRow()) {
                    this.networkState = null
                    notifyItemRemoved(super.getItemCount() - 1)
                }
                return
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
    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }

}