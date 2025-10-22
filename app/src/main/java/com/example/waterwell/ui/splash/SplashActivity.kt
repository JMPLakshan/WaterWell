package com.example.waterwell.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.waterwell.R
import com.example.waterwell.ui.MainActivity  // ✅ Correct import

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Launch MainActivity and finish splash
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
