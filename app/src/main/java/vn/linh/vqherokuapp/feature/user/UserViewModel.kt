package vn.linh.vqherokuapp.feature.user

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import vn.linh.vqherokuapp.data.model.User
import vn.linh.vqherokuapp.data.source.UserRepository
import vn.linh.vqherokuapp.feature.model.ErrorState
import vn.linh.vqherokuapp.feature.model.LoadingState
import vn.linh.vqherokuapp.feature.model.NetworkState
import vn.linh.vqherokuapp.feature.model.SuccessState

class UserViewModel(private val userRepository: UserRepository,
    private val compositeDisposable: CompositeDisposable) : ViewModel() {
    val initialLoad = MutableLiveData<NetworkState>()
    val networkState = MutableLiveData<NetworkState>()
    var newUsers: MutableLiveData<List<User>> = MutableLiveData()
    var offset: Int = STARTING_OFFSET

    init {

    }

    fun loadInitial() {
        load(offset, PAGE_SIZE * 2) {
            newUsers.value = it
        }
    }

    fun loadMore() {
        load(offset, PAGE_SIZE) {
            newUsers.value = it
        }
    }

    private fun load(offSet: Int, limit: Int, onResult: (users: List<User>) -> Unit): Disposable {
        val isInitialLoad = offSet == STARTING_OFFSET
        return userRepository.getUsers(offSet, limit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                if (isInitialLoad) {
                    initialLoad.postValue(LoadingState())
                }
                networkState.postValue(LoadingState())
            }
            .subscribe({
                onResult.invoke(it)
                if (isInitialLoad) {
                    initialLoad.postValue(SuccessState())
                }
                networkState.postValue(SuccessState())
                offset += it.size
            }, {
                if (isInitialLoad) {
                    initialLoad.postValue(ErrorState(it.message))
                }
                networkState.postValue(ErrorState(it.message))
            })
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    companion object {
        const val STARTING_OFFSET = 0
        const val PAGE_SIZE = 5
    }
}