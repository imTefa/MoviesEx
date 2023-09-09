package com.example.movies.presentation.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.movies.R

@BindingAdapter("url")
fun loadImageUrl(image: ImageView, url: String?) {
    url?.let {
        Glide.with(image.context).load(url).placeholder(R.drawable.movie_placeholder).into(image)
    }
}