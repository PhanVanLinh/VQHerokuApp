package vn.linh.vqherokuapp.feature.user

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import vn.linh.vqherokuapp.data.model.User
import vn.linh.vqherokuapp.feature.model.NetworkState
import vn.linh.vqherokuapp.interactor.GetUserDataSource

class UserViewModel(private val getUserSourceFactory: GetUserDataSource.Factory) : ViewModel() {

    var users: LiveData<PagedList<User>>

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setInitialLoadSizeHint(PAGE_SIZE * 2)
            .setEnablePlaceholders(false)
            .build()
        users = LivePagedListBuilder<Int, User>(getUserSourceFactory, config).build()
    }

    fun getInitialState(): LiveData<NetworkState> = Transformations.switchMap<GetUserDataSource, NetworkState>(
        getUserSourceFactory.usersDataSourceLiveData) { it.initialLoad }

    fun getNetworkState(): LiveData<NetworkState> = Transformations.switchMap<GetUserDataSource, NetworkState>(
        getUserSourceFactory.usersDataSourceLiveData) { it.networkState }


    override fun onCleared() {
        super.onCleared()
        getUserSourceFactory.usersDataSourceLiveData.value?.dispose()
    }

    companion object {
        const val PAGE_SIZE = 10
    }
}