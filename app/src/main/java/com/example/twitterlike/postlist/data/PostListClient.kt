package com.example.twitterlike.postlist.data

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface PostListClient {

    @GET("posts")
    fun getPosts(): Single<List<Post>>

    @GET("users")
    fun getUsers(): Single<List<User>>
}
