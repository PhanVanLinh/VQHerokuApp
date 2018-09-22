package vn.linh.vqherokuapp.data.source

import io.reactivex.Single
import vn.linh.vqherokuapp.data.model.User
import vn.linh.vqherokuapp.data.source.remote.UserRemoteDataSource
import java.util.concurrent.TimeUnit

class UserRepository(private val remoteDataSource: UserRemoteDataSource) {

    fun getUsers(offset: Int, limit: Int): Single<List<User>> {
        // todo remove delay later
        return remoteDataSource.getUsers(offset, limit).delay(500, TimeUnit.MILLISECONDS)
    }
}