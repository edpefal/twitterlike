package com.example.twitterlike.postlist.data

import com.example.twitterlike.postlist.presentation.PostModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class PostListRepository @Inject constructor(
    private val postListService: PostListService
) {
    fun getUsersAndPosts(): Single<Pair<List<User>, List<Post>>> {
        val userObservable = postListService.getUserList()
        val postObservable = postListService.getPostList()
        return Single.zip(userObservable, postObservable) { users, posts ->
            Pair(users, posts)
        }
    }
}