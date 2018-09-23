package vn.linh.vqherokuapp.feature.user.adapter.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.item_user.view.*
import vn.linh.vqherokuapp.R
import vn.linh.vqherokuapp.extension.loadImage
import vn.linh.vqherokuapp.feature.user.adapter.model.UserItem

class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(user: UserItem) {
        itemView.image_avatar.loadImage(user.image, R.drawable.ic_cat_playholder,true)
        itemView.text_name.text = user.name
    }
}