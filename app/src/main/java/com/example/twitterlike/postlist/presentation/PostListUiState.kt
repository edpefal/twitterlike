package com.example.twitterlike.postlist.presentation

sealed class PostListUiState{
    data object Empty: PostListUiState()
    data object Loading: PostListUiState()
    data object Success: PostListUiState()
    data object Error: PostListUiState()

}