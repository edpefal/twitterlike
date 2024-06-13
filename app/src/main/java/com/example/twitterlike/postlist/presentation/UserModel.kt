package com.example.twitterlike.postlist.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class UserModel (
    val id: String,
    val userName: String,
): Parcelable

