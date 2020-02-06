package com.jun.clover.frdrcmd

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.jun.clover.BaseActivity
import com.jun.clover.R
import com.jun.clover.databinding.ActivityFrdRecmdBinding
import org.koin.android.viewmodel.ext.android.viewModel

class FrdRecmdActivity : BaseActivity() {

    private val frdRecmdViewModel : FrdRecmdViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val kakaoLink = KakaoLink.getKakaoLink(this)
        val binding = DataBindingUtil.setContentView<ActivityFrdRecmdBinding>(this, R.layout.activity_frd_recmd)

        val id = intent.getStringExtra("id")
        Log.d("user id got", id)

        frdRecmdViewModel.init(id!!)
        binding.frVM = frdRecmdViewModel
        binding.lifecycleOwner = this

//        frdRecmdViewModel.recmdCode.observe(this, Observer {
//            if (it != null) {
//                val kakaoTalkLinkMessageBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder()
//                kakaoTalkLinkMessageBuilder.addText("매일매일 행운을 노려보세요!!! 추천인 코드를 입력하시면 500 포인트를 드려요! 추천인 코드 : $it")
//                kakaoLink.sendMessage(kakaoTalkLinkMessageBuilder, this)
//            }
//        })
    }
}
