package com.example.twitterlike

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.twitterlike.core.Routes
import com.example.twitterlike.postdetail.presentation.PostDetailScreen
import com.example.twitterlike.postdetail.presentation.PostDetailViewModel
import com.example.twitterlike.postlist.presentation.PostListScreen
import com.example.twitterlike.postlist.presentation.PostListViewModel
import com.example.twitterlike.profile.presentation.ProfileScreen
import com.example.twitterlike.profile.presentation.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val postListViewModel: PostListViewModel by viewModels()
    val postDetailViewModel: PostDetailViewModel by viewModels()
    val profileViewModel: ProfileViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Routes.PostListScreen.route) {
                composable(Routes.PostListScreen.route) {
                    PostListScreen(postListViewModel, navController)
                }
                composable(
                    Routes.PostDetailScreen.route,
                    arguments = listOf(
                        navArgument("postId") { type = NavType.StringType },
                        navArgument("userName") { type = NavType.StringType },
                        navArgument("userId") { type = NavType.StringType },
                        navArgument("postContent") { type = NavType.StringType },
                    )
                ) {
                    PostDetailScreen(
                        it.arguments?.getString("postId").orEmpty(),
                        it.arguments?.getString("userName").orEmpty(),
                        it.arguments?.getString("userId").orEmpty(),
                        it.arguments?.getString("postContent").orEmpty(),
                        postDetailViewModel,
                        navController
                    )
                }
                composable(
                    Routes.ProfileScreen.route,
                    arguments = listOf(
                        navArgument("userName") { type = NavType.StringType },
                        navArgument("userId") { type = NavType.StringType },
                    )
                ) {
                    ProfileScreen(
                        profileViewModel,
                        it.arguments?.getString("userId").orEmpty(),
                        it.arguments?.getString("userName").orEmpty(),
                        navController
                    )
                }

            }
        }
    }
}
