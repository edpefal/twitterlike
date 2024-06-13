package com.example.twitterlike.profile.data

import com.example.twitterlike.postlist.data.Post
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ProfileService @Inject constructor(private val  profileClient: ProfileClient) {

    fun getPostsByUserId(userId: String): Single<List<Post>> {
        return profileClient.getPostsByUser(userId)
    }
}