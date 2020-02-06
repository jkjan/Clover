package com.jun.clover.dependency

import android.app.Application
import com.jun.clover.login.KakaoSDKAdapter
import com.kakao.auth.KakaoSDK
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class MyApplication : Application() {
    private var instance : MyApplication? = null

    override fun onCreate() {
        super.onCreate()
        this.instance = this
        startKoin {
            androidContext(this@MyApplication)
            modules(appModule)
        }
        KakaoSDK.init(KakaoSDKAdapter(this))
    }

    fun getGlobalApplicationContext() : MyApplication {
        checkNotNull(instance) { "This Application does not inherit com.kakao.GlobalApplication" }
        return instance!!
    }
}