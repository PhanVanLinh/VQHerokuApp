package vn.linh.vqherokuapp.feature.user.adapter.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.item_user_item.view.*
import vn.linh.vqherokuapp.R
import vn.linh.vqherokuapp.extension.loadImage
import vn.linh.vqherokuapp.feature.user.adapter.model.ItemItem

class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(item: ItemItem) {
        itemView.image_item.loadImage(item.image, R.drawable.ic_cat_playholder)
    }
}