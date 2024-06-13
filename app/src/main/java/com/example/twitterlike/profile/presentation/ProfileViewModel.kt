package com.example.twitterlike.profile.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.twitterlike.postlist.presentation.UserModel
import com.example.twitterlike.profile.domain.GetPostsByUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val getPostsByUserIdUseCase: GetPostsByUserIdUseCase) :
    ViewModel() {
    private val disposable = CompositeDisposable()

    private val _profileUiSate = MutableLiveData<ProfileUiState>()
    val profileUiSate: LiveData<ProfileUiState> get() = _profileUiSate


    fun getPostsByUserId(userId: String, userModel: UserModel) {
        disposable.add(
            getPostsByUserIdUseCase.invoke(userId, userModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _profileUiSate.value = ProfileUiState.Loading }
                .subscribe(
                    {
                        _profileUiSate.value = ProfileUiState.Success(it)
                    }, {
                        _profileUiSate.value = ProfileUiState.Error
                    }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}