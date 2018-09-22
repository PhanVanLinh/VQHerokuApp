package vn.linh.vqherokuapp.data.source.remote.api.response

import com.google.gson.annotations.Expose

class ApiResponse<T> {
    @Expose
    var data: T? = null
    @Expose
    var status: Boolean = false
    @Expose
    var message: String? = null
}