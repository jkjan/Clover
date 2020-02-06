package com.jun.clover.lockscreen

import android.content.Context
import android.widget.RelativeLayout
import android.util.AttributeSet
import android.graphics.drawable.Drawable
import android.widget.TextView
import android.widget.ImageView
import androidx.core.content.ContextCompat
import android.view.ViewGroup
import android.view.MotionEvent
import android.view.View
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.ContextWrapper
import android.graphics.Color
import android.util.Log
import android.view.animation.AccelerateDecelerateInterpolator
import com.jun.clover.R
import androidx.core.content.ContextCompat.getSystemService
import android.view.LayoutInflater
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import com.jun.clover.databinding.ActivityLockScreenBinding

class SwipeButton(context: Context) : RelativeLayout(context) {
    lateinit var mLockScreenViewModel : LockScreenViewModel
    private var slidingButton: ImageView? = null
    private var initialX: Float = 0f
    private var active: Boolean = false
    private var initialButtonWidth: Int = 0
    private var centerText: TextView? = null
    lateinit var binding : ActivityLockScreenBinding
    private var disabledDrawable: Drawable? = null
    private var enabledDrawable: Drawable? = null



    constructor(context: Context, attrs : AttributeSet) : this(context) {
        init(context, null, -1, -1)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : this(context, attrs) {
        init(context, attrs, defStyleAttr, -1)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : this(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr, defStyleRes)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        val background = RelativeLayout(context)
//
//        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        binding = ActivityLockScreenBinding.inflate(inflater)

        //binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.activity_lock_screen, this, false)
        //binding = ActivityLockScreenBinding.inflate(LayoutInflater.from(context), this, true)
        //binding.lifecycleOwner = getLifeCycleOwner(this)
//        getLifeCycleOwner(this)?.let{
//            binding.lifecycleOwner = it
//        }

        val layoutParamsView = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        layoutParamsView.addRule(CENTER_IN_PARENT, TRUE)

        background.background = ContextCompat.getDrawable(context, R.drawable.shape_round)

        addView(background, layoutParamsView)

        val centerText = TextView(context)
        this.centerText = centerText

        val layoutParams = LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        layoutParams.addRule(CENTER_IN_PARENT, TRUE)
        centerText.text = "SWIPE" //add any text you need
        centerText.setTextColor(Color.WHITE)
        centerText.setPadding(35, 35, 35, 35)
        background.addView(centerText, layoutParams)

        val swipeButton = ImageView(context)

        this.slidingButton = swipeButton

        this.initialX = ((width - slidingButton!!.width)/2).toFloat()

        disabledDrawable =
            ContextCompat.getDrawable(getContext(), R.drawable.ic_lock_black_24dp)
        enabledDrawable =
            ContextCompat.getDrawable(getContext(), R.drawable.ic_lock_open_black_24dp)

        slidingButton!!.setImageDrawable(disabledDrawable)
        slidingButton!!.setPadding(40, 40, 40, 40)

        val layoutParamsButton = LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        layoutParamsButton.addRule(CENTER_IN_PARENT, TRUE)
        layoutParamsButton.addRule(CENTER_VERTICAL, TRUE)
        swipeButton.background = ContextCompat.getDrawable(context, R.drawable.shape_button)
        swipeButton.setImageDrawable(disabledDrawable)
        addView(swipeButton, layoutParamsButton)

        setOnTouchListener(getButtonTouchListener())
    }

    private fun getButtonTouchListener(): OnTouchListener {
        return object : OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> return true
                    MotionEvent.ACTION_MOVE -> {
                        //Movement logic here
                        // 가운데 고정
                        if (initialX == (width/2).toFloat()) {
                            initialX = slidingButton!!.x
                        }

                        // 왼쪽->오른쪽 슬라이드
                        if (event.x > initialX + slidingButton!!.width / 2 &&
                            event.x + slidingButton!!.width / 2 < width) {
                            slidingButton!!.x = event.x - slidingButton!!.width / 2
                            centerText!!.alpha = 1 - 1.3f * (slidingButton!!.x + slidingButton!!.width) / width
                        }

                        // 왼쪽<-오른쪽 슬라이드
                        if (event.x < initialX + slidingButton!!.width / 2 &&
                            event.x + slidingButton!!.width / 2 < width) {
                            slidingButton!!.x = event.x - slidingButton!!.width / 2
                            centerText!!.alpha = 1 - 1.3f * (slidingButton!!.x + slidingButton!!.width) / width
                        }

                        // 버튼이 오른쪽으로 넘어가는 것 방지
                        if  (event.x + slidingButton!!.width / 2 > width &&
                            slidingButton!!.x + slidingButton!!.width / 2 < width) {
                            Log.d("slidingButton", "over right")
                            slidingButton!!.x = (width - slidingButton!!.width).toFloat()
                        }

                        // 버튼이 왼쪽으로 넘어가는 것 방지
                        if (event.x + slidingButton!!.width / 2 < slidingButton!!.width &&
                            slidingButton!!.x + slidingButton!!.width / 2 < width) {
                            Log.d("slidingButton", "over left")
                            slidingButton!!.x = 0f
                        }

                        if  (event.x < slidingButton!!.width / 2 &&
                            slidingButton!!.x > width/2) {
                            slidingButton!!.x = (width/2).toFloat()
                        }

                        return true
                    }

                    MotionEvent.ACTION_UP -> {
                        // 손가락 뗐을 때
                        if (active) {
                            collapseButton()
                        } else {
                            initialButtonWidth = slidingButton!!.width

                            if (slidingButton!!.x > (width - slidingButton!!.width)*0.95 && slidingButton!!.x <= width) {
                              //  mLockScreenViewModel!!.unlockScreen()
                            }

                            if (slidingButton!!.x < slidingButton!!.width * 1.05 && slidingButton!!.x >= 0) {
                                //mLockScreenViewModel!!.goToLink()
                            }

                            if (slidingButton!!.x + slidingButton!!.width < width ||
                                    slidingButton!!.x + slidingButton!!.width > slidingButton!!.width) {
                                moveButtonBack()
                            }
                        }

                        return true
                    }
                }

                return false
            }
        }
    }

    private fun expandButton() {
        val positionAnimator = ValueAnimator.ofFloat(slidingButton!!.x, this.initialX)
        positionAnimator.addUpdateListener {
            val x = positionAnimator.animatedValue as Float
            slidingButton!!.x = x
        }


        val widthAnimator = ValueAnimator.ofInt(
            slidingButton!!.width,
            width
        )

        widthAnimator.addUpdateListener {
            val params = slidingButton!!.layoutParams
            params.width = widthAnimator.animatedValue as Int
            slidingButton!!.layoutParams = params
        }


        val animatorSet = AnimatorSet()
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)

                active = true
                slidingButton!!.setImageDrawable(enabledDrawable)
            }
        })

        animatorSet.playTogether(positionAnimator, widthAnimator)
        animatorSet.start()
    }

    private fun collapseButton() {
        val widthAnimator = ValueAnimator.ofInt(
            slidingButton!!.width,
            initialButtonWidth
        )

        widthAnimator.addUpdateListener {
            val params = slidingButton!!.layoutParams
            params.width = widthAnimator.animatedValue as Int
            slidingButton!!.layoutParams = params
        }

        widthAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                active = false
                slidingButton!!.setImageDrawable(disabledDrawable)
            }
        })

        val objectAnimator = ObjectAnimator.ofFloat(
            centerText!!, "alpha", 1f
        )

        val animatorSet = AnimatorSet()

        animatorSet.playTogether(objectAnimator, widthAnimator)
        animatorSet.start()
    }

    private fun moveButtonBack() {
        val positionAnimator = ValueAnimator.ofFloat(slidingButton!!.x, ((width - slidingButton!!.width)/2).toFloat())
        Log.d("slidingButton", "${(width - slidingButton!!.width)/2}")
        positionAnimator.interpolator = AccelerateDecelerateInterpolator()
        positionAnimator.addUpdateListener {
            val x = positionAnimator.animatedValue as Float
            slidingButton!!.x = x
        }

        val objectAnimator = ObjectAnimator.ofFloat(
            centerText!!, "alpha", 1f
        )

        positionAnimator.duration = 200

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(objectAnimator, positionAnimator)
        animatorSet.start()
    }

    fun setViewModel (lsVM : LockScreenViewModel) {
        this.mLockScreenViewModel = lsVM
    }

    fun getLifeCycleOwner(view: View): LifecycleOwner? {
        var context = view.context

        while (context is ContextWrapper) {
            if (context is LifecycleOwner) {
                return context
            }
            context = context.baseContext
        }

        return null
    }
}