package vn.linh.vqherokuapp.feature.user

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*
import vn.linh.vqherokuapp.R
import vn.linh.vqherokuapp.data.model.User
import vn.linh.vqherokuapp.extension.EndlessRecyclerViewScrollListener
import vn.linh.vqherokuapp.extension.isOdd
import vn.linh.vqherokuapp.feature.base.recyclerview.RecyclerViewItem
import vn.linh.vqherokuapp.feature.model.NetworkState
import vn.linh.vqherokuapp.feature.model.Status
import vn.linh.vqherokuapp.feature.user.adapter.UserAdapter
import vn.linh.vqherokuapp.feature.user.adapter.decoration.GridUserDecoration
import vn.linh.vqherokuapp.feature.user.adapter.model.DividerItem
import vn.linh.vqherokuapp.feature.user.adapter.model.ItemItem
import vn.linh.vqherokuapp.feature.user.adapter.model.UserItem
import javax.inject.Inject

class UserActivity : DaggerAppCompatActivity() {

    private lateinit var userAdapter: UserAdapter

    @Inject
    lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initialToolbar()
        initialRecyclerUsers()
        observer()
        viewModel.loadInitial()
    }

    private fun initialToolbar() {
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initialRecyclerUsers() {
        userAdapter = UserAdapter()
        val layoutManager = GridLayoutManager(this, GRID_SPAN_COUNT)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (userAdapter.getItemViewType(position) == UserAdapter.ViewType.STATE.value) {
                    return GRID_SPAN_COUNT
                }
                return userAdapter.getItem(position).spanSize
            }
        }
        recycler_users.layoutManager = layoutManager
        recycler_users.adapter = userAdapter
        val spacing = resources.getDimensionPixelOffset(R.dimen.dp_10)
        recycler_users.addItemDecoration(
            GridUserDecoration(spacing, spacing, 0, spacing, spacing))
        recycler_users.addOnScrollListener(object : EndlessRecyclerViewScrollListener(
            layoutManager) {
            override fun onLoadMore(page: Int) {
                viewModel.loadMore()
            }
        })
    }

    private fun observer() {
        viewModel.newUsers.observe(this, Observer { users ->
            users?.let {
                userAdapter.add(mapToRecyclerViewItem(it))
            }
        })
        viewModel.initialLoad.observe(this, Observer {
            updateNetworkState(it)
        })
        viewModel.networkState.observe(this, Observer {
            userAdapter.setNetworkState(it)
        })
    }

    private fun mapToRecyclerViewItem(users: List<User>): List<RecyclerViewItem> {
        val list = arrayListOf<RecyclerViewItem>()
        for (i in 0 until users.size) {
            if (i > 0 || userAdapter.itemCount > 0) {
                list.add(DividerItem(GRID_SPAN_COUNT))
            }
            val user = users[i]
            list.add(UserItem(user.name, user.image, GRID_SPAN_COUNT))
            val itemSize = user.items.size
            for (j in 0 until itemSize) {
                val item: ItemItem = if (j == 0 && itemSize.isOdd()) {
                    ItemItem(user.items[j], GRID_SPAN_COUNT)
                } else {
                    ItemItem(user.items[j])
                }
                list.add(item)
            }
        }
        return list
    }

    private fun updateNetworkState(networkState: NetworkState?) {
        progress_bar_loading.visibility = if (networkState?.status == Status.LOADING) View.VISIBLE else View.GONE
    }

    companion object {
        const val GRID_SPAN_COUNT = 2
    }
}
