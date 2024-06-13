package com.example.twitterlike.postlist.data

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("id") val id: Long? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("username") val userName: String? = null,
    @SerializedName("email") val email: String? = null
)

