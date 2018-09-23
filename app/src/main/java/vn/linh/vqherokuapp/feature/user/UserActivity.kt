package vn.linh.vqherokuapp.feature.user

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*
import vn.linh.vqherokuapp.R
import vn.linh.vqherokuapp.data.model.User
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
        usersRecyclerView.layoutManager = LinearLayoutManager(this)
        usersRecyclerView.adapter = userAdapter
    }

    private fun observer() {
        viewModel.users.observe(this, Observer { users ->
            users?.let {
                userAdapter.submitList(mapToRecyclerView(it))
            }
        })
    }

    private fun mapToRecyclerView(users: List<User>): List<RecyclerViewItem> {
        val list = arrayListOf<RecyclerViewItem>()
        for (user in users) {
            list.add(UserItem(user.name, user.image))
            for (image in user.items) {
                list.add(ItemItem(image))
            }
        }
        return list
    }

    private fun updateNetworkState(networkState: NetworkState?) {
        progress_bar_loading.visibility = if (networkState?.status == Status.LOADING) View.VISIBLE else View.GONE
    }
}
