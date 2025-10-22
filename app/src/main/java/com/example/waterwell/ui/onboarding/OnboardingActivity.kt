package com.example.waterwell.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import android.widget.LinearLayout
import android.widget.Button
import com.example.waterwell.R
import com.example.waterwell.ui.MainActivity

class OnboardingActivity : AppCompatActivity() {

    private lateinit var onboardingViewPager: ViewPager2
    private lateinit var indicatorsContainer: LinearLayout
    private lateinit var adapter: OnboardingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        onboardingViewPager = findViewById(R.id.onboardingViewPager)
        indicatorsContainer = findViewById(R.id.indicatorsContainer)

        adapter = OnboardingAdapter(
            listOf(
                OnboardingItem(
                    title = getString(R.string.onboarding_title_1),
                    description = getString(R.string.onboarding_desc_1),
                    imageResId = R.drawable.onboarding1
                ),
                OnboardingItem(
                    title = getString(R.string.onboarding_title_2),
                    description = getString(R.string.onboarding_desc_2),
                    imageResId = R.drawable.onboarding2
                ),
                OnboardingItem(
                    title = getString(R.string.onboarding_title_3),
                    description = getString(R.string.onboarding_desc_3),
                    imageResId = R.drawable.onboarding3
                )
            )
        )

        onboardingViewPager.adapter = adapter

        findViewById<Button>(R.id.buttonNext).setOnClickListener {
            if (onboardingViewPager.currentItem + 1 < adapter.itemCount) {
                onboardingViewPager.currentItem++
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }
}
