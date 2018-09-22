package vn.linh.vqherokuapp

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import vn.linh.vqherokuapp.di.DaggerAppComponent

class HerokuApp : DaggerApplication(){
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}