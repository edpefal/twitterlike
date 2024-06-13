package com.example.twitterlike.profile.domain

import com.example.twitterlike.postlist.presentation.PostModel
import com.example.twitterlike.postlist.presentation.UserModel
import com.example.twitterlike.profile.data.ProfileRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetPostsByUserIdUseCase @Inject constructor(private val profileRepository: ProfileRepository) {
    operator fun invoke(userId: String, userModel: UserModel): Single<List<PostModel>> {
        return profileRepository.getPostsByUserId(userId).map {
            it.map { post -> PostModel(post.body.orEmpty(), userModel, post.id.toString()) }
        }
    }
}