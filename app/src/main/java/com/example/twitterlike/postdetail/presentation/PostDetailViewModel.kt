package com.example.twitterlike.postdetail.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.twitterlike.postdetail.domain.GetCommentsByPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(private val getCommentsUseCase: GetCommentsByPostUseCase) :
    ViewModel() {

    private val _postDetailUiSate = MutableLiveData<PostDetailUiState>()
    val postDetailUiSate: LiveData<PostDetailUiState> get() = _postDetailUiSate


    private val disposable = CompositeDisposable()

    fun getCommentsByPost(postId: String) {
        disposable.add(
            getCommentsUseCase.invoke(postId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe{
                    _postDetailUiSate.postValue(PostDetailUiState.Loading)
                }
                .subscribe({
                    _postDetailUiSate.postValue(PostDetailUiState.Success(it))
                    }, {
                    _postDetailUiSate.postValue(PostDetailUiState.Error)
                    }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}