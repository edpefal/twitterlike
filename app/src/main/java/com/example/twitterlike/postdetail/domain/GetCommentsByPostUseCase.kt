package com.example.twitterlike.postdetail.domain

import com.example.twitterlike.postdetail.data.CommentsRepository
import com.example.twitterlike.postdetail.presentation.CommentUiModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetCommentsByPostUseCase @Inject constructor(private val commentsRepository: CommentsRepository) {

    operator fun invoke(postId: String): Single<List<CommentUiModel>> {
        return commentsRepository.getCommentList(postId).map {
            it.map { CommentUiModel(it.name.orEmpty(), it.body.orEmpty()) }
        }
    }
}
