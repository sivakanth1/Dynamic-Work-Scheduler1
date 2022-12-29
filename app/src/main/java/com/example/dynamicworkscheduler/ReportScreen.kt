package com.example.dynamicworkscheduler

import androidx.appcompat.app.AppCompatActivity
import com.example.dynamicworkscheduler.TaskActivity
import com.github.mikephil.charting.charts.PieChart
import android.os.Bundle
import com.example.dynamicworkscheduler.R
import com.example.dynamicworkscheduler.TaskActivityAdapter
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieData
import android.graphics.Typeface
import android.os.Build
import android.view.View
import android.widget.ListView
import androidx.annotation.RequiresApi
import com.example.dynamicworkscheduler.databinding.ActivityCreateTaskBinding
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
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pieChart = binding.pieChart
        //1.pieChart = findViewById(R.id.pie_chart)
        mTaskActivity_LV = binding.TaskActivityLV
       //2. mTaskActivity_LV = findViewById(R.id.TaskActivity_LV)
        setUpPieChart()
        initPieChart()
        initTaskActivityList()
    }

    private fun initTaskActivityList() {
        val titles = arrayOf(
            "Create wireframes",
            "Do Epic Shit",
            "Keep it Low",
            "Catch me If you can",
            "Lionel Messi",
            "Create wireframes",
            "Do Epic Shit",
            "Keep it Low"
        )
        val start_times =
            arrayOf("10:00", "12:00", "15:00", "17:00", "18:00", "10:00", "12:00", "15:00")
        val end_times =
            arrayOf("11:30", "14:00", "16:00", "17:30", "20:00", "11:30", "14:00", "16:00")
        val states = intArrayOf(
            R.drawable.ic_suspended,
            R.drawable.ic_pending,
            R.drawable.ic_finished,
            R.drawable.ic_suspended,
            R.drawable.ic_pending,
            R.drawable.ic_finished,
            R.drawable.ic_pending,
            R.drawable.ic_finished
        )
        val DynamicBg = intArrayOf(
            R.drawable.suspended_task_bg,
            R.drawable.pending_task_bg,
            R.drawable.finished_task_bg,
            R.drawable.suspended_task_bg,
            R.drawable.pending_task_bg,
            R.drawable.finished_task_bg,
            R.drawable.pending_task_bg,
            R.drawable.finished_task_bg
        )
        act_task_list = ArrayList()
        for (i in titles.indices) {
            act_task_list!!.add(
                TaskActivity(
                    start_times[i],
                    titles[i],
                    start_times[i],
                    end_times[i],
                    states[i],
                    DynamicBg[i]
                )
            )
        }
        val taskActivityAdapter = TaskActivityAdapter(this, act_task_list)
        mTaskActivity_LV.adapter = taskActivityAdapter
        mTaskActivity_LV.isClickable = true
    }

    private fun initPieChart() {
        chart_colors = IntArray(3)
        chart_colors[0] = resources.getColor(R.color.finished_task_color) //#47B39C
        chart_colors[1] = resources.getColor(R.color.pending_task_color) // #FFC154
        chart_colors[2] = resources.getColor(R.color.suspended_task_color) // #EC6B56
        val pieEntries = ArrayList<PieEntry>()
        pieEntries.add(PieEntry(6f, "Tasks"))
        pieEntries.add(PieEntry(3f, "Tasks"))
        pieEntries.add(PieEntry(1f, "Tasks"))
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
        pieChart.centerText = "${calculateTotalTasksOfPresentDay()}\nTasks"
        pieChart.setCenterTextSize(16f)
        pieChart.setCenterTextColor(R.color.black)
        pieChart.setCenterTextTypeface(Typeface.DEFAULT_BOLD)
        pieChart.legend.isEnabled = false
        pieChart.invalidate()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun calculateTotalTasksOfPresentDay():String{
        MyApplication.addingTasksToList()
        val currentDayOfWeek=LocalDate.now().dayOfWeek.value
        var count=0
        println(MyApplication.tasks_id_list_week[1])
        val copyOfTasksIdListWeek = MyApplication.tasks_id_list_week[1].toSet().toMutableList()
        println(copyOfTasksIdListWeek)
       copyOfTasksIdListWeek.forEach{
     //   MyApplication.tasks_id_list_week[currentDayOfWeek].forEach{
            if(it.length>2){
                count+=1
            }
        }
        return count.toString()
    }

    fun backToDashboard(view: View?) {
        onBackPressed()
    }
}