package com.oguzel.travel_app.di

import com.oguzel.travel_app.data.remote.ApiService
import com.oguzel.travel_app.data.remote.RemoteDataSource
import com.oguzel.travel_app.data.remote.repository.TravelRepositoryImp
import com.oguzel.travel_app.domain.repository.TravelRepository
import com.oguzel.travel_app.utils.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        networkApiService: ApiService,
    ): RemoteDataSource {
        return RemoteDataSource(networkApiService)
    }

    @Provides
    @Singleton
    fun provideTravelRepository(remoteDataSource: RemoteDataSource): TravelRepository {
        return TravelRepositoryImp(remoteDataSource)
    }
}
