package com.example.twitterlike.postlist.data

import com.google.gson.annotations.SerializedName

data class Post (
    @SerializedName("userId") val userID: Long? = null,
    @SerializedName("id") val id: Long? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("body") val body: String? = null
)

