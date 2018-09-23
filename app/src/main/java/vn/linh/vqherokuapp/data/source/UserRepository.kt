package vn.linh.vqherokuapp.data.source

import io.reactivex.Single
import vn.linh.vqherokuapp.data.model.User
import vn.linh.vqherokuapp.data.source.remote.UserRemoteDataSource

class UserRepository(private val remoteDataSource: UserRemoteDataSource) {

    fun getUsers(offset: Int, limit: Int): Single<List<User>> {
        return remoteDataSource.getUsers(offset, limit)
    }
}