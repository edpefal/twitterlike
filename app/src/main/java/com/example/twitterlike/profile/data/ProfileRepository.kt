package com.example.twitterlike.profile.data

import com.example.twitterlike.postlist.data.Post
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val profileService: ProfileService) {

    fun getPostsByUserId(userId: String): Single<List<Post>> {
        return profileService.getPostsByUserId(userId)
    }
}