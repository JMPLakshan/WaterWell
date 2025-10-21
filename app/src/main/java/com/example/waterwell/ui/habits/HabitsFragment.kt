package com.example.waterwell.ui.habits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.waterwell.data.Prefs
import com.example.waterwell.data.models.Habit
import com.example.waterwell.data.repository.HabitRepository
import com.example.waterwell.databinding.FragmentHabitsBinding
import com.example.waterwell.util.DateUtils

class HabitsFragment : Fragment() {

    private var _binding: FragmentHabitsBinding? = null
    private val binding get() = _binding!!

    private lateinit var repo: HabitRepository
    private lateinit var adapter: HabitAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHabitsBinding.inflate(inflater, container, false)
        repo = HabitRepository(requireContext())

        // midnight reset (simple check on open)
        val prefs = Prefs(requireContext())
        val today = DateUtils.todayKey()
        if (prefs.getLastResetDay() != today) {
            repo.resetTodayFlags()
            prefs.setLastResetDay(today)
        }

        adapter = HabitAdapter(repo.all(),
            onToggle = { id, done -> repo.toggle(id, done); updateEmpty() },
            onEdit = { habit ->
                val action = com.example.waterwell.R.id.action_habitsFragment_to_editHabitFragment
                findNavController().navigate(action, EditHabitFragment.bundleFor(habit))
            })

        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter

        // Swipe to delete
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(rv: RecyclerView, vh: RecyclerView.ViewHolder, t: RecyclerView.ViewHolder) = false
            override fun onSwiped(vh: RecyclerView.ViewHolder, dir: Int) {
                val item = adapter.current[vh.bindingAdapterPosition]
                repo.remove(item.id)
                adapter.refresh(repo.all())
                updateEmpty()
            }
        }).attachToRecyclerView(binding.recycler)

        binding.fabAdd.setOnClickListener {
            EditHabitFragment.newDialog(requireContext()) { title, desc ->
                repo.add(Habit(title = title, description = desc))
                adapter.refresh(repo.all()); updateEmpty()
            }.show()
        }

        updateEmpty()
        return binding.root
    }

    private fun updateEmpty() {
        binding.empty.isVisible = adapter.current.isEmpty()
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
