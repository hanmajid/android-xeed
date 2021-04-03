package com.hanmajid.android.xeed.di

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {

    @Suppress("unused")
    private const val TAG = "SharedPreferencesModule"
    private const val SP_NAME = "com.hanmajid.android.xeed.DEFAULT_SP"

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context,
        masterKey: MasterKey,
    ): SharedPreferences {
        return if (Build.VERSION.SDK_INT >= 21) {
            EncryptedSharedPreferences.create(
                context,
                SP_NAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } else {
            context.getSharedPreferences(
                SP_NAME,
                Context.MODE_PRIVATE,
            )
        }
    }
}