package com.example.movies.data.utils.resource

import com.example.movies.R

data class ErrorMessage(
    val message: String? = null,
    val errorResId: Int = R.string.error_common_message
)
