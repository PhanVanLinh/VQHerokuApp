package vn.linh.vqherokuapp.feature.user.adapter.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.item_network_state.view.*
import vn.linh.vqherokuapp.feature.model.NetworkState
import vn.linh.vqherokuapp.feature.model.Status

class NetworkStateViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(networkState: NetworkState) {
        itemView.progress_bar_loading.visibility = if (networkState.status == Status.LOADING) View.VISIBLE else View.GONE
    }
}