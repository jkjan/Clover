package com.jun.clover.login

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.facebook.*
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.jun.clover.R
import com.jun.clover.BaseActivity
import com.jun.clover.databinding.ActivityLoginBinding
import com.jun.clover.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.Exception
import java.security.MessageDigest

class LoginActivity : BaseActivity() {
    private val mLoginViewModel: LoginViewModel by viewModel()
    //    lateinit var auth : FirebaseAuth
//    lateinit var authListener : FirebaseAuth.AuthStateListener
    lateinit var googleSigneInClient: GoogleSignInClient
    lateinit var callbackManager: CallbackManager
    lateinit var auth: FirebaseAuth

    lateinit var id : String
    lateinit var connApp : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLoginViewModel.init()

        val pref = getSharedPreferences("autologin",MODE_PRIVATE)
        val savedId = pref.getString("savedId", null)

        if (savedId != null) {
            mLoginViewModel.getUser(savedId)
        }

        mLoginViewModel.user.observe(this, Observer {
            if (it.id != "null") {
                toast("로그인 성공")

                val pref = getSharedPreferences("autologin",MODE_PRIVATE)
                val savedId = pref.edit()
                savedId.putString("savedId", it.id)
                savedId.apply()

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("user", it)
                startActivity(intent)
                finish()

            } else {
                toast("회원 가입")
                mLoginViewModel.registerInit()
                mLoginViewModel.id.value = id
                mLoginViewModel.connApp.value = connApp

                ll_login.isVisible = false
                ll_register.isVisible = true
            }
        })

        val binding = DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
        binding.lifecycleOwner = this
        binding.loginVM = mLoginViewModel

        //getHashKey(this) facebook 연동시 필요한 hashkey

        FacebookSdk.sdkInitialize(getApplicationContext())
//        AppEventsLogger.activateApp(this)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSigneInClient = GoogleSignIn.getClient(this, gso)

        bt_google_login.setOnClickListener {
            val signInIntent = googleSigneInClient.signInIntent
            startActivityForResult(signInIntent, 100)
        }

        callbackManager = CallbackManager.Factory.create()

        auth = FirebaseAuth.getInstance()

        bt_facebook_login.setPermissions("email")

        bt_facebook_login.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                handleFacebookAccessToken(result!!.accessToken)
            }

            override fun onError(error: FacebookException?) {

            }

            override fun onCancel() {

            }
        })

        mLoginViewModel.register.observe(this, Observer {
            when (it) {
                1 -> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("prcrsId", mLoginViewModel.id.value)
                    startActivity(intent)
                    finish()
                }

                -1 -> {
                    toast("양식에 맞게 입력해주세요.")
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)

            if (result.isSuccess) {
                //구글 로그인이 성공했을 경우
                val account = result.signInAccount
                if (account != null) {
                    Log.d("google prcrsId", account.id!!) // TODO : 실제 id로 쓸 값 : conn_app google
                    Log.d("google email", account.email!!)
                    Log.d("google token", account.idToken!!)

                    //loginSucceed(account.prcrsId!!, "google")
                    this.id = account.id!!
                    this.connApp = "google"
                    mLoginViewModel.getUser(this.id)
                }

                val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                FirebaseAuth.getInstance().signInWithCredential(credential)
            }
        }

        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    fun getHashKey(context: Context): String? {
        var keyHash: String? = null

        try {
            val packageInfo = context.packageManager.getPackageInfo(
                context.packageName,
                PackageManager.GET_SIGNATURES
            )
            for (s in packageInfo.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(s.toByteArray())
                keyHash = Base64.encode(md.digest(), 0).toString()
                Log.d("keyHash", keyHash)
            }
        } catch (e: Exception) {
            Log.e("name not found", e.toString())
        }

        return keyHash
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        val TAG = "facebook"
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    Log.d(TAG, user!!.uid) // TODO : 실제 아이디로 쓸 것. conn_app : facebook

                    //loginSucceed(user.uid, "facebook")
                    this.id = user.uid
                    this.connApp = "facebook"
                    mLoginViewModel.getUser(this.id)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    toast("인증 실패")
                }
            }
    }

    private fun loginSucceed(id : String, connApp : String) {
        mLoginViewModel.getUser(id)
    }
}
