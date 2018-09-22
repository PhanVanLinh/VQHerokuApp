package vn.linh.vqherokuapp.feature.user

import dagger.Module
import dagger.Provides
import vn.linh.vqherokuapp.di.scope.ActivityScope
import vn.linh.vqherokuapp.interactor.GetUserDataSource

@Module
class UserModule {
    @Provides
    @ActivityScope
    internal fun provideViewModel(getUserSourceFactory: GetUserDataSource.Factory): UserViewModel {
        return UserViewModel(getUserSourceFactory)
    }
}
