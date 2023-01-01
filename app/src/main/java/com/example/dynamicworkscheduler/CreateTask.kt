package com.example.dynamicworkscheduler

import android.annotation.SuppressLint
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import androidx.annotation.RequiresApi
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import android.app.TimePickerDialog.OnTimeSetListener
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.Log
import android.view.View
import android.widget.*
//import androidx.compose.ui.graphics.ColorFilter
import androidx.lifecycle.ViewModelProvider
import com.example.dynamicworkscheduler.data.TaskData
import com.example.dynamicworkscheduler.data.TaskViewModel
import com.example.dynamicworkscheduler.databinding.ActivityCreateTaskBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

@Suppress("DEPRECATION")

class CreateTask : AppCompatActivity() {
    private lateinit var mDurationSpinner: Spinner
    private var selectedDateIndex = -1
    private var selectedDate = "00"
    private var category = "Select Category of Task"
    private var priority = "+"
    private lateinit var cat1Ll: LinearLayout
    private lateinit var cat2Ll: LinearLayout
    private lateinit var cat3LL: LinearLayout
    private lateinit var mPriorityListLl: LinearLayout
    private lateinit var cat1Title: TextView
    private lateinit var cat2Title: TextView
    private lateinit var cat3Title: TextView
    private lateinit var cat1Des: TextView
    private lateinit var cat2Des: TextView
    private lateinit var cat3Des: TextView
    private lateinit var pri1: TextView
    private lateinit var pri2: TextView
    private lateinit var pri3: TextView
    private lateinit var pri4: TextView
    private lateinit var pri5: TextView
    private lateinit var mSelectCategoryTv: TextView
    private lateinit var mAddPriorityTv: TextView
    private lateinit var mShowTaskCategoryListDialog: Dialog
    private var startHours = 0
    private var startMinute = 0
    private var endHours = 0
    private var endMinute = 0
    private lateinit var start_selected_time: String
    private lateinit var end_selected_time: String
    private lateinit var duration_array_adapter: ArrayAdapter<String>
    var duration_time = arrayOf("< 30 mins", "< 1hr", "1hrs - 2 hrs", "2hrs - 3hrs")
    var category_list =
        arrayOf("Design", "Develop", "Blog", "Sales", "Backend", "FrontEnd", "Business")
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
    private lateinit var mAssign_end_time_BTN: Button
    private lateinit var mAssign_start_time_BTN: Button
    private lateinit var binding:ActivityCreateTaskBinding
    private lateinit var mTaskViewModel: TaskViewModel
    private lateinit var calendar: Calendar
    @SuppressLint("UseCompatLoadingForDrawables", "CutPasteId")
    @RequiresApi(api = Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mTaskViewModel= ViewModelProvider(this)[TaskViewModel::class.java]

        // hooks
        mDurationSpinner = binding.DurationSpinner
        duration_array_adapter = ArrayAdapter(this, R.layout.priority_dropdown_item, duration_time)
        mDurationSpinner.adapter = duration_array_adapter
        mAssign_start_time_BTN = binding.AssignStartTimeBTN
        mAssign_end_time_BTN = binding.AssignEndTimeBTN
        mSelectCategoryTv = binding.SelectCategoryTV
        mAddPriorityTv = binding.AddPriorityTV
        mPriorityListLl = binding.PriorityListLL
        pri1=binding.Priority1TV
        pri2 = binding.Priority2TV
        pri3 = binding.Priority3TV
        pri4 = binding.Priority4TV
        pri5 = binding.Priority5TV
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
        val sdf=SimpleDateFormat("MM")
        calendar = Calendar.getInstance()

        initWeekLayout()


        /* INIT() CATEGORY DIALOG */
        mShowTaskCategoryListDialog = Dialog(this@CreateTask)
        mShowTaskCategoryListDialog.setContentView(R.layout.task_category_selection_dialog)
        mShowTaskCategoryListDialog.window!!.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        mShowTaskCategoryListDialog.window!!.setBackgroundDrawable(getDrawable(R.drawable.all_rounded_corners_big))

        /* TASK PRIORITY SELECTION */
        mAddPriorityTv.setOnClickListener {
            mPriorityListLl.visibility = View.VISIBLE
            pri1.setOnClickListener {
                priority = pri1.text as String
                mAddPriorityTv.text = priority
                mAddPriorityTv.animate().translationY(0f)
                mPriorityListLl.visibility = View.GONE
            }
            pri2.setOnClickListener {
                priority = pri2.text as String
                mAddPriorityTv.text = priority
                mPriorityListLl.visibility = View.GONE
            }
            pri3.setOnClickListener {
                priority = pri3.text as String
                mAddPriorityTv.text = priority
                mPriorityListLl.visibility = View.GONE
            }
            pri4.setOnClickListener {
                priority = pri4.text as String
                mAddPriorityTv.text = priority
                mPriorityListLl.visibility = View.GONE
            }
            pri5.setOnClickListener {
                priority = pri5.text as String
                mAddPriorityTv.text = priority
                mPriorityListLl.visibility = View.GONE
            }
        }


        /* TASK CATEGORY SELECTION */
        mSelectCategoryTv.setOnClickListener {
            // hooking the elements
            cat1Ll = mShowTaskCategoryListDialog.findViewById(R.id.category_1_LL)
            cat2Ll = mShowTaskCategoryListDialog.findViewById(R.id.category_2_LL)
            cat3LL = mShowTaskCategoryListDialog.findViewById(R.id.category_3_LL)
            cat1Title = mShowTaskCategoryListDialog.findViewById(R.id.category_1_title_TV)
            cat2Title = mShowTaskCategoryListDialog.findViewById(R.id.category_2_title_TV)
            cat3Title = mShowTaskCategoryListDialog.findViewById(R.id.category_3_title_TV)
            cat1Des = mShowTaskCategoryListDialog.findViewById(R.id.category_1_des_TV)
            cat2Des = mShowTaskCategoryListDialog.findViewById(R.id.category_2_des_TV)
            cat3Des = mShowTaskCategoryListDialog.findViewById(R.id.category_3_des_TV)

            // popup
            mShowTaskCategoryListDialog.show()
            cat1Ll.setOnClickListener {

                // change this layout and its element's tint to royal blue and soft black and others to white and soft black
                cat1Ll.background = resources.getDrawable(R.drawable.tinted_all_rounded_corners)
                cat1Title.setTextColor(resources.getColor(R.color.white))
                cat1Des.setTextColor(resources.getColor(R.color.white))


                //
                cat2Ll.background = resources.getDrawable(R.drawable.all_rounded_corners)
                cat2Title.setTextColor(resources.getColor(R.color.soft_black))
                cat2Des.setTextColor(resources.getColor(R.color.soft_black))
                cat3LL.background = resources.getDrawable(R.drawable.all_rounded_corners)
                cat3Title.setTextColor(resources.getColor(R.color.soft_black))
                cat3Des.setTextColor(resources.getColor(R.color.soft_black))
                category = "Category-1"
                binding.selectTimeRl.visibility = View.VISIBLE
                //1. findViewById<RelativeLayout>(R.id.select_time_rl).visibility = View.VISIBLE
                binding.selectDurationTV.visibility = View.GONE
                //2. findViewById<TextView>(R.id.select_duration_TV).visibility = View.GONE
                binding.DurationSpinner.visibility = View.GONE
                //3. findViewById<Spinner>(R.id.Duration_Spinner).visibility = View.GONE
            }
            cat2Ll.setOnClickListener {

                // change this layout and its element's tint to royal blue and soft black and others to white and soft black
                cat2Ll.background = resources.getDrawable(R.drawable.tinted_all_rounded_corners)
                cat2Title.setTextColor(resources.getColor(R.color.white))
                cat2Des.setTextColor(resources.getColor(R.color.white))


                //
                cat1Ll.background = resources.getDrawable(R.drawable.all_rounded_corners)
                cat1Title.setTextColor(resources.getColor(R.color.soft_black))
                cat1Des.setTextColor(resources.getColor(R.color.soft_black))
                cat3LL.background = resources.getDrawable(R.drawable.all_rounded_corners)
                cat3Title.setTextColor(resources.getColor(R.color.soft_black))
                cat3Des.setTextColor(resources.getColor(R.color.soft_black))
                category = "Category-2"
                findViewById<RelativeLayout>(R.id.select_time_rl).visibility = View.VISIBLE
                findViewById<TextView>(R.id.select_duration_TV).visibility = View.GONE
                findViewById<Spinner>(R.id.Duration_Spinner).visibility = View.GONE
            }
            cat3LL.setOnClickListener {

                // change this layout and its element's tint to royal blue and soft black and others to white and soft black
                cat3LL.background = resources.getDrawable(R.drawable.tinted_all_rounded_corners)
                cat3Title.setTextColor(resources.getColor(R.color.white))
                cat3Des.setTextColor(resources.getColor(R.color.white))


                //
                cat1Ll.background = resources.getDrawable(R.drawable.all_rounded_corners)
                cat1Title.setTextColor(resources.getColor(R.color.soft_black))
                cat1Des.setTextColor(resources.getColor(R.color.soft_black))
                cat2Ll.background = resources.getDrawable(R.drawable.all_rounded_corners)
                cat2Title.setTextColor(resources.getColor(R.color.soft_black))
                cat2Des.setTextColor(resources.getColor(R.color.soft_black))
                category = "Category-3"
                findViewById<RelativeLayout>(R.id.select_time_rl).visibility = View.GONE
                findViewById<TextView>(R.id.select_duration_TV).visibility = View.VISIBLE
                findViewById<Spinner>(R.id.Duration_Spinner).visibility = View.VISIBLE
            }
            val mokBtn = mShowTaskCategoryListDialog.findViewById<Button>(R.id.Ok_BTN)
            mokBtn.setOnClickListener {
                mSelectCategoryTv.text = category
                mShowTaskCategoryListDialog.dismiss()
            }
        }

        /* TASK DEADLINE SELECTION */

        /* TASK INIT() DEADLINE DATES */
//        initWeekLayout();


        /* Add Listeners and change the colors accordingly*/
        mDay1.setOnClickListener {
            // change background of selected layout to tinted background and change all other layouts to non-tinted background
            mDay1.background =
                resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn)
            mDay_day1.setTextColor(resources.getColor(R.color.white))
            mDay_date1.setTextColor(resources.getColor(R.color.white))
            selectedDateIndex = 0
            selectedDate = "${LocalDate.now().year}-${sdf.format(calendar.time.month+1)}-${mDay_date1.text}"
        }
        mDay2.setOnClickListener {

            // change background of selected layout to tinted background and change all other layouts to non-tinted background
            mDay2.background =
                resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn)
            mDay_day2.setTextColor(resources.getColor(R.color.white))
            mDay_date2.setTextColor(resources.getColor(R.color.white))
            selectedDateIndex = 1
            selectedDate = "${LocalDate.now().year}-${sdf.format(calendar.time.month+1)}-${mDay_date2.text}"
        }
        mDay3.setOnClickListener {

            // change background of selected layout to tinted background and change all other layouts to non-tinted background
            mDay3.background =
                resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn)
            mDay_day3.setTextColor(resources.getColor(R.color.white))
            mDay_date3.setTextColor(resources.getColor(R.color.white))
            selectedDateIndex = 2
            selectedDate = "${LocalDate.now().year}-${sdf.format(calendar.time.month+1)}-${mDay_date3.text}"
        }
        mDay4.setOnClickListener {

            // change background of selected layout to tinted background and change all other layouts to non-tinted background
            mDay4.background =
                resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn)
            mDay_day4.setTextColor(resources.getColor(R.color.white))
            mDay_date4.setTextColor(resources.getColor(R.color.white))
            selectedDateIndex = 3
            selectedDate = "${LocalDate.now().year}-${sdf.format(calendar.time.month+1)}-${mDay_date4.text}"
        }
        mDay5.setOnClickListener {

            // change background of selected layout to tinted background and change all other layouts to non-tinted background
            mDay5.background =
                resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn)
            mDay_day5.setTextColor(resources.getColor(R.color.white))
            mDay_date5.setTextColor(resources.getColor(R.color.white))
            selectedDateIndex = 4
            selectedDate = "${LocalDate.now().year}-${sdf.format(calendar.time.month+1)}-${mDay_date5.text}"
        }
        mDay6.setOnClickListener {

            // change background of selected layout to tinted background and change all other layouts to non-tinted background
            mDay6.background =
                resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn)
            mDay_day6.setTextColor(resources.getColor(R.color.white))
            mDay_date6.setTextColor(resources.getColor(R.color.white))
            selectedDateIndex = 5
            selectedDate = "${LocalDate.now().year}-${sdf.format(calendar.time.month+1)}-${mDay_date6.text}"
        }
        mDay7.setOnClickListener {

            // change background of selected layout to tinted background and change all other layouts to non-tinted background
            mDay7.background =
                resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn)
            mDay_day7.setTextColor(resources.getColor(R.color.white))
            mDay_date7.setTextColor(resources.getColor(R.color.white))
            selectedDateIndex = 6
            selectedDate = "${LocalDate.now().year}-${sdf.format(calendar.time.month+1)}-${mDay_date7.text}"
        }

        /* TASK TIME SELECTION */
        /* TASK START TIME SELECTION */
        lateinit var selectedStartTime:String
        mAssign_start_time_BTN.setOnClickListener {
            val onTimeSetListener =
                OnTimeSetListener { _: TimePicker?, selected_start_hour: Int, selected_startMinute: Int ->
                    startHours = selected_start_hour
                    startMinute = selected_startMinute
                    start_selected_time =
                        String.format(Locale.getDefault(), "%02d:%02d", startHours, startMinute)
                    selectedStartTime =
                        String.format(Locale.getDefault(), "%02d : %02d", startHours, startMinute)
                    mAssign_start_time_BTN.setText(selectedStartTime)
                    Toast.makeText(this@CreateTask, start_selected_time, Toast.LENGTH_SHORT).show()
                }
            val timePickerDialog =
                TimePickerDialog(this, onTimeSetListener, startHours, startMinute, true)
            timePickerDialog.setTitle("Select time")
            timePickerDialog.show()
        }

        /* TASK END TIME SELECTION */
        mAssign_end_time_BTN.setOnClickListener {
            val onTimeSetListener =
                OnTimeSetListener { _, selected_end_hour, selectedEndMinute ->
                    endHours = selected_end_hour
                    endMinute = selectedEndMinute
                    timeValidation(startHours, startMinute, endHours, endMinute)
//                    end_selected_time =
//                        String.format(Locale.getDefault(), "%02d:%02d", endHours, endMinute)
//                    Toast.makeText(this@CreateTask, end_selected_time, Toast.LENGTH_SHORT).show()
                }
            val timePickerDialog =
                TimePickerDialog(this, onTimeSetListener, endHours, endMinute, true)
            timePickerDialog.setTitle("Select time")
            timePickerDialog.show()
        }
    }

    override fun onPause() {
        super.onPause()
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun initWeekLayout() {
        val color=Color.parseColor("#20264246")
        val sdf=SimpleDateFormat("dd",Locale.UK)
        val calender:Calendar=Calendar.getInstance()
        calender.set(Calendar.WEEK_OF_YEAR,Calendar.getInstance().get(Calendar.WEEK_OF_YEAR))
        val fistDayOfWeek:Int = calender.firstDayOfWeek
        calender.set(Calendar.DAY_OF_WEEK,fistDayOfWeek)
        val startDate:String= sdf.format(calender.time)
        if(calender.time.date>startDate.toInt()&&(calender.time.date-startDate.toInt()>30||calender.time.date-startDate.toInt()>-30))
        {
            mDay1.background.setColorFilter(color,PorterDuff.Mode.MULTIPLY);
            mDay1.isEnabled=false
        }

        calender.set(Calendar.DAY_OF_WEEK,fistDayOfWeek+1)
        val date1:String= sdf.format(calender.time)
        if(calender.time.date>date1.toInt()&&(calender.time.date-date1.toInt()>30||(calender.time.date-date1.toInt())>-30))
        {
            mDay2.background.setColorFilter(color,PorterDuff.Mode.MULTIPLY);
            mDay2.isEnabled=false
        }

        calender.set(Calendar.DAY_OF_WEEK,fistDayOfWeek+2)
        val date2:String= sdf.format(calender.time)
        if(calender.time.date>date2.toInt()&&(calender.time.date-date2.toInt()>30||(calender.time.date-date2.toInt())>-30))
        {
            mDay3.background.setColorFilter(color,PorterDuff.Mode.MULTIPLY);
            mDay3.isEnabled=false
        }

        calender.set(Calendar.DAY_OF_WEEK,fistDayOfWeek + 3)
        val date3:String= sdf.format(calender.time)
        if(calender.time.date>date3.toInt()&&(calender.time.date-date3.toInt()>30||(calender.time.date-date3.toInt())>-30))
        {
            mDay4.background.setColorFilter(color,PorterDuff.Mode.MULTIPLY);
            mDay4.isEnabled=false
        }

        calender.set(Calendar.DAY_OF_WEEK,fistDayOfWeek + 4)
        val date4:String= sdf.format(calender.time)
        if(calender.time.date>date4.toInt()&&(calender.time.date-date4.toInt()>30||(calender.time.date-date4.toInt())>-30))
        {
            mDay5.background.setColorFilter(color,PorterDuff.Mode.MULTIPLY);
            mDay5.isEnabled=false
        }

        calender.set(Calendar.DAY_OF_WEEK,fistDayOfWeek + 5)
        val date5:String= sdf.format(calender.time)
        if(calender.time.date>date5.toInt()&&(calender.time.date-date5.toInt()>30||(calender.time.date-date5.toInt())>-30))
        {
            mDay6.background.setColorFilter(color,PorterDuff.Mode.MULTIPLY);
            mDay6.isEnabled=false
        }

        //EndDate
        calender.set(Calendar.DAY_OF_WEEK,fistDayOfWeek + 6)
        val endDate1:String=sdf.format(calender.time)
        if(calender.time.date>endDate1.toInt()&&(calender.time.date-endDate1.toInt()>30&&(calender.time.date-endDate1.toInt())>=-30))
        {
            Log.d("end date:",calender.time.toString())
            mDay7.background.setColorFilter(color,PorterDuff.Mode.MULTIPLY);
            mDay7.isEnabled=false
        }


        mDay_date1.text = startDate
        mDay_date2.text = date1
        mDay_date3.text = date2
        mDay_date4.text = date3
        mDay_date5.text = date4
        mDay_date6.text = date5
        mDay_date7.text = endDate1
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun backToDashboard(view: View) {
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun addTaskAndSchedule(view: View) {
        val title=binding.TitleET
        val description=binding.DescriptionET
        if(title.text.isEmpty()||title.text.startsWith(" ")) {
            title.error="Enter title for Task"
        }
        if(description.text.isEmpty()||description.text.startsWith(" ")) {
            description.error="Enter Description for Task"
        }
        else {
            startActivity(Intent(this,MainActivity::class.java))
            insertDataToDatabase()
            finish()
        }
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    fun insertDataToDatabase(){
        val timeFormatter = SimpleDateFormat("HH:mm")
        val title=binding.TitleET.text.toString()
        val description=binding.DescriptionET.text.toString()
        val currentDate = LocalDate.now().toString()
        val currentTime = timeFormatter.format(Calendar.getInstance().time)
        val priority=when(priority){
            "#1"->1
            "#2"->2
            "#3"->3
            "#4"->4
            else -> {5}
        }
        lateinit var task:TaskData
        when(category){
            "Category-1"->task=TaskData(
                taskId = MyApplication.createTaskId(createdTime =currentTime, createdDate = currentDate),
                title = title,priority=priority, category=category, startDate = selectedDate,
                deadlineDate = selectedDate, startTime = start_selected_time, endTime = end_selected_time,
                description = description, status = "pending", duration = 0,
                weekNumber = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)-1
            )
            else->task=TaskData(
                taskId = MyApplication.createTaskId(createdTime =currentTime, createdDate = currentDate),
                title = title,priority=priority, category=category, startDate = currentDate,
                deadlineDate = selectedDate, startTime = start_selected_time, endTime = end_selected_time,
                description = description, status = "pending", duration = 0,
                weekNumber = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)-1
            )
        }

        mTaskViewModel.addTask(task)
        Toast.makeText(this,"Successfully added!",Toast.LENGTH_SHORT).show()
    }
//    fun dateValidation()
//    {

//        val calender1:Calendar=Calendar.getInstance()
//
//        val fistDayOfWeek:Int = calender.firstDayOfWeek
//        calender.set(Calendar.DAY_OF_WEEK,fistDayOfWeek)
//
//
//
//
//    }

    private fun timeValidation(startHours:Int, startMinute:Int, endHours:Int, endMinute:Int)
    {
        val duration:Int=startMinute-endMinute
        if(startHours<endHours && duration < 45) {
            mAssign_start_time_BTN.text = start_selected_time
            mAssign_end_time_BTN.text =
                String.format(Locale.getDefault(), "%02d : %02d", endHours, endMinute)
            end_selected_time=String.format(Locale.getDefault(), "%02d:%02d", endHours, endMinute)
        } else if(startHours==endHours && (endMinute>(15+startMinute)||(endMinute-startMinute)>15)) {
            mAssign_start_time_BTN.text = start_selected_time
            mAssign_end_time_BTN.text =
                String.format(Locale.getDefault(), "%02d : %02d", endHours, endMinute)
            end_selected_time=String.format(Locale.getDefault(), "%02d:%02d", endHours, endMinute)
        } else {
            if(startHours>endHours) {
                Toast.makeText(this,"Invalid End-time",Toast.LENGTH_SHORT).show()
                mAssign_end_time_BTN.text = String.format(Locale.getDefault(), "%02d : %02d", 0, 0)
            }
            if(endMinute<(startMinute+15)||(endMinute-startMinute)<15) {
                Toast.makeText(this,"Duration must be more than 15 minutes",Toast.LENGTH_SHORT).show()
                mAssign_end_time_BTN.text = String.format(Locale.getDefault(), "%02d : %02d", 0, 0)
            }
        }
    }
}


