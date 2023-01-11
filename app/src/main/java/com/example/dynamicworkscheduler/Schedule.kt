package com.example.dynamicworkscheduler;

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
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
    var task_item_list = arrayListOf<TaskFormat>()
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
    var weekListBoolean = mutableListOf<Boolean>()
    private var selecteddateindex = getCurDate();
    private var dateSelected = false;

    lateinit var weekListData:MutableList<MutableList<Task>>
    val titles0 = mutableListOf<String>()
    val titles1 = mutableListOf<String>()
    val titles2 = mutableListOf<String>()
    val titles3 = mutableListOf<String>()
    val titles4 = mutableListOf<String>()
    val titles5 = mutableListOf<String>()
    val titles6 = mutableListOf<String>()
    val des0 = mutableListOf<String>()
    val des1 = mutableListOf<String>()
    val des2 = mutableListOf<String>()
    val des3 = mutableListOf<String>()
    val des4 = mutableListOf<String>()
    val des5 = mutableListOf<String>()
    val des6 = mutableListOf<String>()
    val durations0 = mutableListOf<String>()
    val durations1 = mutableListOf<String>()
    val durations2 = mutableListOf<String>()
    val durations3 = mutableListOf<String>()
    val durations4 = mutableListOf<String>()
    val durations5 = mutableListOf<String>()
    val durations6 = mutableListOf<String>()
    lateinit var bgColor:IntArray
    lateinit var preferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityScheduleBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        preferences = getSharedPreferences("iValue", MODE_PRIVATE)
        Active_Item = preferences.getString("i",null)?.toInt() ?:0
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
        initUserTaskList()
    }
    //Create a week Array//
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun weekArrayList() {

        /* TASK DEADLINE SELECTION */

        /* TASK INIT() DEADLINE DATES */
        val sdf = SimpleDateFormat("dd", Locale.getDefault())
        val monthsdf=SimpleDateFormat("MMMM ", Locale.getDefault())
        val calender: Calendar = Calendar.getInstance()
        //calender.set(Calendar.WEEK_OF_YEAR,Calendar.getInstance().get(Calendar.WEEK_OF_YEAR))
        val fistDayOfWeek: Int = calender.firstDayOfWeek
        calender.set(Calendar.DAY_OF_WEEK, fistDayOfWeek)
        var startDate: String = sdf.format(calender.time)
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
            weekListBoolean.add(false)
            idx += 1
        }


        var isCurDay = false
        var today = Calendar.getInstance()
        var todayString: String = sdf.format(today.time)
//        Toast.makeText(this, "$todayString", Toast.LENGTH_SHORT).show()


        Log.d("WeekDatesToday", todayString)
        Log.d("WeekListDatesSize " ,weekListDates.size.toString())
        for (i in 0 until weekListDates.size) {
            if (todayString == weekListDates[i]) {
                weekListBoolean[i] = true
            }
        }


        mDay_date1.text = weekListDates[0]
        mDay_date2.text = weekListDates[1]
        mDay_date3.text = weekListDates[2]
        mDay_date4.text = weekListDates[3]
        mDay_date5.text = weekListDates[4]
        mDay_date6.text = weekListDates[5]
        mDay_date7.text = weekListDates[6]
        //Initial Dates//

        monthDisplay.text=monthsdf.format(Date()) + " "+todayString
        // change background of selected layout to tinted background and change all other layouts to non-tinted background

        if(weekListBoolean[0])
        {
            mDay1.background = resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn)
            mDay_day1.setTextColor(resources.getColor(R.color.white))
            mDay_date1.setTextColor(resources.getColor(R.color.white))
        }
        else
        {
            mDay1.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day1.setTextColor(resources.getColor(R.color.black))
            mDay_date1.setTextColor(resources.getColor(R.color.black))
        }

        if(weekListBoolean[1])
        {
            mDay2.background = resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn)
            mDay_day2.setTextColor(resources.getColor(R.color.white))
            mDay_date2.setTextColor(resources.getColor(R.color.white))
        }
        else
        {
            mDay2.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day2.setTextColor(resources.getColor(R.color.black))
            mDay_date2.setTextColor(resources.getColor(R.color.black))
        }

        if(weekListBoolean[2])
        {
            mDay3.background = resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn)
            mDay_day3.setTextColor(resources.getColor(R.color.white))
            mDay_date3.setTextColor(resources.getColor(R.color.white))
        }
        else
        {
            mDay3.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day3.setTextColor(resources.getColor(R.color.black))
            mDay_date3.setTextColor(resources.getColor(R.color.black))
        }

        if(weekListBoolean[3])
        {
            mDay4.background = resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn)
            mDay_day4.setTextColor(resources.getColor(R.color.white))
            mDay_date4.setTextColor(resources.getColor(R.color.white))
        }
        else
        {
            mDay4.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day4.setTextColor(resources.getColor(R.color.black))
            mDay_date4.setTextColor(resources.getColor(R.color.black))
        }

        if(weekListBoolean[4])
        {
            mDay5.background = resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn)
            mDay_day5.setTextColor(resources.getColor(R.color.white))
            mDay_date5.setTextColor(resources.getColor(R.color.white))
        }
        else
        {
            mDay5.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day5.setTextColor(resources.getColor(R.color.black))
            mDay_date5.setTextColor(resources.getColor(R.color.black))
        }

        if(weekListBoolean[5])
        {
            mDay6.background = resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn)
            mDay_day6.setTextColor(resources.getColor(R.color.white))
            mDay_date6.setTextColor(resources.getColor(R.color.white))
        }
        else
        {
            mDay6.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day6.setTextColor(resources.getColor(R.color.black))
            mDay_date6.setTextColor(resources.getColor(R.color.black))
        }

        if(weekListBoolean[6])
        {
            mDay7.background = resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn)
            mDay_day7.setTextColor(resources.getColor(R.color.white))
            mDay_date7.setTextColor(resources.getColor(R.color.white))
        }
        else
        {
            mDay7.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
            mDay_day7.setTextColor(resources.getColor(R.color.black))
            mDay_date7.setTextColor(resources.getColor(R.color.black))
        }

