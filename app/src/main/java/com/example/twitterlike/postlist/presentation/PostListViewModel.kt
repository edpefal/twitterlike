package com.example.twitterlike.postlist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.twitterlike.postlist.domain.GetPostListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject


const val PAGE_SIZE = 10

@HiltViewModel
class PostListViewModel @Inject constructor(private val getPostListUseCase: GetPostListUseCase) :
    ViewModel() {

    private val _postListUiSate = MutableLiveData<PostListUiState>()
    val postListUiSate: LiveData<PostListUiState> get() = _postListUiSate

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _posts = MutableLiveData<List<PostModel>>()
    val posts: LiveData<List<PostModel>> = _posts

    private var currentPage = 1

    private val disposable = CompositeDisposable()

    fun fetchPosts() {
        disposable.add(
            getPostListUseCase.invoke(currentPage, PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _postListUiSate.value = PostListUiState.Loading }
                .subscribe(
                    { newPosts ->
                        val currentPosts = _posts.value.orEmpty()
                        _posts.value = currentPosts + newPosts

                        _postListUiSate.value = PostListUiState.Success
                        currentPage++
                    },
                    { throwable -> _error.value = throwable.message }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}