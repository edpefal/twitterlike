package com.example.twitterlike.postdetail.presentation

sealed class PostDetailUiState{
    data object Loading: PostDetailUiState()
    class Success(val comments: List<CommentUiModel>): PostDetailUiState()
    data object Error: PostDetailUiState()

}