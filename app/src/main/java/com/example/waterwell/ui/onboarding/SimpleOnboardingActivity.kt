package com.example.waterwell.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.waterwell.MainActivity
import com.example.waterwell.R
import com.example.waterwell.utils.PreferenceManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class SimpleOnboardingActivity : AppCompatActivity() {
    
    private lateinit var illustration: ImageView
    private lateinit var title: TextView
    private lateinit var description: TextView
    private lateinit var btnNext: MaterialButton
    private lateinit var btnSkip: View
    private lateinit var indicators: LinearLayout
    
    private var currentPage = 0
    private val totalPages = 3
    
    private val onboardingData = listOf(
        OnboardingItem(
            title = "Track Your Hydration",
            description = "Log your daily water intake and monitor your hydration progress with beautiful charts and insights.",
            illustration = R.drawable.ic_water
        ),
        OnboardingItem(
            title = "Smart Reminders",
            description = "Get personalized reminders to drink water throughout the day based on your schedule and preferences.",
            illustration = R.drawable.ic_checklist
        ),
        OnboardingItem(
            title = "Build Healthy Habits",
            description = "Create and maintain healthy hydration habits with our intuitive habit tracking system.",
            illustration = R.drawable.ic_chart
        )
    )
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_simple)
        
        // Debug logging
        android.util.Log.d("SimpleOnboardingActivity", "SimpleOnboardingActivity created")
        
        // Hide action bar
        supportActionBar?.hide()
        
        initViews()
        setupIndicators()
        setupButtons()
        showPage(0)
    }
    
    private fun initViews() {
        illustration = findViewById(R.id.illustration)
        title = findViewById(R.id.title)
        description = findViewById(R.id.description)
        btnNext = findViewById(R.id.btnNext)
        btnSkip = findViewById(R.id.btnSkip)
        indicators = findViewById(R.id.indicators)
    }
    
    private fun setupIndicators() {
        for (i in 0 until totalPages) {
            val indicator = MaterialCardView(this).apply {
                layoutParams = LinearLayout.LayoutParams(16, 16).apply {
                    setMargins(8, 0, 8, 0)
                }
                radius = 8f
                setCardBackgroundColor(ContextCompat.getColor(this@SimpleOnboardingActivity, R.color.indicator_inactive))
            }
            indicators.addView(indicator)
        }
        updateIndicators(0)
    }
    
    private fun updateIndicators(position: Int) {
        for (i in 0 until indicators.childCount) {
            val indicator = indicators.getChildAt(i) as MaterialCardView
            val color = if (i == position) R.color.indicator_active else R.color.indicator_inactive
            indicator.setCardBackgroundColor(ContextCompat.getColor(this, color))
        }
    }
    
    private fun setupButtons() {
        btnNext.setOnClickListener {
            if (currentPage < totalPages - 1) {
                currentPage++
                showPage(currentPage)
            } else {
                completeOnboarding()
            }
        }
        
        btnSkip.setOnClickListener {
            completeOnboarding()
        }
        
        updateButtons()
    }
    
    private fun showPage(page: Int) {
        if (page < onboardingData.size) {
            val item = onboardingData[page]
            illustration.setImageResource(item.illustration)
            title.text = item.title
            description.text = item.description
            updateIndicators(page)
            updateButtons()
        }
    }
    
    private fun updateButtons() {
        if (currentPage == totalPages - 1) {
            btnNext.text = "Get Started"
        } else {
            btnNext.text = "Next"
        }
    }
    
    private fun completeOnboarding() {
        val preferenceManager = PreferenceManager(this)
        preferenceManager.setFirstLaunchCompleted()
        preferenceManager.setOnboardingCompleted()
        
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}




