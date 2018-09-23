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

        initialRecyclerUsers()
        observer()
        viewModel.loadInitial()
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
        for (user in users) {
            list.add(UserItem(user.name, user.image, GRID_SPAN_COUNT))

            val itemSize = user.items.size
            for (i in 0 until itemSize) {
                val item: ItemItem = if (i == 0 && itemSize.isOdd()) {
                    ItemItem(user.items[i], GRID_SPAN_COUNT)
                } else {
                    ItemItem(user.items[i])
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
