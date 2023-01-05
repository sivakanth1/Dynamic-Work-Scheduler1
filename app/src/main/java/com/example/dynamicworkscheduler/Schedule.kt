package com.example.dynamicworkscheduler;

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.dynamicworkscheduler.databinding.ActivityScheduleBinding
import java.text.SimpleDateFormat
import java.util.*

class Schedule : AppCompatActivity() {

    private lateinit var binding: ActivityScheduleBinding
    var mAdd_task_BTN: Button? = null
    var Active_Item = 0
    var task_item_list: ArrayList<TaskFormat>? = null
    var mTaskActivity_LV: ListView? = null
    private lateinit var mDay1: LinearLayout
    private lateinit var mDay2: LinearLayout
    private lateinit var mDay3: LinearLayout
    private lateinit var mDay4: LinearLayout
    private lateinit var mDay5: LinearLayout
    private lateinit var mDay6: LinearLayout
    private lateinit var mDay7: LinearLayout
    private lateinit var mDay_day1: TextView
    private lateinit var mDay_day2: TextView
    private lateinit var mDay_day3: TextView
    private lateinit var mDay_day4: TextView
    private lateinit var mDay_day5: TextView
    private lateinit var mDay_day6: TextView
    private lateinit var mDay_day7: TextView
    private lateinit var mDay_date1: TextView
    private lateinit var mDay_date2: TextView
    private lateinit var mDay_date3: TextView
    private lateinit var mDay_date4: TextView
    private lateinit var mDay_date5: TextView
    private lateinit var mDay_date6: TextView
    private lateinit var mDay_date7: TextView
    private lateinit var monthDisplay:TextView
    var weekListDates = mutableListOf<String>()
    private var selecteddateindex:Int=0

    lateinit var weekListData:MutableList<MutableList<Task>>
    val titles = mutableListOf<String>()
    val des = mutableListOf<String>()
    val durations = mutableListOf<String>()
    lateinit var bgColor:IntArray
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityScheduleBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        mTaskActivity_LV = binding.TaskActivityLV
        monthDisplay=binding.CurrentMonth
        mDay1 = binding.day1
        mDay2 = binding.day2
        mDay3 = binding.day3
        mDay4 = binding.day4
        mDay5 = binding.day5
        mDay6 = binding.day6
        mDay7 = binding.day7
        mDay_day1 = binding.WeekDay1
        mDay_day2 = binding.WeekDay2
        mDay_day3 = binding.WeekDay3
        mDay_day4 = binding.WeekDay4
        mDay_day5 = binding.WeekDay5
        mDay_day6 = binding.WeekDay6
        mDay_day7 = binding.WeekDay7
        mDay_date1 = binding.WeekDate1
        mDay_date2 = binding.WeekDate2
        mDay_date3 = binding.WeekDate3
        mDay_date4 = binding.WeekDate4
        mDay_date5 = binding.WeekDate5
        mDay_date6 = binding.WeekDate6
        mDay_date7 = binding.WeekDate7
        binding.addTaskBTN.setOnClickListener { v: View? ->
            startActivity(
                Intent(
                    this@Schedule,
                    CreateTask::class.java
                )
            )
            finish()
        }
        bgColor = intArrayOf(
            R.drawable.soft_blue_total_task_bg,
            R.drawable.soft_lavender_total_task_bg,
            R.drawable.soft_mustard_total_task_bg,
        )


