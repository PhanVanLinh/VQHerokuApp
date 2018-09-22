package vn.linh.vqherokuapp.interactor

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import android.arch.paging.PositionalDataSource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import vn.linh.vqherokuapp.data.model.User
import vn.linh.vqherokuapp.data.source.UserRepository
import vn.linh.vqherokuapp.feature.model.ErrorState
import vn.linh.vqherokuapp.feature.model.LoadingState
import vn.linh.vqherokuapp.feature.model.NetworkState
import vn.linh.vqherokuapp.feature.model.SuccessState
import javax.inject.Inject

class GetUserDataSource @Inject constructor(
    private val userRepository: UserRepository) : PositionalDataSource<User>() {
    private val compositeDisposable = CompositeDisposable()

    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<User>) {
        compositeDisposable.add(
            load(STARTING_OFFSET, params.requestedLoadSize, object : LoadCallback<User>() {
                override fun onResult(data: List<User>) {
                    callback.onResult(data, STARTING_OFFSET)
                }
            }))
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<User>) {
        compositeDisposable.add(
            load(params.startPosition, params.loadSize, object : LoadCallback<User>() {
                override fun onResult(data: List<User>) {
                    callback.onResult(data)
                }
            }))
    }

    private fun load(offSet: Int, limit: Int, callback: LoadCallback<User>): Disposable {
        val isInitialLoad = offSet == STARTING_OFFSET
        return userRepository.getUsers(offSet, limit)
            .doOnSubscribe {
                if (isInitialLoad) {
                    initialLoad.postValue(LoadingState())
                }
                networkState.postValue(LoadingState())
            }
            .subscribe({
                callback.onResult(it)
                if (isInitialLoad) {
                    initialLoad.postValue(SuccessState())
                }
                networkState.postValue(SuccessState())
            }, {
                if (isInitialLoad) {
                    initialLoad.postValue(ErrorState(it.message))
                }
                networkState.postValue(ErrorState(it.message))
            })
    }

    fun dispose() {
        compositeDisposable.dispose()
    }

    abstract class LoadCallback<T> {
        abstract fun onResult(data: List<T>)
    }

    companion object {
        const val STARTING_OFFSET = 0
    }

    class Factory @Inject constructor(
        private val getUserDataSource: GetUserDataSource) : DataSource.Factory<Int, User>() {
        val usersDataSourceLiveData = MutableLiveData<GetUserDataSource>()
        override fun create(): DataSource<Int, User> {
            usersDataSourceLiveData.postValue(getUserDataSource)
            return getUserDataSource
        }
    }
}