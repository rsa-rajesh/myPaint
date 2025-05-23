package com.bts.mypaint.core

import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.transition.Transition
import android.transition.TransitionValues
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.bts.mypaint.R


@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class FadeTransition : Transition {

    private var startAlpha: Float = 0.0f
    private var endAlpha: Float = 1.0f

    constructor(startAlpha: Float, endAlpha: Float) {
        this.startAlpha = startAlpha
        this.endAlpha = endAlpha
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.FadeTransition)
        startAlpha = a.getFloat(R.styleable.FadeTransition_startAlpha, startAlpha)
        endAlpha = a.getFloat(R.styleable.FadeTransition_endAlpha, endAlpha)
        a.recycle()
    }

    private fun captureValues(transitionValues: TransitionValues) {
        transitionValues.values[PROP_NAME_ALPHA] = transitionValues.view.alpha
    }

    override fun captureStartValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

//    override fun createAnimator(sceneRoot: ViewGroup, startValues: TransitionValues, endValues: TransitionValues): Animator {
//        val view = endValues.view
//        if (startAlpha != endAlpha) view.alpha = endAlpha
//        return ObjectAnimator.ofFloat(view, View.ALPHA, startAlpha, endAlpha)
//    }

    companion object {
        private const val PROP_NAME_ALPHA = "android:custom:alpha"
    }
}