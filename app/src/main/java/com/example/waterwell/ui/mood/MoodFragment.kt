package com.example.waterwell.ui.mood

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.waterwell.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class MoodFragment : Fragment() {

    private lateinit var chipDateTime: Chip
    private lateinit var toggleMoods: MaterialButtonToggleGroup
    private lateinit var btnSad: MaterialButton
    private lateinit var btnNeutral: MaterialButton
    private lateinit var btnHappy: MaterialButton
    private lateinit var inputNote: TextInputEditText
    private lateinit var btnSave: MaterialButton
    private lateinit var recycler: RecyclerView
    private lateinit var btnBell: ImageButton

    private val calendar: Calendar = Calendar.getInstance()
    private val adapter = MoodAdapter()
    private val entries = mutableListOf<LocalMoodEntry>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_mood, container, false)

        chipDateTime = view.findViewById(R.id.chipDateTime)
        toggleMoods = view.findViewById(R.id.toggleMoods)
        btnSad = view.findViewById(R.id.btnSad)
        btnNeutral = view.findViewById(R.id.btnNeutral)
        btnHappy = view.findViewById(R.id.btnHappy)
        inputNote = view.findViewById(R.id.inputNote)
        btnSave = view.findViewById(R.id.btnSave)
        recycler = view.findViewById(R.id.recycler)
        btnBell = view.findViewById(R.id.btnBell)

        setupDateTime()
        setupEmojiToggle()
        setupRecycler()
        setupSave()
        setupBellButton()

        return view
    }

    private fun setupDateTime() {
        updateChipText()
        chipDateTime.setOnClickListener {
            val y = calendar.get(Calendar.YEAR)
            val m = calendar.get(Calendar.MONTH)
            val d = calendar.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val hr = calendar.get(Calendar.HOUR_OF_DAY)
                val min = calendar.get(Calendar.MINUTE)
                TimePickerDialog(requireContext(), { _, hourOfDay, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)
                    updateChipText()
                }, hr, min, false).show()
            }, y, m, d).show()
        }
    }

    private fun updateChipText() {
        val fmt = SimpleDateFormat("EEE, MMM d • h:mm a", Locale.getDefault())
        chipDateTime.text = fmt.format(Date(calendar.timeInMillis))
    }

    private fun setupEmojiToggle() {
        toggleMoods.check(btnNeutral.id)
    }

    private fun selectedMood(): MoodType? =
        when (toggleMoods.checkedButtonId) {
            btnSad.id -> MoodType.SAD
            btnNeutral.id -> MoodType.NEUTRAL
            btnHappy.id -> MoodType.HAPPY
            else -> null
        }

    private fun setupRecycler() {
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter
        adapter.submitList(entries.toList())
    }

    private fun setupSave() {
        btnSave.setOnClickListener {
            val mood = selectedMood()
            if (mood == null) {
                Toast.makeText(requireContext(), "Pick an emoji", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val note = inputNote.text?.toString()?.trim().orEmpty()
            val entry = LocalMoodEntry(
                timestampMillis = calendar.timeInMillis,
                mood = mood,
                note = note.ifBlank { null }
            )
            entries.add(0, entry)
            adapter.submitList(entries.toList())
            inputNote.setText("")
            Toast.makeText(requireContext(), "Mood saved", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupBellButton() {
        btnBell.setOnClickListener {
            try {
                findNavController().navigate(R.id.action_moodFragment_to_hydrationReminderFragment)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Unable to open reminders", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
