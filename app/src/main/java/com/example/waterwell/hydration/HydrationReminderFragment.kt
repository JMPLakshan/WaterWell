package com.example.waterwell.hydration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.waterwell.R

class HydrationReminderFragment : Fragment() {

    private lateinit var spinnerInterval: Spinner
    private lateinit var btnStart: Button
    private lateinit var btnStop: Button
    private lateinit var tvStatus: TextView
    private val scheduler by lazy { ReminderScheduler(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_hydration_reminder, container, false)

        spinnerInterval = view.findViewById(R.id.spinnerInterval)
        btnStart = view.findViewById(R.id.btnStart)
        btnStop = view.findViewById(R.id.btnStop)
        tvStatus = view.findViewById(R.id.tvStatus)

        setupSpinner()
        setupButtons()

        return view
    }

    private fun setupSpinner() {
        val intervals = listOf("1 minute", "5 minutes", "10 minutes", "15 minutes", "30 minutes", "1 hour")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, intervals)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInterval.adapter = adapter
        spinnerInterval.setSelection(0)
    }

    private fun setupButtons() {
        btnStart.setOnClickListener {
            val selection = spinnerInterval.selectedItem.toString()
            val minutes = when {
                selection.contains("1 minute") -> 1L
                selection.contains("5 minutes") -> 5L
                selection.contains("10 minutes") -> 10L
                selection.contains("15 minutes") -> 15L
                selection.contains("30 minutes") -> 30L
                else -> 60L
            }
            scheduler.scheduleReminder(minutes)
            tvStatus.text = "Status: reminders every $minutes minute(s)"
            Toast.makeText(requireContext(), "Hydration reminders started", Toast.LENGTH_SHORT).show()
        }

        btnStop.setOnClickListener {
            scheduler.cancelReminder()
            tvStatus.text = "Status: stopped"
            Toast.makeText(requireContext(), "Reminders stopped", Toast.LENGTH_SHORT).show()
        }
    }
}
