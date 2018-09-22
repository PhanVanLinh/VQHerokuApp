package vn.linh.vqherokuapp.data.source.remote

import io.reactivex.Single
import vn.linh.vqherokuapp.data.model.User
import vn.linh.vqherokuapp.data.source.remote.api.HerokuNoneApi
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(private val herokuNoneApi: HerokuNoneApi) {
    fun getUsers(offset: Int, limit: Int): Single<List<User>> {
        return herokuNoneApi.getUsers(offset, limit).map { it.data?.users }
    }
}