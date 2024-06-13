package com.example.twitterlike.postdetail.data

import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class CommentsService @Inject constructor(private val commentsClient: CommentsClient) {

    fun getCommentList(postId: String): Single<List<Comment>> {
        return (commentsClient.getCommentsByPostId(postId))
    }
}