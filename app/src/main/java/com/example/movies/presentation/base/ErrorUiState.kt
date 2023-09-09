package com.example.movies.presentation.base

import android.content.Context
import androidx.annotation.StringRes
import com.example.movies.R

data class ErrorUiState(
    val errorMessage: String? = null, @StringRes val errorResId: Int = R.string.error_common_message
) {
    fun getMessage(context: Context): String {
        return errorMessage ?: context.getString(errorResId)
    }
}