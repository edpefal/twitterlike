package com.example.twitterlike.postlist.domain

import com.example.twitterlike.postlist.data.Post
import com.example.twitterlike.postlist.data.PostListRepository
import com.example.twitterlike.postlist.data.User
import com.example.twitterlike.postlist.presentation.PostModel
import com.example.twitterlike.postlist.presentation.UserModel
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GetPostListUseCase @Inject constructor(private val postListRepository: PostListRepository) {

    private var cachedPosts: List<PostModel> = emptyList()
    private var totalFetched: Int = 0

    operator fun invoke(page: Int, pageSize: Int): Observable<List<PostModel>> {

        return if(totalFetched == 0){
            postListRepository.getUsersAndPosts().toObservable().doOnNext {
                createPostModelList(it)
            }.flatMap {
                getPostsFromCache(page, pageSize)
            }
        } else {
            getPostsFromCache(page, pageSize)
        }
    }

    private fun getPostsFromCache(page: Int, pageSize: Int): Observable<List<PostModel>> {
        val fromIndex = (page - 1) * pageSize
        val toIndex = minOf(fromIndex + pageSize, totalFetched)
        return if (fromIndex < totalFetched) {
            Observable.just(cachedPosts.subList(fromIndex, toIndex))
        } else {
            Observable.just(emptyList())
        }
    }

    private fun createPostModelList(list: Pair<List<User>, List<Post>>){
        val postModelList = list.second.map { post ->
            val user = list.first.firstOrNull { user ->
                user.id == post.userID
            }.toUserModelOrEmpty()
            PostModel(
                post.body.orEmpty(),
                user,
                post.id.toString()
            )
        }.toMutableList()
        if (cachedPosts.isEmpty()) {
            cachedPosts = postModelList.toList()
            totalFetched = cachedPosts.size
        }
    }

    private fun User?.toUserModelOrEmpty(): UserModel {
        this ?: return UserModel("","")
        return UserModel(
            id = id.orZero().toString(),
            userName = userName.orEmpty(),
        )
    }


    private fun Long?.orZero(): Long = this ?: 0

}