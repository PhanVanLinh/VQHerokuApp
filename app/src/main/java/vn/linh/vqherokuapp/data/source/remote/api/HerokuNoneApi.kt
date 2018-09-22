package vn.linh.vqherokuapp.data.source.remote.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import vn.linh.vqherokuapp.data.source.remote.api.response.ApiResponse
import vn.linh.vqherokuapp.data.source.remote.api.response.GetUserResponse

interface HerokuNoneApi {

    @GET("api/users")
    fun getUsers(@Query("offset") offset: Int, @Query(
        "limit") limit: Int): Single<ApiResponse<GetUserResponse>>
}