        weekArrayList()
        //initUserTaskList()
    }
    //Create a week Array//
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun weekArrayList()
    {

        /* TASK DEADLINE SELECTION */

        /* TASK INIT() DEADLINE DATES */
        val sdf = SimpleDateFormat("dd", Locale.getDefault())
        val monthsdf=SimpleDateFormat("MMMM ", Locale.getDefault())
        val calender: Calendar = Calendar.getInstance()
        //calender.set(Calendar.WEEK_OF_YEAR,Calendar.getInstance().get(Calendar.WEEK_OF_YEAR))
        val fistDayOfWeek: Int = calender.firstDayOfWeek
        calender.set(Calendar.DAY_OF_WEEK, fistDayOfWeek)
        var startDate: String = sdf.format(calender.time.date)
        Log.d("date1=", calender.time.date.toString())
        Log.d("week1=", calender.firstDayOfWeek.toString())
        Log.d("date1=", startDate)
        calender.set(Calendar.DAY_OF_WEEK, fistDayOfWeek + 1)


        var idx = 1
        weekListDates.add(startDate)
        repeat(6)
        {
            calender.set(Calendar.DAY_OF_WEEK, fistDayOfWeek + idx)
            startDate = sdf.format(calender.time)
            idx += 1
            weekListDates.add(startDate)
        }

//        Toast.makeText(this, "$startDate", Toast.LENGTH_SHORT).show()

        idx = 0
        repeat(7)
        {
            Log.d("WeekDatesString1", weekListDates.get(idx))
            idx += 1
        }
        mDay_date1.setText(weekListDates[0])
        mDay_date2.setText(weekListDates[1])
        mDay_date3.setText(weekListDates[2])
        mDay_date4.setText(weekListDates[3])
        mDay_date5.setText(weekListDates[4])
        mDay_date6.setText(weekListDates[5])
        mDay_date7.setText(weekListDates[6])
        //Initial Dates//

        monthDisplay.text=monthsdf.format(Date())
        // change background of selected layout to tinted background and change all other layouts to non-tinted background
        mDay1.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
        mDay_day1.setTextColor(resources.getColor(R.color.black))
        mDay_date1.setTextColor(resources.getColor(R.color.black))

        mDay2.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
        mDay_day2.setTextColor(resources.getColor(R.color.black))
        mDay_date2.setTextColor(resources.getColor(R.color.black))

        mDay3.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
        mDay_day3.setTextColor(resources.getColor(R.color.black))
        mDay_date3.setTextColor(resources.getColor(R.color.black))

        mDay4.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
        mDay_day4.setTextColor(resources.getColor(R.color.black))
        mDay_date4.setTextColor(resources.getColor(R.color.black))

        mDay5.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
        mDay_day5.setTextColor(resources.getColor(R.color.black))
        mDay_date5.setTextColor(resources.getColor(R.color.black))

        mDay6.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
        mDay_day6.setTextColor(resources.getColor(R.color.black))
        mDay_date6.setTextColor(resources.getColor(R.color.black))

        mDay7.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
        mDay_day7.setTextColor(resources.getColor(R.color.black))
        mDay_date7.setTextColor(resources.getColor(R.color.black))
        //Initial Dates//
//        initWeekLayout();

        /* Add Listeners and change the colors accordingly*/mDay1.setOnClickListener { v: View? ->

        // change background of selected layout to tinted background and change all other layouts to non-tinted background
        mDay1.background =
            resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn)
        mDay_day1.setTextColor(resources.getColor(R.color.white))
        mDay_date1.setTextColor(resources.getColor(R.color.white))
        monthDisplay.text=monthsdf.format(Date())
        monthDisplay.append(weekListDates[0])
        mDay2.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
        mDay_day2.setTextColor(resources.getColor(R.color.black))
        mDay_date2.setTextColor(resources.getColor(R.color.black))
        mDay3.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
        mDay_day3.setTextColor(resources.getColor(R.color.black))
        mDay_date3.setTextColor(resources.getColor(R.color.black))
        mDay4.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
        mDay_day4.setTextColor(resources.getColor(R.color.black))
        mDay_date4.setTextColor(resources.getColor(R.color.black))
        mDay5.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
        mDay_day5.setTextColor(resources.getColor(R.color.black))
        mDay_date5.setTextColor(resources.getColor(R.color.black))
        mDay6.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
        mDay_day6.setTextColor(resources.getColor(R.color.black))
        mDay_date6.setTextColor(resources.getColor(R.color.black))
        mDay7.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
        mDay_day7.setTextColor(resources.getColor(R.color.black))
        mDay_date7.setTextColor(resources.getColor(R.color.black))
        selecteddateindex = 1
    }

        mDay2.setOnClickListener { v: View? ->

            // change background of selected layout to tinted background and change all other layouts to non-tinted background
            mDay2.background =
                resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn)
            mDay_day2.setTextColor(resources.getColor(R.color.white))
            mDay_date2.setTextColor(resources.getColor(R.color.white))
            monthDisplay.text=monthsdf.format(Date())
            monthDisplay.append(weekListDates[1])
            mDay1.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day1.setTextColor(resources.getColor(R.color.black))
            mDay_date1.setTextColor(resources.getColor(R.color.black))
            mDay3.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day3.setTextColor(resources.getColor(R.color.black))
            mDay_date3.setTextColor(resources.getColor(R.color.black))
            mDay4.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day4.setTextColor(resources.getColor(R.color.black))
            mDay_date4.setTextColor(resources.getColor(R.color.black))
            mDay5.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day5.setTextColor(resources.getColor(R.color.black))
            mDay_date5.setTextColor(resources.getColor(R.color.black))
            mDay6.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day6.setTextColor(resources.getColor(R.color.black))
            mDay_date6.setTextColor(resources.getColor(R.color.black))
            mDay7.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day7.setTextColor(resources.getColor(R.color.black))
            mDay_date7.setTextColor(resources.getColor(R.color.black))
            selecteddateindex = 2
        }

        mDay3.setOnClickListener { v: View? ->

            // change background of selected layout to tinted background and change all other layouts to non-tinted background
            mDay3.background =
                resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn)
            mDay_day3.setTextColor(resources.getColor(R.color.white))
            mDay_date3.setTextColor(resources.getColor(R.color.white))
            monthDisplay.text=monthsdf.format(Date())
            monthDisplay.append(weekListDates[2])
            mDay2.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day2.setTextColor(resources.getColor(R.color.black))
            mDay_date2.setTextColor(resources.getColor(R.color.black))
            mDay1.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day1.setTextColor(resources.getColor(R.color.black))
            mDay_date1.setTextColor(resources.getColor(R.color.black))
            mDay4.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day4.setTextColor(resources.getColor(R.color.black))
            mDay_date4.setTextColor(resources.getColor(R.color.black))
            mDay5.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day5.setTextColor(resources.getColor(R.color.black))
            mDay_date5.setTextColor(resources.getColor(R.color.black))
            mDay6.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day6.setTextColor(resources.getColor(R.color.black))
            mDay_date6.setTextColor(resources.getColor(R.color.black))
            mDay7.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day7.setTextColor(resources.getColor(R.color.black))
            mDay_date7.setTextColor(resources.getColor(R.color.black))
            selecteddateindex = 3
        }

        mDay4.setOnClickListener { v: View? ->

            // change background of selected layout to tinted background and change all other layouts to non-tinted background
            mDay4.background =
                resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn)
            mDay_day4.setTextColor(resources.getColor(R.color.white))
            mDay_date4.setTextColor(resources.getColor(R.color.white))
            monthDisplay.text=monthsdf.format(Date())
            monthDisplay.append(weekListDates[3])
            mDay2.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day2.setTextColor(resources.getColor(R.color.black))
            mDay_date2.setTextColor(resources.getColor(R.color.black))
            mDay3.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day3.setTextColor(resources.getColor(R.color.black))
            mDay_date3.setTextColor(resources.getColor(R.color.black))
            mDay1.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day1.setTextColor(resources.getColor(R.color.black))
            mDay_date1.setTextColor(resources.getColor(R.color.black))
            mDay5.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day5.setTextColor(resources.getColor(R.color.black))
            mDay_date5.setTextColor(resources.getColor(R.color.black))
            mDay6.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day6.setTextColor(resources.getColor(R.color.black))
            mDay_date6.setTextColor(resources.getColor(R.color.black))
            mDay7.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day7.setTextColor(resources.getColor(R.color.black))
            mDay_date7.setTextColor(resources.getColor(R.color.black))
            selecteddateindex = 4
        }

        mDay5.setOnClickListener { v: View? ->

            // change background of selected layout to tinted background and change all other layouts to non-tinted background
            mDay5.background =
                resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn)
            mDay_day5.setTextColor(resources.getColor(R.color.white))
            mDay_date5.setTextColor(resources.getColor(R.color.white))
            monthDisplay.text=monthsdf.format(Date())
            monthDisplay.append(weekListDates[4])
            mDay2.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day2.setTextColor(resources.getColor(R.color.black))
            mDay_date2.setTextColor(resources.getColor(R.color.black))
            mDay3.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day3.setTextColor(resources.getColor(R.color.black))
            mDay_date3.setTextColor(resources.getColor(R.color.black))
            mDay1.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day1.setTextColor(resources.getColor(R.color.black))
            mDay_date1.setTextColor(resources.getColor(R.color.black))
            mDay4.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day4.setTextColor(resources.getColor(R.color.black))
            mDay_date4.setTextColor(resources.getColor(R.color.black))
            mDay6.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day6.setTextColor(resources.getColor(R.color.black))
            mDay_date6.setTextColor(resources.getColor(R.color.black))
            mDay7.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day7.setTextColor(resources.getColor(R.color.black))
            mDay_date7.setTextColor(resources.getColor(R.color.black))
            selecteddateindex = 5
        }

        mDay6.setOnClickListener { v: View? ->

            // change background of selected layout to tinted background and change all other layouts to non-tinted background
            mDay6.background =
                resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn)
            mDay_day6.setTextColor(resources.getColor(R.color.white))
            mDay_date6.setTextColor(resources.getColor(R.color.white))
            monthDisplay.text=monthsdf.format(Date())
            monthDisplay.append(weekListDates[5])
            mDay2.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day2.setTextColor(resources.getColor(R.color.black))
            mDay_date2.setTextColor(resources.getColor(R.color.black))
            mDay3.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day3.setTextColor(resources.getColor(R.color.black))
            mDay_date3.setTextColor(resources.getColor(R.color.black))
            mDay4.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day4.setTextColor(resources.getColor(R.color.black))
            mDay_date4.setTextColor(resources.getColor(R.color.black))
            mDay5.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day5.setTextColor(resources.getColor(R.color.black))
            mDay_date5.setTextColor(resources.getColor(R.color.black))
            mDay1.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day1.setTextColor(resources.getColor(R.color.black))
            mDay_date1.setTextColor(resources.getColor(R.color.black))
            mDay7.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day7.setTextColor(resources.getColor(R.color.black))
            mDay_date7.setTextColor(resources.getColor(R.color.black))
            selecteddateindex = 6
        }


        mDay7.setOnClickListener { v: View? ->

            // change background of selected layout to tinted background and change all other layouts to non-tinted background
            mDay7.background =
                resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn)
            mDay_day7.setTextColor(resources.getColor(R.color.white))
            mDay_date7.setTextColor(resources.getColor(R.color.white))
            monthDisplay.text=monthsdf.format(Date())
            monthDisplay.append(weekListDates[6])
            mDay2.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day2.setTextColor(resources.getColor(R.color.black))
            mDay_date2.setTextColor(resources.getColor(R.color.black))
            mDay3.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day3.setTextColor(resources.getColor(R.color.black))
            mDay_date3.setTextColor(resources.getColor(R.color.black))
            mDay4.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day4.setTextColor(resources.getColor(R.color.black))
            mDay_date4.setTextColor(resources.getColor(R.color.black))
            mDay5.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day5.setTextColor(resources.getColor(R.color.black))
            mDay_date5.setTextColor(resources.getColor(R.color.black))
            mDay6.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day6.setTextColor(resources.getColor(R.color.black))
            mDay_date6.setTextColor(resources.getColor(R.color.black))
            mDay1.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day1.setTextColor(resources.getColor(R.color.black))
            mDay_date1.setTextColor(resources.getColor(R.color.black))
            selecteddateindex = 7
        }

       // Toast.makeText(this, selecteddateindex.toString(), Toast.LENGTH_SHORT).show()


    }


    //Create a week Array//

    private fun initUserTaskList() {
        weekListData=MyApplication.getDayTasksObject()
        val non_active_state: Int = R.drawable.ic_circle
        val active_state_IV: Int = R.drawable.ic_radio_checked
        val active_state_bg: Int = R.drawable.active_task_total_task_bg
        assignValuesToList()
        //titles.clear()
        task_item_list = ArrayList()
        for (i in titles.indices) {
            if (Active_Item == i + 1) task_item_list!!.add(
                TaskFormat(
                    titles[i],
                    des[i], durations[i], active_state_bg, active_state_IV
                )
            ) else task_item_list!!.add(
                TaskFormat(
                    titles[i], des[i], durations[i], bgColor[i], non_active_state
                )
            )
        }
        val totalTaskAdapter = TotalTaskAdapter(this@Schedule, task_item_list)
        mTaskActivity_LV!!.adapter = totalTaskAdapter
        mTaskActivity_LV!!.isClickable = true
    }

    fun assignValuesToList(){
        lateinit var duration:String
        lateinit var array: Array<MutableList<String>>
        for (i in 0..6){
            if(weekListData[i].isNotEmpty()) {
                weekListData[i].forEach {
                    titles.add(it.title.toString())
                    des.add(it.description.toString())
                    array = getChunkValues(it.startTime.toString(),it.endTime.toString())
                    val hour = array[1][0].toInt()-array[0][0].toInt()
                    val min = if(array[1][1].toInt()-array[0][1].toInt()<0){
                        array[0][1].toInt()-array[1][1].toInt()
                    }else{
                        array[1][1].toInt()-array[0][1].toInt()
                    }
                    duration = if(min>0){
                        if(min in 1..9){
                            "$hour hr 0$min mins"
                        }else{
                            "$hour hr $min mins"
                        }
                    }else{
                        "$min mins"
                    }
                    durations.add(duration)
//                    when (it.status) {
//                        "pending" -> {
//                            states.add(R.drawable.ic_pending)
//                            dynamicBg.add(R.drawable.pending_task_bg)
//                        }
//                        "finished" -> {
//                            states.add(R.drawable.ic_finished)
//                            dynamicBg.add(R.drawable.ic_finished)
//                        }
//                        "suspended" -> {
//                            states.add(R.drawable.ic_suspended)
//                            dynamicBg.add(R.drawable.ic_suspended)
//                        }
//                    }
                }
            }
        }
    }

    fun getChunkValues(startTime:String,endTime:String):Array<MutableList<String>>{
        val chunkedTaskEndTime = endTime.filter { it.isDigit() }.chunked(2).toMutableList()
        val chunkedTaskStartTime = startTime.filter { it.isDigit() }.chunked(2).toMutableList()

        return arrayOf(chunkedTaskStartTime,chunkedTaskEndTime)
    }

    fun backToDashboard(view: View?) {
        onBackPressed()
        finish()
    }
}