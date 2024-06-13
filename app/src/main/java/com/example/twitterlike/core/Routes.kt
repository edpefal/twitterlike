package com.example.twitterlike.core

sealed class Routes(val route: String) {
    data object PostListScreen : Routes("post_list_screen")
    data object PostDetailScreen : Routes("post_detail_screen/{postId}/{userName}/{userId}/{postContent}") {
        fun createRoute(postId: String, userName: String, userId: String, postContent: String) =
            "post_detail_screen/$postId/$userName/$userId/$postContent"
    }
    data object ProfileScreen : Routes("profile_screen/{userName}/{userId}") {
        fun createRoute(userName: String, userId: String) = "profile_screen/$userName/$userId"

    }


}