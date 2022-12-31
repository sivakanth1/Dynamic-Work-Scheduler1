package com.example.dynamicworkscheduler

import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.PieChart
import android.os.Bundle
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieData
import android.graphics.Typeface
import android.os.Build
import android.view.View
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.dynamicworkscheduler.databinding.ActivityReportScreenBinding
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import java.time.LocalDate
import java.util.ArrayList

class ReportScreen : AppCompatActivity() {
    lateinit var mTaskActivity_LV: ListView
    lateinit var act_task_list: ArrayList<TaskActivity?>
    private lateinit var chart_colors: IntArray
    private lateinit var pieChart: PieChart
    private lateinit var binding:ActivityReportScreenBinding
    lateinit var weekListData:MutableList<MutableList<Task>>
    var finishedTaskCount=0
    var suspendedTaskCount=0
    var pendingTaskCount=0
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pieChart = binding.pieChart
        mTaskActivity_LV = binding.TaskActivityLV
        initTaskActivityList()
        setUpPieChart()
        initPieChart()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initTaskActivityList() {
        val titles = mutableListOf<MutableList<String>>()
        val startTimes = mutableListOf<MutableList<String>>()
        val endTimes = mutableListOf<MutableList<String>>()
        val states = mutableListOf<MutableList<Int>>()
        val dynamicBg = mutableListOf<MutableList<Int>>()
        weekListData = MyApplication.getDayTasksObject()
        println(weekListData)
        titles.clear()
        startTimes.clear()
        endTimes.clear()
        states.clear()
        dynamicBg.clear()
        repeat(7){
            val rowTitles = mutableListOf<String>()
            val rowStartTimes = mutableListOf<String>()
            val rowEndTimes = mutableListOf<String>()
            val rowStates = mutableListOf<Int>()
            val rowDynamicBg= mutableListOf<Int>()
            titles.add(rowTitles)
            startTimes.add(rowStartTimes)
            endTimes.add(rowEndTimes)
            states.add(rowStates)
            dynamicBg.add(rowDynamicBg)
        }
        for (i in 0..6){
                weekListData[i].forEach {
                    titles[i].add(it.title.toString())
                    startTimes[i].add(it.startTime.toString())
                    endTimes[i].add(it.endTime.toString())
                    when (it.status) {
                        "pending" -> {
                            states[i].add(R.drawable.ic_pending)
                            dynamicBg[i].add(R.drawable.pending_task_bg)
                        }
                        "finished" -> {
                            states[i].add(R.drawable.ic_finished)
                            dynamicBg[i].add(R.drawable.ic_finished)
                        }
                        "suspended" -> {
                            states[i].add(R.drawable.ic_suspended)
                            dynamicBg[i].add(R.drawable.ic_suspended)
                        }
                    }
                }
        }
        act_task_list = ArrayList()
        for (i in titles[LocalDate.now().dayOfWeek.value].indices) {
            act_task_list.add(
                TaskActivity(
                    startTimes[LocalDate.now().dayOfWeek.value][i],
                    titles[LocalDate.now().dayOfWeek.value][i],
                    startTimes[LocalDate.now().dayOfWeek.value][i],
                    endTimes[LocalDate.now().dayOfWeek.value][i],
                    states[LocalDate.now().dayOfWeek.value][i],
                    dynamicBg[LocalDate.now().dayOfWeek.value][i]
                )
            )
        }
        finishedTaskCount=states[LocalDate.now().dayOfWeek.value].count {
            it == R.drawable.ic_finished
        }
        suspendedTaskCount=states[LocalDate.now().dayOfWeek.value].count {
            it == R.drawable.ic_suspended
        }
        pendingTaskCount=states[LocalDate.now().dayOfWeek.value].count {
            it == R.drawable.ic_pending
        }
        val taskActivityAdapter = TaskActivityAdapter(this, act_task_list)
        mTaskActivity_LV.adapter = taskActivityAdapter
        mTaskActivity_LV.isClickable = true

      //  Toast.makeText(this,"${weekListData[LocalDate.now().dayOfWeek.value]}",Toast.LENGTH_SHORT).show()
    }

    private fun initPieChart() {
        chart_colors = IntArray(3)
        chart_colors[0] = resources.getColor(R.color.finished_task_color) //#47B39C
        chart_colors[1] = resources.getColor(R.color.pending_task_color) // #FFC154
        chart_colors[2] = resources.getColor(R.color.suspended_task_color) // #EC6B56
        val pieEntries = ArrayList<PieEntry>()
        pieEntries.add(PieEntry(finishedTaskCount.toFloat(), "Tasks"))
        pieEntries.add(PieEntry(pendingTaskCount.toFloat(), "Tasks"))
        pieEntries.add(PieEntry(suspendedTaskCount.toFloat(), "Tasks"))
        val pieDataSet = PieDataSet(pieEntries, "Report")
        pieDataSet.setColors(*chart_colors)
        pieDataSet.setDrawIcons(false)
        val pieData = PieData(pieDataSet)
        //        Toast.makeText(this, String.format("%.0f" ,(pieEntries.get(1).getValue())), Toast.LENGTH_SHORT).show();
        pieData.setValueTypeface(Typeface.DEFAULT_BOLD)
        pieChart.setUsePercentValues(false)
        pieData.setValueTextSize(14f)
        pieChart.data = pieData
        pieChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry, h: Highlight) {
//                Toast.makeText(Report_Screen.this, e.get, Toast.LENGTH_SHORT).show();
            }

            override fun onNothingSelected() {}
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setUpPieChart() {
        pieChart.setUsePercentValues(false)
        pieChart.animateXY(1000, 1000)
        pieChart.isHapticFeedbackEnabled = true
        pieChart.description.textColor = resources.getColor(R.color.black)
        pieChart.description.isEnabled = false
        pieChart.holeRadius = 30f
        pieChart.setEntryLabelTextSize(13f)
        pieChart.setEntryLabelTypeface(Typeface.DEFAULT_BOLD)
        pieChart.transparentCircleRadius = 0f
        pieChart.isRotationEnabled = false
        pieChart.centerText = "${weekListData[LocalDate.now().dayOfWeek.value].size}\nTasks"
       // pieChart.centerText = "$8\nTasks"
        pieChart.setCenterTextSize(16f)
        pieChart.setCenterTextColor(R.color.black)
        pieChart.setCenterTextTypeface(Typeface.DEFAULT_BOLD)
        pieChart.legend.isEnabled = false
        pieChart.invalidate()
    }

    fun backToDashboard(view: View?) {
        onBackPressed()
    }
}