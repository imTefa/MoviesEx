package com.example.movies.data.models.responses

import com.google.gson.annotations.SerializedName

abstract class AppBaseResponse {

    @SerializedName("status_message")
    var statusMessage: String? = ""
}