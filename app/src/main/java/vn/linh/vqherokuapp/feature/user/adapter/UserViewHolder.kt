package vn.linh.vqherokuapp.feature.user.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_user.view.*
import vn.linh.vqherokuapp.data.model.User

class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bindTo(user: User) {
        Glide.with(itemView.context)
            .load(user.image)
            .into(itemView.image_avatar)
        itemView.text_name.text = user.name
    }
}