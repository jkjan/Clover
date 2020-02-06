package com.jun.clover.login

import android.util.Log
import com.facebook.AccessToken
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider

class MyFacebookCallback(private val activity: LoginActivity) : FacebookCallback<LoginResult> {
    override fun onSuccess(result: LoginResult?) {
        handleFacebookAccessToken(result!!.accessToken)
    }

    override fun onError(error: FacebookException?) {

    }

    override fun onCancel() {

    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        val TAG = "facebook"
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        activity.auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = activity.auth.currentUser
                    Log.d(TAG, user!!.uid) // TODO : 실제 아이디로 쓸 것. conn_app : facebook

                    //loginSucceed(user.uid, "facebook")
                    activity.id = user.uid
                    activity.connApp = "facebook"
                    activity.mLoginViewModel.getUser(activity.id)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    activity.toast("인증 실패")
                }
            }
    }
}