package vn.linh.vqherokuapp.feature.model

enum class Status {
    LOADING,
    SUCCESS,
    ERROR
}

open class NetworkState constructor(
    val status: Status)

data class LoadingState(val message: String? = null) : NetworkState(Status.LOADING)

data class SuccessState(val message: String? = null) : NetworkState(Status.SUCCESS)

data class ErrorState(val message: String? = null) : NetworkState(Status.ERROR)
