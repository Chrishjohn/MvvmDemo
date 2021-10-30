package com.example.mvvmdemo

import android.app.Application
import com.example.mvvmdemo.ui.factory.UserViewmodelFactory
import com.example.mvvmdemo.data.api.RetrofitClient
import com.example.mvvmdemo.data.repository.AppRepository
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import java.util.Collections.singleton

class Application : Application(), KodeinAware {
    override fun onCreate() {
        super.onCreate()
        application = this
        }

    companion object {
        @JvmStatic
        var application: Application? = null
            private set
    }
    //kodein injection provide by kotlin
    override val kodein = Kodein.lazy {
        import(androidXModule(this@Application))
        bind() from singleton { RetrofitClient() }
        bind() from provider {
            UserViewmodelFactory(
                instance(), appRepository = AppRepository()
            )
        }

    }
    }