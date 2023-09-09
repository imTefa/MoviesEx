package com.example.movies.presentation.base

import android.content.Context
import androidx.annotation.StringRes
import com.example.movies.R

open class BaseUiState(
    open val isLoading: Boolean = false,
    open val isError: Boolean = false,
    open val errorMessage: String? = null,
    @StringRes open val errorResId: Int = R.string.error_common_message
)

