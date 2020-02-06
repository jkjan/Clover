package com.jun.clover.login

import com.jun.clover.dependency.MyApplication
import com.kakao.auth.*

class KakaoSDKAdapter(private val context: MyApplication) : KakaoAdapter() {
    override fun getSessionConfig(): ISessionConfig {
        return (object : ISessionConfig {
            override fun getAuthTypes(): Array<AuthType> {
                return arrayOf(AuthType.KAKAO_ACCOUNT)
            }

            override fun isSaveFormData(): Boolean {
                return true
            }

            override fun getApprovalType(): ApprovalType? {
                return ApprovalType.INDIVIDUAL
            }

            override fun isSecureMode(): Boolean {
                return false
            }

            override fun isUsingWebviewTimer(): Boolean {
                return false
            }
        })
    }

    override fun getApplicationConfig(): IApplicationConfig {
        return (IApplicationConfig { context.getGlobalApplicationContext() })
    }
}