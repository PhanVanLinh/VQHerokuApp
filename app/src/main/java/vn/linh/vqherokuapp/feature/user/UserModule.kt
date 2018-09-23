package vn.linh.vqherokuapp.feature.user

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import vn.linh.vqherokuapp.data.source.UserRepository
import vn.linh.vqherokuapp.di.scope.ActivityScope

@Module
class UserModule {
    @Provides
    @ActivityScope
    internal fun provideViewModel(userRepository: UserRepository,
        compositeDisposable: CompositeDisposable): UserViewModel {
        return UserViewModel(userRepository, compositeDisposable)
    }

    @Provides
    @ActivityScope
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }
}
