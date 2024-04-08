package com.parag.indianicweather.di



//import com.advantal.projecta.utils.BASE_URL
//import com.paragsharma.indianicweather.utils.BASE_URL
import com.parag.indianicweather.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    /**
     * Provide logging interceptor
     *
     * @return
     */
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor{
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    /**
     * Provide ok http client
     *
     * @param logging
     * @return
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(logging: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logging).connectTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES) // write timeout
            .readTimeout(5, TimeUnit.MINUTES)
            .addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                    try {
                        return chain.proceed(chain.request())
                    } catch (e: Exception) {
                        // Handle network-related exceptions, including timeouts
                        e.printStackTrace() // Print the exception, replace with your custom error handling
                        throw e // Rethrow the exception if needed
                    }
                }

            })
            .build()
    }

    /**
     * Provide retrofit
     *
     * @param client
     * @return
     */
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    /**
     * Provide news api
     *
     * @param retrofit
     * @return
     */
    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}