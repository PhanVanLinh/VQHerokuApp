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
    val compositeDisposable: CompositeDisposable) : ViewModel() {
    val initialLoad = MutableLiveData<NetworkState>()
    val networkState = MutableLiveData<NetworkState>()
    var users: MutableLiveData<MutableList<User>> = MutableLiveData()

    init {

    }

    fun loadInitial() {
        load(STARTING_OFFSET, PAGE_SIZE * 2) {
            val newList = users.value ?: arrayListOf()
            newList.addAll(it)
            users.value = newList
        }
    }

    fun loadMore() {
        load(users.value!!.size, PAGE_SIZE) {
            val newList = users.value ?: arrayListOf()
            newList.addAll(it)
            users.value = newList
        }
    }

    private fun load(offSet: Int, limit: Int, onResult: (users: List<User>) -> Unit): Disposable {
        return userRepository.getUsers(offSet, limit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                networkState.postValue(LoadingState())
            }
            .subscribe({
                onResult.invoke(it)
                networkState.postValue(SuccessState())
            }, {
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