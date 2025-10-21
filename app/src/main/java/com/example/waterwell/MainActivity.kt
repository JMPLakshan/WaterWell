package com.example.waterwell

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.waterwell.databinding.ActivityMainBinding
import com.example.waterwell.utils.PreferenceManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Create notification channel for hydration reminders
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val ch = NotificationChannel(
                "hydration", "Hydration Reminders",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            getSystemService(NotificationManager::class.java).createNotificationChannel(ch)
        }

        val navController = findNavController(R.id.nav_host)
        binding.bottomNav.setupWithNavController(navController)
        
        // Debug: Add long press on bottom nav to reset onboarding
        binding.bottomNav.setOnLongClickListener {
            resetOnboardingForTesting()
            true
        }
    }
    
    private fun resetOnboardingForTesting() {
        val preferenceManager = PreferenceManager(this)
        preferenceManager.resetOnboarding()
        Toast.makeText(this, "Onboarding reset! Restart app to see onboarding screens.", Toast.LENGTH_LONG).show()
    }
}
