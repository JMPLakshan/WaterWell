package com.example.waterwell.ui.mood

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.waterwell.data.models.MoodEntry
import com.example.waterwell.data.repository.MoodRepository
import com.example.waterwell.databinding.FragmentAddMoodBinding

class AddMoodFragment : Fragment() {

    private var _b: FragmentAddMoodBinding? = null
    private val b get() = _b!!
    private lateinit var repo: MoodRepository
    private var selectedEmoji: String = "😊"

    private val preset = listOf("😞","😕","😐","🙂","😊","😄","😁")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _b = FragmentAddMoodBinding.inflate(inflater, container, false)
        repo = MoodRepository(requireContext())

        // build simple emoji row
        preset.forEach { e ->
            val tv = TextView(requireContext()).apply {
                text = e; textSize = 28f; setPadding(16,16,16,16)
                setOnClickListener { selectedEmoji = e; b.tvSelected.text = e }
            }
            b.emojiRow.addView(tv)
        }
        b.tvSelected.text = selectedEmoji

        b.btnSave.setOnClickListener {
            val note = b.etNote.text.toString().trim().ifEmpty { null }
            repo.add(MoodEntry(emoji = selectedEmoji, note = note))
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        return b.root
    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
