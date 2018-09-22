package vn.linh.vqherokuapp.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class, RepositoryModule::class])
abstract class AppModule {

    @Binds
    abstract fun bindContext(application: Application): Context
}