//        mDay2.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
//        mDay_day2.setTextColor(resources.getColor(R.color.black))
//        mDay_date2.setTextColor(resources.getColor(R.color.black))
//
//        mDay3.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
//        mDay_day3.setTextColor(resources.getColor(R.color.black))
//        mDay_date3.setTextColor(resources.getColor(R.color.black))
//
//        mDay4.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
//        mDay_day4.setTextColor(resources.getColor(R.color.black))
//        mDay_date4.setTextColor(resources.getColor(R.color.black))
//
//        mDay5.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
//        mDay_day5.setTextColor(resources.getColor(R.color.black))
//        mDay_date5.setTextColor(resources.getColor(R.color.black))
//
//        mDay6.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
//        mDay_day6.setTextColor(resources.getColor(R.color.black))
//        mDay_date6.setTextColor(resources.getColor(R.color.black))
//
//        mDay7.background = resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
//        mDay_day7.setTextColor(resources.getColor(R.color.black))
//        mDay_date7.setTextColor(resources.getColor(R.color.black))


        //Initial Dates//
//        initWeekLayout();

        /* Add Listeners and change the colors accordingly*/
        mDay1.setOnClickListener { v: View? ->

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
        selecteddateindex = 0
        dateSelected = true
        initUserTaskList()
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
            selecteddateindex = 1
            dateSelected = true
            initUserTaskList()
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
            selecteddateindex = 2
            dateSelected = true
            initUserTaskList()
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
            selecteddateindex = 3
            dateSelected = true
            initUserTaskList()
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
            selecteddateindex = 4
            dateSelected = true
            initUserTaskList()
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
            selecteddateindex = 5
            dateSelected = true
            initUserTaskList()
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
            selecteddateindex = 6
            dateSelected = true
            initUserTaskList()
        }

       // Toast.makeText(this, selecteddateindex.toString(), Toast.LENGTH_SHORT).show()

    }


    //Create a week Array//

    private fun initUserTaskList() {

        task_item_list.clear()
        // weekListData[0][i]
        weekListData=MyApplication.getDayTasksObject()
        val non_active_state: Int = R.drawable.ic_circle
        val active_state_IV: Int = R.drawable.ic_radio_checked
        val active_state_bg: Int = R.drawable.active_task_total_task_bg
        assignValuesToList()
        //titles.clear()


        val tempList = weekListData[selecteddateindex]


        for (i in 0 until tempList.size) {

            val startTime = getChunkValues(tempList[i].startTime.toString())
            val endTime = getChunkValues(tempList[i].endTime.toString())
            val duration = ((endTime[0].toInt()-startTime[0].toInt())*60+(endTime[1].toInt()-startTime[1].toInt()))
            if (Active_Item == i  && (selecteddateindex == getCurDate())) task_item_list.add(
                TaskFormat(
                    tempList[i].title.toString(),
                    tempList[i].description.toString(), " $duration\nmins", active_state_bg, active_state_IV
                )
            ) else task_item_list.add(
                TaskFormat(
                    tempList[i].title.toString(), tempList[i].description.toString(), " $duration\nmins", bgColor[i%3], non_active_state
                )
            )
        }

        val totalTaskAdapter = TotalTaskAdapter(this@Schedule, task_item_list)
        mTaskActivity_LV!!.adapter = totalTaskAdapter
        mTaskActivity_LV!!.isClickable = true
    }

    private fun getCurDate(): Int {
        return Calendar.getInstance().time.day;
    }

    fun assignValuesToList(){
        var duration = 0
        lateinit var startTime: MutableList<String>
        lateinit var endTime: MutableList<String>
        for (i in 0..6){
            if(weekListData[i].isNotEmpty()) {
                weekListData[i].forEach {
                    startTime = getChunkValues(it.startTime.toString())
                    endTime = getChunkValues(it.endTime.toString())
                    duration = ((endTime[0].toInt()-startTime[0].toInt())*60+(endTime[1].toInt()-startTime[1].toInt()))
                    when(i){
                        0->{
                            titles0.add(it.title.toString())
                            des0.add(it.description.toString())
                            durations0.add("$duration mins")
                        }
                        1->{
                            titles1.add(it.title.toString())
                            des1.add(it.description.toString())
                            durations1.add("$duration mins")
                        }
                        2->{
                            titles2.add(it.title.toString())
                            des2.add(it.description.toString())
                            durations2.add("$duration mins")
                        }
                        3->{
                            titles3.add(it.title.toString())
                            des3.add(it.description.toString())
                            durations3.add("$duration mins")
                        }
                        4->{
                            titles4.add(it.title.toString())
                            des4.add(it.description.toString())
                            durations4.add("$duration mins")
                        }
                        5->{
                            titles5.add(it.title.toString())
                            des5.add(it.description.toString())
                            durations5.add("$duration mins")
                        }
                        6->{
                            titles6.add(it.title.toString())
                            des6.add(it.description.toString())
                            durations6.add("$duration mins")
                        }
                    }
                }
            }
        }
    }

    fun getChunkValues(time:String):MutableList<String>{
        val chunkedTime = time.filter { it.isDigit() }.chunked(2).toMutableList()
        return chunkedTime
    }

    fun backToDashboard(view: View?) {
        onBackPressed()
        finish()
    }
}