package vn.linh.vqherokuapp.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import vn.linh.vqherokuapp.feature.user.UserActivity
import vn.linh.vqherokuapp.di.scope.ActivityScope
import vn.linh.vqherokuapp.feature.user.UserModule

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [UserModule::class])
    @ActivityScope
    abstract fun bindHomeActivity(): UserActivity
}