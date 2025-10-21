package com.example.waterwell.ui.charts

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.waterwell.data.repository.MoodRepository
import com.example.waterwell.databinding.FragmentMoodChartBinding
import com.example.waterwell.util.DateUtils
import com.example.waterwell.util.EmojiScore
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class MoodChartFragment : Fragment() {
    private var _b: FragmentMoodChartBinding? = null
    private val b get() = _b!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _b = FragmentMoodChartBinding.inflate(inflater, container, false)
        val moods = MoodRepository(requireContext()).all()

        // last 7 days including today
        val entries = (6 downTo 0).mapIndexed { idx, back ->
            val dayTs = DateUtils.daysBack(back)
            val dayMoods = moods.filter { DateUtils.isSameDay(it.timestamp, dayTs) }
            val avg = if (dayMoods.isEmpty()) 0f else dayMoods.map { EmojiScore.score(it.emoji) }.average().toFloat()
            Entry(idx.toFloat(), avg)
        }

        val set = LineDataSet(entries, "Mood (last 7 days)")
        b.chart.data = LineData(set)
        b.chart.axisLeft.axisMinimum = 0f
        b.chart.axisLeft.axisMaximum = 5f
        b.chart.axisRight.isEnabled = false
        b.chart.description.isEnabled = false
        b.chart.invalidate()

        return b.root
    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
