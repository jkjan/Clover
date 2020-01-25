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
import android.graphics.Color
import android.view.animation.AccelerateDecelerateInterpolator
import com.jun.clover.R

class SwipeButton(context: Context) : RelativeLayout(context) {
    private var slidingButton: ImageView? = null
    private var initialX: Float = 0.toFloat()
    private var active: Boolean = false
    private var initialButtonWidth: Int = 0
    private var centerText: TextView? = null

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

        layoutParamsButton.addRule(ALIGN_PARENT_LEFT, TRUE)
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
                        if (initialX == 0f) {
                            initialX = slidingButton!!.x
                        }
                        if (event.x > initialX + slidingButton!!.width / 2 &&
                            event.x + slidingButton!!.width / 2 < width) {
                            slidingButton!!.x = event.x - slidingButton!!.width / 2
                            centerText!!.alpha = 1 - 1.3f * (slidingButton!!.x + slidingButton!!.width) / width
                        }

                        if  (event.x + slidingButton!!.width / 2 > width &&
                            slidingButton!!.x + slidingButton!!.width / 2 < width) {
                            slidingButton!!.x = (width - slidingButton!!.width).toFloat()
                        }

                        if  (event.x < slidingButton!!.width / 2 &&
                            slidingButton!!.x > 0) {
                            slidingButton!!.x = 0f
                        }
                        return true
                    }
                    MotionEvent.ACTION_UP -> {
                        //Release logic here
                        if (active) {
                            collapseButton()
                        } else {
                            initialButtonWidth = slidingButton!!.width

                            if (slidingButton!!.x + slidingButton!!.width > width * 0.85) {
                                expandButton()
                            } else {
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
        val positionAnimator = ValueAnimator.ofFloat(slidingButton!!.x, 0f)
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
        val positionAnimator = ValueAnimator.ofFloat(slidingButton!!.x, 0F)
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
}