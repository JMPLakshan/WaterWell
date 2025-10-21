package com.example.waterwell.ui.splash

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.waterwell.MainActivity
import com.example.waterwell.R
import com.example.waterwell.ui.onboarding.OnboardingActivity
import com.example.waterwell.utils.PreferenceManager

class SplashActivity : AppCompatActivity() {
    
    private val splashDuration = 3000L // 3 seconds
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        
        // Hide action bar
        supportActionBar?.hide()
        
        // Start animations
        startAnimations()
        
        // Check if user has completed onboarding
        val preferenceManager = PreferenceManager(this)
        val isFirstLaunch = preferenceManager.isFirstLaunch()
        
        // Delay navigation
        Handler(Looper.getMainLooper()).postDelayed({
            if (isFirstLaunch) {
                // First time - show onboarding
                startActivity(Intent(this, OnboardingActivity::class.java))
            } else {
                // Returning user - go to main app
                startActivity(Intent(this, MainActivity::class.java))
            }
            finish()
        }, splashDuration)
    }
    
    private fun startAnimations() {
        val logoContainer = findViewById<View>(R.id.logoContainer)
        val loadingProgress = findViewById<View>(R.id.loadingProgress)
        val versionText = findViewById<View>(R.id.versionText)
        
        // Logo container animation
        val logoFadeIn = AnimationUtils.loadAnimation(this, R.anim.splash_fade_in)
        logoContainer.startAnimation(logoFadeIn)
        
        // Logo scale animation
        val logoScale = AnimationUtils.loadAnimation(this, R.anim.splash_scale)
        logoContainer.startAnimation(logoScale)
        
        // Loading progress animation (delayed)
        Handler(Looper.getMainLooper()).postDelayed({
            loadingProgress.alpha = 0f
            loadingProgress.animate()
                .alpha(1f)
                .setDuration(500)
                .start()
        }, 1000)
        
        // Version text animation (delayed)
        Handler(Looper.getMainLooper()).postDelayed({
            versionText.alpha = 0f
            versionText.animate()
                .alpha(0.6f)
                .setDuration(500)
                .start()
        }, 1500)
    }
}
