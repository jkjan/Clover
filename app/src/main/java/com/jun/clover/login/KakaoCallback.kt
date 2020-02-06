package com.jun.clover.login

import android.util.Log
import com.kakao.auth.ISessionCallback
import com.kakao.util.exception.KakaoException
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response

class KakaoCallback(private val activity: LoginActivity) : ISessionCallback {
    override fun onSessionOpened() {
        requestMe()
    }

    override fun onSessionOpenFailed(exception: KakaoException?) {
        Log.e("SessionCallback :: ", "onSessionOpenFailed : " + exception!!.message);
    }

    private fun requestMe() {
        // 사용자정보 요청 결과에 대한 Callback
        UserManagement.getInstance().me(object : MeV2ResponseCallback() {

            // 세션 오픈 실패. 세션이 삭제된 경우,

            override fun onSessionClosed(errorResult: ErrorResult) {
                Log.e("SessionCallback :: ", "onSessionClosed : " + errorResult.errorMessage)
            }

            override fun onSuccess(result: MeV2Response?) {
                Log.d("kakao id", "${result!!.id}")
                activity.loginSucceed(result.id.toString(), "Kakaotalk")
                activity.mLoginViewModel.getUser(activity.id)
//                Log.d("kakao uuid", result.kakaoAccount.email)
            }

            // 사용자 정보 요청 실패

            override fun onFailure(errorResult: ErrorResult?) {
                Log.e("SessionCallback :: ", "onFailure : " + errorResult!!.errorMessage)
            }

        })

    }
}