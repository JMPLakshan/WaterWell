package com.example.waterwell.ui.mood

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.waterwell.R
import com.example.waterwell.data.repository.MoodRepository
import com.example.waterwell.databinding.FragmentMoodBinding

class MoodFragment : Fragment() {

    private var _b: FragmentMoodBinding? = null
    private val b get() = _b!!
    private lateinit var repo: MoodRepository
    private lateinit var adapter: MoodAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _b = FragmentMoodBinding.inflate(inflater, container, false)
        repo = MoodRepository(requireContext())
        adapter = MoodAdapter(repo.all())
        b.recycler.layoutManager = LinearLayoutManager(requireContext())
        b.recycler.adapter = adapter

        b.fabAddMood.setOnClickListener {
            findNavController().navigate(R.id.action_moodFragment_to_addMoodFragment)
        }
        return b.root
    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
