package com.example.twitterlike.profile.presentation

import com.example.twitterlike.postlist.presentation.PostModel

sealed class ProfileUiState{
    data object Loading: ProfileUiState()
    class Success(val posts: List<PostModel>): ProfileUiState()
    data object Error: ProfileUiState()

}