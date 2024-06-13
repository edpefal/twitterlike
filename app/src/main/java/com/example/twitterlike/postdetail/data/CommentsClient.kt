package com.example.twitterlike.postdetail.data

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CommentsClient {

    @GET("comments")
    fun getCommentsByPostId(@Query("postId") postId: String): Single<List<Comment>>

}
