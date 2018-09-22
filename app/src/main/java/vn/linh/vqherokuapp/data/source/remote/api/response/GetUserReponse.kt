package vn.linh.vqherokuapp.data.source.remote.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import vn.linh.vqherokuapp.data.model.User

class GetUserResponse(

    @SerializedName("has_more")
    @Expose
    val hasMore:Boolean,

    @SerializedName("users")
    @Expose
    val users:List<User>
)