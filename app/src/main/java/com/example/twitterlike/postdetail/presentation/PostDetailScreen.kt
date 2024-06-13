package com.example.twitterlike.postdetail.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.twitterlike.core.Routes
import com.example.twitterlike.core.widgets.Header
import com.example.twitterlike.postlist.presentation.PostItem
import com.example.twitterlike.ui.theme.cardTitleColor
import com.example.twitterlike.ui.theme.subtitleColor
import kotlinx.coroutines.launch

@Composable
fun PostDetailScreen(
    postId: String,
    userName: String,
    userId: String,
    postContent: String,
    postDetailViewModel: PostDetailViewModel,
    navController: NavController
) {
    postDetailViewModel.getCommentsByPost(postId)
    val postUiState: PostDetailUiState by postDetailViewModel.postDetailUiSate.observeAsState(
        initial = PostDetailUiState.Loading
    )
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
        ) {
            Header(title = "Post Detail") {
                coroutineScope.launch {
                    navController.popBackStack()
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {

                PostItem(userName, postContent, onClick = {

                }, onHeaderClick = {
                    navController.navigate(
                        Routes.ProfileScreen.createRoute(
                            userName,
                            userId,
                        )
                    )
                })
                Text(
                    color = subtitleColor,
                    text = "Comments",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                when (postUiState) {
                    PostDetailUiState.Error -> Text(text = "Error")
                    PostDetailUiState.Loading -> Text(text = "Loading")
                    is PostDetailUiState.Success -> {
                        val comments = (postUiState as PostDetailUiState.Success).comments
                        CommentList(comments)
                    }
                }
            }
        }

    }

}

@Composable
fun CommentList(comments: List<CommentUiModel>) {
    Column {
        comments.forEach { comment ->
            CommentItem(comment)
        }
    }
}

@Composable
fun CommentItem(comment: CommentUiModel) {
    Card(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Text(
            text = comment.name,
            Modifier.padding(16.dp, vertical = 8.dp),
            color = cardTitleColor,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(
            text = comment.body,
            Modifier.padding(16.dp, bottom = 8.dp),
            fontSize = 16.sp,
            overflow = TextOverflow.Ellipsis
        )
    }
}
