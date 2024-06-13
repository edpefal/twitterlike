package com.example.twitterlike.postlist.data

import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PostListService @Inject constructor(private val postListClient: PostListClient) {

    fun getPostList(): Single<List<Post>> {
        return (postListClient.getPosts())
    }

    fun getUserList(): Single<List<User>> {
        return (postListClient.getUsers())
    }

}