package com.example.twitterlike.postdetail.data

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("postId") val postId: Long? = null,
    @SerializedName("id") val id: Long? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("body") val body: String? = null
)
