package com.example.waterwell.ui.charts

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.waterwell.databinding.FragmentMoodChartBinding
import com.example.waterwell.ui.shared.HabitSharedViewModel
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import android.graphics.Color
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class MoodChartFragment : Fragment() {
    private var _b: FragmentMoodChartBinding? = null
    private val b get() = _b!!

    private val sharedViewModel: HabitSharedViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _b = FragmentMoodChartBinding.inflate(inflater, container, false)

        // Observe habits to update chart automatically
        sharedViewModel.habits.observe(viewLifecycleOwner) { habits ->
            val total = habits.size
            val completed = habits.count { it.isCompletedToday }
            val remaining = (total - completed).coerceAtLeast(0)

            val entries = ArrayList<PieEntry>()
            if (completed > 0) entries.add(PieEntry(completed.toFloat(), "Completed"))
            if (remaining > 0) entries.add(PieEntry(remaining.toFloat(), "Remaining"))

            val set = PieDataSet(entries, "Today's Habits")
            // Use actual color ints (not resource ids) to avoid Resources.NotFoundException
            set.colors = listOf(
                Color.parseColor("#4CAF50"), // completed
                Color.parseColor("#BDBDBD")  // remaining
            )
            set.sliceSpace = 2f
            set.valueTextSize = 12f

            b.chart.data = PieData(set)
            b.chart.setUsePercentValues(true)
            b.chart.centerText = getString(com.example.waterwell.R.string.chart_title)
            b.chart.setDrawEntryLabels(false)
            b.chart.description.isEnabled = false
            // Disable interactions to prevent accidental taps causing crashes/navigation
            b.chart.setTouchEnabled(false)
            b.chart.setHighlightPerTapEnabled(false)
            b.chart.setNoDataText("No data")
            b.chart.legend.orientation = Legend.LegendOrientation.HORIZONTAL
            b.chart.legend.isWordWrapEnabled = true
            b.chart.legend.textSize = 12f
            b.chart.invalidate()

            // Build bar data for this week: completed counts per day
            val entriesBar = ArrayList<BarEntry>()
            // Simple: show completed vs remaining as two bars
            entriesBar.add(BarEntry(0f, completed.toFloat()))
            entriesBar.add(BarEntry(1f, remaining.toFloat()))

            val barSet = BarDataSet(entriesBar, "Today")
            barSet.colors = listOf(Color.parseColor("#4CAF50"), Color.parseColor("#BDBDBD"))
            barSet.valueTextSize = 12f

            val barData = BarData(barSet)
            barData.barWidth = 0.6f
            b.barChart.data = barData
            b.barChart.description.isEnabled = false
            b.barChart.axisRight.isEnabled = false
            b.barChart.axisLeft.axisMinimum = 0f
            b.barChart.xAxis.granularity = 1f
            b.barChart.xAxis.setDrawGridLines(false)
            b.barChart.setTouchEnabled(false)
            b.barChart.invalidate()
        }

        return b.root
    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
