package com.hanmajid.android.xeed.di

import com.hanmajid.android.xeed.repository.AuthRepository
import com.hanmajid.android.xeed.repository.FakeAuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthRepositoryFAKE

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    @AuthRepositoryFAKE
    abstract fun bindFakeAuthRepository(repository: FakeAuthRepository): AuthRepository
}