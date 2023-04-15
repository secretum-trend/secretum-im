package com.messaging.scrtm.core.utils

data class Resource<out T>(
    val status: Status,
    val data: T?,
    val message: String?,
    val code: Int = -1
) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T, code: Int = -1): Resource<T> {
            return Resource(Status.SUCCESS, data, null, code)
        }

        fun <T> error(message: String, code: Int = -1, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, message, code)
        }

        fun <T> loading(data: T? = null, code: Int = -1): Resource<T> {
            return Resource(Status.LOADING, data, null, code)
        }
    }
}
