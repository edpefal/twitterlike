package com.example.twitterlike.core

import com.example.twitterlike.postdetail.data.CommentsClient
import com.example.twitterlike.postlist.data.PostListClient
import com.example.twitterlike.profile.data.ProfileClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitHelper(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providePostListClient(retrofit: Retrofit): PostListClient {
        return retrofit.create(PostListClient::class.java)
    }

    @Singleton
    @Provides
    fun provideCommentsClient(retrofit: Retrofit): CommentsClient {
        return retrofit.create(CommentsClient::class.java)
    }

    @Singleton
    @Provides
    fun provideProfileClient(retrofit: Retrofit): ProfileClient {
        return retrofit.create(ProfileClient::class.java)
    }
}