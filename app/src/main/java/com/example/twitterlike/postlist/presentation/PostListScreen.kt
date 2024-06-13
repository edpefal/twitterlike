package com.example.twitterlike.postlist.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.twitterlike.R
import com.example.twitterlike.core.Routes
import com.example.twitterlike.ui.theme.CustomTextStyles

@Composable
fun PostListScreen(postListViewModel: PostListViewModel, navController: NavHostController) {

    LaunchedEffect(Unit) {
        postListViewModel.fetchPosts()
    }

    val postUiState: PostListUiState by postListViewModel.postListUiSate.observeAsState(initial = PostListUiState.Empty)
    val posts by postListViewModel.posts.observeAsState(emptyList())
    val listState = rememberLazyListState()
    Scaffold {
        Column(Modifier.padding(it)) {
            Text(
                color = com.example.twitterlike.ui.theme.titleColor,
                text = stringResource(id = R.string.home_header),
                modifier = Modifier.padding(dimensionResource(id = R.dimen.default_margin)),
                style = CustomTextStyles.header,
            )
            when (postUiState) {
                PostListUiState.Error -> Text(
                    text = stringResource(id = R.string.error_text),
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(it)
                        .fillMaxSize(), textAlign = TextAlign.Center
                )

                PostListUiState.Loading -> {
                    Text(
                        text = stringResource(id = R.string.loading),
                        Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(it)
                            .fillMaxSize(), textAlign = TextAlign.Center
                    )

                }

                is PostListUiState.Success -> PostContent(
                    navController,
                    postListViewModel,
                    posts,
                    listState,
                )

                PostListUiState.Empty -> Text(
                    text = stringResource(id = R.string.fetching_data),
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(it)
                        .fillMaxSize(), textAlign = TextAlign.Center
                )
            }
        }

    }
}

@Composable
fun PostItem(
    userName: String,
    postContent: String,
    onClick: () -> Unit? = {},
    onHeaderClick: () -> Unit? = {}
) {
    Card(modifier = Modifier
        .clickable {
            onClick()
        }
        .height(dimensionResource(id = R.dimen.post_item_height))
        .fillMaxWidth()
        .padding(
            vertical = dimensionResource(id = R.dimen.default_vertical_padding),
            horizontal = dimensionResource(id = R.dimen.default_margin)
        ),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(id = R.dimen.post_card_elevation))
    ) {
        Row(modifier = Modifier.clickable { onHeaderClick() }) {
            Image(
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = "profile",
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.image_post_size))
                    .padding(start = 8.dp, top = 8.dp)
            )
            Text(
                text = userName,
                style = CustomTextStyles.cardTitle,
                modifier = Modifier.padding(8.dp)
            )
        }
        Text(
            text = postContent,
            Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
            fontSize = 18.sp,
            overflow = TextOverflow.Ellipsis
        )
    }

}

@Composable
fun PostContent(
    navController: NavHostController,
    viewModel: PostListViewModel,
    posts: List<PostModel>,
    listState: LazyListState,
) {

    LazyColumn(state = listState) {
        items(posts) { post ->
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
                        userId = post.user.id,
                        userName = post.user.userName
                    )
                )
            })
        }
    }

    LaunchedEffect(listState.canScrollForward) {
        if (listState.canScrollForward.not() && listState.firstVisibleItemIndex > 1) {
            viewModel.fetchPosts()
        }
    }

}