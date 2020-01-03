package com.githubsearch.utility

import android.animation.Animator
import android.graphics.Rect
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator

class BaseAnimation {
  companion object {

    fun getFadeInAnimation(): Animation {
      val fadeIn = AlphaAnimation(0f, 1f)
      fadeIn.interpolator = DecelerateInterpolator() //add this
      fadeIn.duration = 600

      val animation = AnimationSet(false) //change to false
      animation.addAnimation(fadeIn)
      return animation
    }

    fun getFadeOutAnimation(view : View): Animation {
      val fadeOut = AlphaAnimation(1f, 0f)
      fadeOut.interpolator = AccelerateInterpolator()
      fadeOut.duration = 600
      val animation = AnimationSet(false)
      animation.addAnimation(fadeOut)
      animation.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(p0: Animation?) {

        }

        override fun onAnimationEnd(p0: Animation?) {
          view.visibility = View.GONE
        }

        override fun onAnimationStart(p0: Animation?) {
        }
      })
      return animation
    }

    fun revealAnimationVisible(parameterView: View, currentView: View, duration: Long,
        animatorListener: Animator.AnimatorListener) {
      if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
        val bounds = Rect()
        parameterView.getDrawingRect(bounds)
        val centerX = bounds.centerX()
        val centerY = bounds.centerY()
        val startRadius = 0
        val endRadius = Math.hypot(parameterView.width.toDouble(),
            parameterView.height.toDouble()).toInt()

        var anim: Animator? = null
        anim = ViewAnimationUtils.createCircularReveal(currentView, centerX, centerY,
            startRadius.toFloat(),
            endRadius.toFloat())
        anim!!.duration = duration

        currentView.visibility = View.VISIBLE
        anim.addListener(animatorListener)
        anim.start()
      } else {
        currentView.visibility = View.VISIBLE
      }
    }

    fun revealAnimationGone(parameterView: View, currentView: View, duration: Long,
        animatorListener: Animator.AnimatorListener) {
      if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
        val bounds = Rect()
        parameterView.getDrawingRect(bounds)
        val centerX = bounds.centerX()
        val centerY = bounds.centerY()
        val startRadius = Math.hypot(parameterView.width.toDouble(),
            parameterView.height.toDouble()).toInt()
        val endRadius = 0

        var anim: Animator? = null
        anim = ViewAnimationUtils.createCircularReveal(currentView, centerX, centerY,
            startRadius.toFloat(),
            endRadius.toFloat())
        anim!!.duration = duration
        anim.addListener(animatorListener)
        anim.start()
      } else {
        currentView.visibility = View.GONE
      }
    }
  }
}