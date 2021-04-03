package com.hanmajid.android.xeed.util

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class Resource<out T>(
    val success: ResourceStatus,
    val payload: T?,
    val error: String?
) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(ResourceStatus.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(ResourceStatus.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(ResourceStatus.LOADING, data, null)
        }
    }
}