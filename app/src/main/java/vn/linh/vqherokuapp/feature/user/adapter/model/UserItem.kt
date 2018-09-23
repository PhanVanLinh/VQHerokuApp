package vn.linh.vqherokuapp.feature.user.adapter.model

import vn.linh.vqherokuapp.feature.base.recyclerview.RecyclerViewItem

data class UserItem(val name: String, val image: String,
    override val spanSize: Int = 1) : RecyclerViewItem(spanSize) {

}