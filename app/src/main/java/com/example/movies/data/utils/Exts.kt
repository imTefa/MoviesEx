package com.example.movies.data.utils

import com.example.movies.data.utils.resource.NetworkResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart

fun <T> Flow<NetworkResource<T>>.startWithLoadingResult(): Flow<NetworkResource<T>> {
    return this.onStart {
        emit(NetworkResource.loading())
    }
}
