package vn.linh.vqherokuapp.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import vn.linh.vqherokuapp.feature.home.HomeActivity
import vn.linh.vqherokuapp.di.scope.ActivityScope

@Module
abstract class ActivityBuilderModule{

    @ContributesAndroidInjector(modules = [])
    @ActivityScope
    abstract fun bindHomeActivity() : HomeActivity
}