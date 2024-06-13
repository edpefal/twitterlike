package com.example.twitterlike.postdetail.data

import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class CommentsRepository @Inject constructor(
    private val commentsService: CommentsService
) {
    fun getCommentList(postId: String): Single<List<Comment>> {
        return commentsService.getCommentList(postId)
    }
}