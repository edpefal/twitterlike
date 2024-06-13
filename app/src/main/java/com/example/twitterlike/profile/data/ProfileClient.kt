package com.example.twitterlike.profile.data

import com.example.twitterlike.postlist.data.Post
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ProfileClient {
    @GET("posts")
    fun getPostsByUser(@Query("userId") userId: String): Single<List<Post>>
}