package com.paragsharma.myfamous.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Pref File Key
     */
    private const val PREF_FILE_KEY = "preferences"

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob())
    }

    /**
     * Provide authentication shared pref
     *
     * @param appContext
     * @return
     */
    @Provides
    @Singleton
    fun provideAuthenticationSharedPref(@ApplicationContext appContext: Context): SharedPreferences {
        return appContext.getSharedPreferences(PREF_FILE_KEY, Context.MODE_PRIVATE)
    }

    /**
     * Provide context
     *
     * @param application
     * @return
     */
    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext


}

/**
 * Application scope
 *
 * @constructor Create empty Application scope
 */
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope