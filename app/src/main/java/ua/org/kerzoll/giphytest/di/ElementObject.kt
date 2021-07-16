package ua.org.kerzoll.giphytest.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import ua.org.kerzoll.giphytest.data.remote.elements.ElementRemoteSource
import ua.org.kerzoll.giphytest.data.remote.elements.ElementService
import ua.org.kerzoll.giphytest.data.repository.ElementRepository
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object ElementObject {

    @Singleton
    @Provides
    fun provideElementService(retrofit: Retrofit): ElementService = retrofit.create(ElementService::class.java)

    @Singleton
    @Provides
    fun provideElementRepository(
        remoteDataSource: ElementRemoteSource
    ) = ElementRepository(remoteDataSource)
}