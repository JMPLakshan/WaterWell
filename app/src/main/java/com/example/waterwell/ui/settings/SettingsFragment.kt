package com.example.waterwell.ui.settings

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.waterwell.data.Prefs
import com.example.waterwell.databinding.FragmentSettingsBinding
import com.example.waterwell.ui.reminders.HydrationWorker
import java.util.concurrent.TimeUnit

class SettingsFragment : Fragment() {
    private var _b: FragmentSettingsBinding? = null
    private val b get() = _b!!
    private lateinit var prefs: Prefs

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _b = FragmentSettingsBinding.inflate(inflater, container, false)
        prefs = Prefs(requireContext())

        b.etMinutes.setText(prefs.getReminderMinutes().toString())

        b.btnSave.setOnClickListener {
            val minutes = b.etMinutes.text.toString().toIntOrNull()?.coerceAtLeast(15) ?: 120
            prefs.setReminderMinutes(minutes)

            val req = PeriodicWorkRequestBuilder<HydrationWorker>(minutes.toLong(), TimeUnit.MINUTES).build()
            WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
                "hydration", ExistingPeriodicWorkPolicy.UPDATE, req
            )
            b.tvStatus.text = "Reminder scheduled every $minutes minutes."
        }

        b.btnShare.setOnClickListener {
            val summary = "WaterWell: Track habits daily, log moods with emojis, and stay hydrated!"
            val i = android.content.Intent(android.content.Intent.ACTION_SEND).apply {
                type = "text/plain"; putExtra(android.content.Intent.EXTRA_TEXT, summary)
            }
            startActivity(android.content.Intent.createChooser(i, "Share summary"))
        }
        return b.root
    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
