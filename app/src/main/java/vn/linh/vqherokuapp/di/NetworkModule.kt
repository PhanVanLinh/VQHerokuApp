package vn.linh.vqherokuapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import vn.linh.vqherokuapp.BuildConfig
import vn.linh.vqherokuapp.data.source.remote.api.HerokuNoneApi
import vn.linh.vqherokuapp.data.source.remote.api.service.ServiceGenerator
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    @Singleton
    @Provides
    fun provideNoneAuthApi(gson: Gson, loggingInterceptor: HttpLoggingInterceptor): HerokuNoneApi {
        val interceptors = arrayOf<Interceptor>(loggingInterceptor)
        return ServiceGenerator.generate(BuildConfig.BASE_URL, HerokuNoneApi::class.java, gson, null, interceptors)
    }
}