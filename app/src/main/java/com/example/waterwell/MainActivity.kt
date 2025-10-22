package com.example.waterwell.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.waterwell.R
import com.example.waterwell.databinding.ActivityMainBinding
import com.example.waterwell.utils.PreferenceManager
import com.example.waterwell.notifications.NotificationHelper

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
        createNotificationChannel()

        // 🧪 Debug shortcut: long-press bottom nav to reset onboarding
        binding.bottomNav.setOnLongClickListener {
            resetOnboardingForTesting()
            true
        }
    }

    /**
     * ✅ Setup Navigation Component
     * Uses safe NavHostFragment reference instead of findNavController().
     */
    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)
    }

    /**
     * ✅ Ensure the hydration notification channel exists.
     * Safe for Android 8.0+ and avoids redundant recreation.
     */
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NotificationHelper.CHANNEL_HYDRATION_ID,
                "Hydration Reminders",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Reminds you to drink water regularly"
            }

            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
    }

    /**
     * 🧪 Developer-only helper to clear onboarding status for testing.
     */
    private fun resetOnboardingForTesting() {
        val preferenceManager = PreferenceManager(this)
        preferenceManager.resetOnboarding()
        Toast.makeText(
            this,
            "✅ Onboarding reset! Restart app to see onboarding screens.",
            Toast.LENGTH_LONG
        ).show()
    }
}
