package vn.linh.vqherokuapp.feature.user

import android.arch.lifecycle.Observer
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*
import vn.linh.vqherokuapp.R
import vn.linh.vqherokuapp.data.model.User
import vn.linh.vqherokuapp.feature.model.NetworkState
import vn.linh.vqherokuapp.feature.model.Status
import vn.linh.vqherokuapp.feature.user.adapter.UserAdapter
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
    }

    private fun initialRecyclerUsers() {
        userAdapter = UserAdapter()
        usersRecyclerView.layoutManager = LinearLayoutManager(this)
        usersRecyclerView.adapter = userAdapter
    }

    private fun observer() {
        viewModel.users.observe(this, Observer<PagedList<User>> { userAdapter.submitList(it) })
        viewModel.getInitialState().observe(this, Observer {
            updateNetworkState(it)
        })
        viewModel.getNetworkState().observe(this, Observer {
            userAdapter.setNetworkState(it)
        })
    }

    private fun updateNetworkState(networkState: NetworkState?) {
        progress_bar_loading.visibility = if (networkState?.status == Status.LOADING) View.VISIBLE else View.GONE
    }
}
