package com.example.twitterlike.profile.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.twitterlike.R
import com.example.twitterlike.core.Routes
import com.example.twitterlike.core.widgets.Header
import com.example.twitterlike.postlist.presentation.PostItem
import com.example.twitterlike.postlist.presentation.UserModel
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    userId: String,
    userName: String,
    navController: NavHostController,

    ) {
    profileViewModel.getPostsByUserId(
        userId,
        UserModel(id = userId, userName = userName)
    )
    val postUiState: ProfileUiState by profileViewModel.profileUiSate.observeAsState(
        initial = ProfileUiState.Loading
    )
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            Header("Profile") {
                coroutineScope.launch {
                    navController.popBackStack()
                }

            }
            Image(
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = "profile",
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                userName,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally)

            )
            Text(
                "My Posts", modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
            )
            when (postUiState) {
                ProfileUiState.Error -> Text(text = "Error")
                ProfileUiState.Loading -> Text(text = "Loading")
                is ProfileUiState.Success -> {
                    Column {
                        (postUiState as ProfileUiState.Success).posts.forEach { post ->
                            PostItem(post.user.userName, post.postContent, onClick = {
                                navController.navigate(
                                    Routes.PostDetailScreen.createRoute(
                                        post.postId,
                                        post.user.userName,
                                        post.user.id,
                                        post.postContent
                                    )
                                )
                            }, onHeaderClick = {
                                navController.navigate(
                                    Routes.ProfileScreen.createRoute(
                                        post.user.id,
                                        post.user.userName
                                    ))
                            })
                        }
                    }
                }
            }

        }
    }
}

