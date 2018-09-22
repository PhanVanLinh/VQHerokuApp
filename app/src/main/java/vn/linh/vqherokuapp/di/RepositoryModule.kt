package vn.linh.vqherokuapp.di

import dagger.Module
import dagger.Provides
import vn.linh.vqherokuapp.data.source.UserRepository
import vn.linh.vqherokuapp.data.source.remote.UserRemoteDataSource
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideUserRepository(remoteDataSource: UserRemoteDataSource): UserRepository {
        return UserRepository(remoteDataSource)
    }
}