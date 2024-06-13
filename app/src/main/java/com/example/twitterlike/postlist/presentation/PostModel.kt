package com.example.twitterlike.postlist.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class PostModel(
    val postContent: String,
    val user: UserModel,
    val postId: String,
): Parcelable