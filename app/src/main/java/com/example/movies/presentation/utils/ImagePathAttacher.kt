package com.example.movies.presentation.utils

import android.content.Context
import com.example.movies.R

object ImagePathAttacher {

    fun attachImageBaseUrl(context: Context, imagePath: String): String {
        return context.getString(R.string.image_url) + imagePath
    }

}