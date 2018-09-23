package vn.linh.vqherokuapp.feature.user.adapter.decoration

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import vn.linh.vqherokuapp.extension.GridSpacingDecoration
import vn.linh.vqherokuapp.feature.user.adapter.UserAdapter

class GridUserDecoration(top: Int, right: Int, bottom: Int, left: Int,
    middle: Int) : GridSpacingDecoration(top, right, bottom, left, middle) {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.layoutManager.getItemViewType(view) == UserAdapter.ViewType.DIVIDER.value) {
            outRect.left = 0
            outRect.right = 0
        }
    }
}