package com.example.movies.data.utils.resource

import androidx.annotation.NonNull
import com.example.movies.R

data class NetworkResource<out T>(
    var status: Status,
    val data: T? = null,
    var errorMessage: String? = null,
) {

    suspend fun <R> map(map: suspend (T) -> R): NetworkResource<R> {
        return if (status == Status.SUCCESS && data != null) success(map(data))
        else NetworkResource(
            status = status,
            errorMessage = errorMessage,
            data = null
        )
    }

    companion object {
        fun <T> success(data: T?): NetworkResource<T> {
            return NetworkResource(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(
            errorMessage: String?
        ): NetworkResource<T> {
            return NetworkResource(
                status = Status.ERROR,
                data = null,
                errorMessage = errorMessage,
            )
        }


        fun <T> loading(): NetworkResource<T> {
            return NetworkResource(
                Status.LOADING,
                null,
                null
            )
        }

    }
}