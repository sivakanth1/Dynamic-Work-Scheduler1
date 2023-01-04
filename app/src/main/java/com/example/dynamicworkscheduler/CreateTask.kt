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
class CreateTask : AppCompatActivity(),AdapterView.OnItemSelectedListener{
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
    private lateinit var mShowSameTaskError:Dialog
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
    var duration = 0
    var weekListDates = mutableListOf<String>()
    var weekListBoolean = mutableListOf<Boolean>()
    @SuppressLint("UseCompatLoadingForDrawables", "CutPasteId")
    @RequiresApi(api = Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCreateTaskBinding.inflate(layoutInflater)
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
        mShowSameTaskError = Dialog(this@CreateTask)
        mShowTaskCategoryListDialog.setContentView(R.layout.task_category_selection_dialog)
        mShowSameTaskError.setContentView(R.layout.same_task_error)
        mShowTaskCategoryListDialog.window!!.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        mShowSameTaskError.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        mShowTaskCategoryListDialog.window!!.setBackgroundDrawable(getDrawable(R.drawable.all_rounded_corners_big))
        mShowSameTaskError.window!!.setBackgroundDrawable(getDrawable(R.drawable.all_rounded_corners_big))

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
        mDurationSpinner.onItemSelectedListener =this

        /* TASK DEADLINE SELECTION */

        /* TASK INIT() DEADLINE DATES */
//        initWeekLayout();


        /* Add Listeners and change the colors accordingly*/
//        mDay1.setOnClickListener {
//            // change background of selected layout to tinted background and change all other layouts to non-tinted background
//            mDay1.background =
//                resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn)
//            mDay_day1.setTextColor(resources.getColor(R.color.white))
//            mDay_date1.setTextColor(resources.getColor(R.color.white))
//            selectedDateIndex = 0
//            selectedDate = "${LocalDate.now().year}-${sdf.format(calendar.time.month+1)}-${mDay_date1.text}"
//        }
//        mDay2.setOnClickListener {
//
//            // change background of selected layout to tinted background and change all other layouts to non-tinted background
//            mDay2.background =
//                resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn)
//            mDay_day2.setTextColor(resources.getColor(R.color.white))
//            mDay_date2.setTextColor(resources.getColor(R.color.white))
//            selectedDateIndex = 1
//            selectedDate = "${LocalDate.now().year}-${sdf.format(calendar.time.month+1)}-${mDay_date2.text}"
//        }
//        mDay3.setOnClickListener {
//
//            // change background of selected layout to tinted background and change all other layouts to non-tinted background
//            mDay3.background =
//                resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn)
//            mDay_day3.setTextColor(resources.getColor(R.color.white))
//            mDay_date3.setTextColor(resources.getColor(R.color.white))
//            selectedDateIndex = 2
//            selectedDate = "${LocalDate.now().year}-${sdf.format(calendar.time.month+1)}-${mDay_date3.text}"
//        }
//        mDay4.setOnClickListener {
//
//            // change background of selected layout to tinted background and change all other layouts to non-tinted background
//            mDay4.background =
//                resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn)
//            mDay_day4.setTextColor(resources.getColor(R.color.white))
//            mDay_date4.setTextColor(resources.getColor(R.color.white))
//            selectedDateIndex = 3
//            selectedDate = "${LocalDate.now().year}-${sdf.format(calendar.time.month+1)}-${mDay_date4.text}"
//        }
//        mDay5.setOnClickListener {
//
//            // change background of selected layout to tinted background and change all other layouts to non-tinted background
//            mDay5.background =
//                resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn)
//            mDay_day5.setTextColor(resources.getColor(R.color.white))
//            mDay_date5.setTextColor(resources.getColor(R.color.white))
//            selectedDateIndex = 4
//            selectedDate = "${LocalDate.now().year}-${sdf.format(calendar.time.month+1)}-${mDay_date5.text}"
//        }
//        mDay6.setOnClickListener {
//
//            // change background of selected layout to tinted background and change all other layouts to non-tinted background
//            mDay6.background =
//                resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn)
//            mDay_day6.setTextColor(resources.getColor(R.color.white))
//            mDay_date6.setTextColor(resources.getColor(R.color.white))
//            selectedDateIndex = 5
//            selectedDate = "${LocalDate.now().year}-${sdf.format(calendar.time.month+1)}-${mDay_date6.text}"
//        }
//        mDay7.setOnClickListener {
//
//            // change background of selected layout to tinted background and change all other layouts to non-tinted background
//            mDay7.background =
//                resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn)
//            mDay_day7.setTextColor(resources.getColor(R.color.white))
//            mDay_date7.setTextColor(resources.getColor(R.color.white))
//            selectedDateIndex = 6
//            selectedDate = "${LocalDate.now().year}-${sdf.format(calendar.time.month+1)}-${mDay_date7.text}"
//        }

        /* TASK TIME SELECTION */
        /* TASK START TIME SELECTION */
        lateinit var selectedStartTime:String
        mAssign_start_time_BTN.setOnClickListener {
            val onTimeSetListener =
                OnTimeSetListener { _: TimePicker?, selected_start_hour: Int, selected_startMinute: Int ->
                    startHours = selected_start_hour
                    startMinute = selected_startMinute
                    startTaskTimeValidation(startHours,startMinute,selectedDate)
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


    @SuppressLint("SetTextI18n", "SimpleDateFormat", "UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun initWeekLayout()
    {
        val color = Color.parseColor("#20264246")
        val sdf = SimpleDateFormat("dd", Locale.getDefault())
        val calender: Calendar = Calendar.getInstance()
        //calender.set(Calendar.WEEK_OF_YEAR,Calendar.getInstance().get(Calendar.WEEK_OF_YEAR))
        val fistDayOfWeek: Int = calender.firstDayOfWeek
        calender.set(Calendar.DAY_OF_WEEK, fistDayOfWeek)
        var startDate: String = sdf.format(calender.time.date)
        Log.d("date=", calender.time.date.toString())
        Log.d("week=", calender.firstDayOfWeek.toString())
        Log.d("date=", startDate)
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
            Log.d("WeekDatesString", weekListDates.get(idx))
            idx += 1
        }

        var isPrevDay = true
        var today = Calendar.getInstance()
        var todayString: String = sdf.format(today.time)
//        Toast.makeText(this, "$todayString", Toast.LENGTH_SHORT).show()


        Log.d("WeekDatesToday", "$todayString")
        for (i in weekListDates) {
            if (todayString == i) {
                isPrevDay = false;
            }

            weekListBoolean.add(isPrevDay)
        }

        for (i in weekListBoolean) Log.d("WeekDatesBoolean", i.toString())

        mDay_date1.setText(weekListDates[0])
        mDay_date2.setText(weekListDates[1])
        mDay_date3.setText(weekListDates[2])
        mDay_date4.setText(weekListDates[3])
        mDay_date5.setText(weekListDates[4])
        mDay_date6.setText(weekListDates[5])
        mDay_date7.setText(weekListDates[6])


        mDay1.background =
            if (weekListBoolean[0]) resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn) else resources.getDrawable(
                R.drawable.all_rounded_corners_small_btn
            )
        mDay2.background =
            if (weekListBoolean[1]) resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn) else resources.getDrawable(
                R.drawable.all_rounded_corners_small_btn
            )
        mDay3.background =
            if (weekListBoolean[2]) resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn) else resources.getDrawable(
                R.drawable.all_rounded_corners_small_btn
            )
        mDay4.background =
            if (weekListBoolean[3]) resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn) else resources.getDrawable(
                R.drawable.all_rounded_corners_small_btn
            )
        mDay5.background =
            if (weekListBoolean[4]) resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn) else resources.getDrawable(
                R.drawable.all_rounded_corners_small_btn
            )
        mDay6.background =
            if (weekListBoolean[5]) resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn) else resources.getDrawable(
                R.drawable.all_rounded_corners_small_btn
            )
        mDay7.background =
            if (weekListBoolean[6]) resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn) else resources.getDrawable(
                R.drawable.all_rounded_corners_small_btn
            )

        mDay1.setOnClickListener {

            if (weekListBoolean[0])
                mDay1.background =
                    resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
            else {
                mDay1.background =
                    resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn)
                mDay_day1.setTextColor(resources.getColor(R.color.white))
                mDay_date1.setTextColor(resources.getColor(R.color.white))

                if (weekListBoolean[1]) {
                    mDay2.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date2.setTextColor(resources.getColor(R.color.black))
                    mDay_day2.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay2.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day2.setTextColor(resources.getColor(R.color.black))
                    mDay_date2.setTextColor(resources.getColor(R.color.black))
                }

                if (weekListBoolean[2]) {
                    mDay3.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date3.setTextColor(resources.getColor(R.color.black))
                    mDay_day3.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay3.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day3.setTextColor(resources.getColor(R.color.black))
                    mDay_date3.setTextColor(resources.getColor(R.color.black))
                }

                if (weekListBoolean[3]) {
                    mDay4.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date4.setTextColor(resources.getColor(R.color.black))
                    mDay_day4.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay4.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day4.setTextColor(resources.getColor(R.color.black))
                    mDay_date4.setTextColor(resources.getColor(R.color.black))
                }

                if (weekListBoolean[4]) {
                    mDay5.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date5.setTextColor(resources.getColor(R.color.black))
                    mDay_day5.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay5.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day5.setTextColor(resources.getColor(R.color.black))
                    mDay_date5.setTextColor(resources.getColor(R.color.black))
                }

                if (weekListBoolean[5]) {
                    mDay6.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date6.setTextColor(resources.getColor(R.color.black))
                    mDay_day6.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay6.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day6.setTextColor(resources.getColor(R.color.black))
                    mDay_date6.setTextColor(resources.getColor(R.color.black))
                }

                if (weekListBoolean[6]) {
                    mDay7.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date7.setTextColor(resources.getColor(R.color.black))
                    mDay_day7.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay7.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day7.setTextColor(resources.getColor(R.color.black))
                    mDay_date7.setTextColor(resources.getColor(R.color.black))
                }
//                Toast.makeText(this, "Day1", Toast.LENGTH_SHORT).show()
            }
            if(!weekListBoolean[0]) {
                selectedDate =
                    "${LocalDate.now().year}-${sdf.format(calendar.time.month + 1)}-${mDay_date1.text}"
            }else{
                Toast.makeText(this,"Invalid Date",Toast.LENGTH_SHORT).show()
//                Toast.makeText(this, "Day1", Toast.LENGTH_SHORT).show(
            }
        }

        mDay2.setOnClickListener {

            if (weekListBoolean[1])
                mDay2.background =
                    resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
            else {
                mDay2.background =
                    resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn)
                mDay_day2.setTextColor(resources.getColor(R.color.white))
                mDay_date2.setTextColor(resources.getColor(R.color.white))

                if (weekListBoolean[0]) {
                    mDay1.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date1.setTextColor(resources.getColor(R.color.black))
                    mDay_day1.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay1.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day1.setTextColor(resources.getColor(R.color.black))
                    mDay_date1.setTextColor(resources.getColor(R.color.black))
                }

                if (weekListBoolean[2]) {
                    mDay3.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date3.setTextColor(resources.getColor(R.color.black))
                    mDay_day3.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay3.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day3.setTextColor(resources.getColor(R.color.black))
                    mDay_date3.setTextColor(resources.getColor(R.color.black))
                }

                if (weekListBoolean[3]) {
                    mDay4.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date4.setTextColor(resources.getColor(R.color.black))
                    mDay_day4.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay4.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day4.setTextColor(resources.getColor(R.color.black))
                    mDay_date4.setTextColor(resources.getColor(R.color.black))
                }

                if (weekListBoolean[4]) {
                    mDay5.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date5.setTextColor(resources.getColor(R.color.black))
                    mDay_day5.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay5.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day5.setTextColor(resources.getColor(R.color.black))
                    mDay_date5.setTextColor(resources.getColor(R.color.black))
                }

                if (weekListBoolean[5]) {
                    mDay6.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date6.setTextColor(resources.getColor(R.color.black))
                    mDay_day6.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay6.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day6.setTextColor(resources.getColor(R.color.black))
                    mDay_date6.setTextColor(resources.getColor(R.color.black))
                }

                if (weekListBoolean[6]) {
                    mDay7.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date7.setTextColor(resources.getColor(R.color.black))
                    mDay_day7.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay7.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day7.setTextColor(resources.getColor(R.color.black))
                    mDay_date7.setTextColor(resources.getColor(R.color.black))
                }

            }
            if(!weekListBoolean[1]) {
                selectedDate =
                    "${LocalDate.now().year}-${sdf.format(calendar.time.month + 1)}-${mDay_date2.text}"
            }else{
                Toast.makeText(this,"Invalid Date",Toast.LENGTH_SHORT).show()
//                Toast.makeText(this, "Day1", Toast.LENGTH_SHORT).show(
            }
        }

        mDay3.setOnClickListener {

            if (weekListBoolean[2])
                mDay3.background =
                    resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
            else {
                mDay3.background =
                    resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn)
                mDay_day3.setTextColor(resources.getColor(R.color.white))
                mDay_date3.setTextColor(resources.getColor(R.color.white))

                if (weekListBoolean[0]) {
                    mDay1.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date1.setTextColor(resources.getColor(R.color.black))
                    mDay_day1.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay1.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day1.setTextColor(resources.getColor(R.color.black))
                    mDay_date1.setTextColor(resources.getColor(R.color.black))
                }

                if (weekListBoolean[1]) {
                    mDay2.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date2.setTextColor(resources.getColor(R.color.black))
                    mDay_day2.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay2.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day2.setTextColor(resources.getColor(R.color.black))
                    mDay_date2.setTextColor(resources.getColor(R.color.black))
                }

                if (weekListBoolean[3]) {
                    mDay4.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date4.setTextColor(resources.getColor(R.color.black))
                    mDay_day4.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay4.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day4.setTextColor(resources.getColor(R.color.black))
                    mDay_date4.setTextColor(resources.getColor(R.color.black))
                }

                if (weekListBoolean[4]) {
                    mDay5.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date5.setTextColor(resources.getColor(R.color.black))
                    mDay_day5.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay5.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day5.setTextColor(resources.getColor(R.color.black))
                    mDay_date5.setTextColor(resources.getColor(R.color.black))
                }

                if (weekListBoolean[5]) {
                    mDay6.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date6.setTextColor(resources.getColor(R.color.black))
                    mDay_day6.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay6.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day6.setTextColor(resources.getColor(R.color.black))
                    mDay_date6.setTextColor(resources.getColor(R.color.black))
                }

                if (weekListBoolean[6]) {
                    mDay7.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date7.setTextColor(resources.getColor(R.color.black))
                    mDay_day7.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay7.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day7.setTextColor(resources.getColor(R.color.black))
                    mDay_date7.setTextColor(resources.getColor(R.color.black))
                }


            }
            if(!weekListBoolean[2]) {
                selectedDate =
                    "${LocalDate.now().year}-${sdf.format(calendar.time.month + 1)}-${mDay_date3.text}"
            }else{
                Toast.makeText(this,"Invalid Date",Toast.LENGTH_SHORT).show()
//                Toast.makeText(this, "Day1", Toast.LENGTH_SHORT).show(
            }
        }

        mDay4.setOnClickListener {

            if (weekListBoolean[3])
                mDay4.background =
                    resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
            else {
                mDay4.background =
                    resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn)
                mDay_day4.setTextColor(resources.getColor(R.color.white))
                mDay_date4.setTextColor(resources.getColor(R.color.white))

                if (weekListBoolean[0]) {
                    mDay1.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date1.setTextColor(resources.getColor(R.color.black))
                    mDay_day1.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay1.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day1.setTextColor(resources.getColor(R.color.black))
                    mDay_date1.setTextColor(resources.getColor(R.color.black))
                }

                if (weekListBoolean[1]) {
                    mDay2.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date2.setTextColor(resources.getColor(R.color.black))
                    mDay_day2.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay2.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day2.setTextColor(resources.getColor(R.color.black))
                    mDay_date2.setTextColor(resources.getColor(R.color.black))
                }

                if (weekListBoolean[2]) {
                    mDay3.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date3.setTextColor(resources.getColor(R.color.black))
                    mDay_day3.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay3.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day3.setTextColor(resources.getColor(R.color.black))
                    mDay_date3.setTextColor(resources.getColor(R.color.black))
                }

                if (weekListBoolean[4]) {
                    mDay5.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date5.setTextColor(resources.getColor(R.color.black))
                    mDay_day5.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay5.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day5.setTextColor(resources.getColor(R.color.black))
                    mDay_date5.setTextColor(resources.getColor(R.color.black))
                }

                if (weekListBoolean[5]) {
                    mDay6.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date6.setTextColor(resources.getColor(R.color.black))
                    mDay_day6.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay6.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day6.setTextColor(resources.getColor(R.color.black))
                    mDay_date6.setTextColor(resources.getColor(R.color.black))
                }

                if (weekListBoolean[6]) {
                    mDay7.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date7.setTextColor(resources.getColor(R.color.black))
                    mDay_day7.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay7.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day7.setTextColor(resources.getColor(R.color.black))
                    mDay_date7.setTextColor(resources.getColor(R.color.black))
                }


            }
            if(!weekListBoolean[3]) {
                selectedDate =
                    "${LocalDate.now().year}-${sdf.format(calendar.time.month + 1)}-${mDay_date4.text}"
            }else{
                Toast.makeText(this,"Invalid Date",Toast.LENGTH_SHORT).show()
//                Toast.makeText(this, "Day1", Toast.LENGTH_SHORT).show(
            }
//                Toast.makeText(this, "Day1", Toast.LENGTH_SHORT).show()
        }

        mDay5.setOnClickListener {

            if (weekListBoolean[4])
                mDay5.background =
                    resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
            else {
                mDay5.background =
                    resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn)
                mDay_day5.setTextColor(resources.getColor(R.color.white))
                mDay_date5.setTextColor(resources.getColor(R.color.white))

                if (weekListBoolean[0]) {
                    mDay1.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date1.setTextColor(resources.getColor(R.color.black))
                    mDay_day1.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay1.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day1.setTextColor(resources.getColor(R.color.black))
                    mDay_date1.setTextColor(resources.getColor(R.color.black))
                }

                if (weekListBoolean[1]) {
                    mDay2.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date2.setTextColor(resources.getColor(R.color.black))
                    mDay_day2.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay2.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day2.setTextColor(resources.getColor(R.color.black))
                    mDay_date2.setTextColor(resources.getColor(R.color.black))
                }

                if (weekListBoolean[2]) {
                    mDay3.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date3.setTextColor(resources.getColor(R.color.black))
                    mDay_day3.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay3.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day3.setTextColor(resources.getColor(R.color.black))
                    mDay_date3.setTextColor(resources.getColor(R.color.black))
                }

                if (weekListBoolean[3]) {
                    mDay4.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date4.setTextColor(resources.getColor(R.color.black))
                    mDay_day4.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay4.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day4.setTextColor(resources.getColor(R.color.black))
                    mDay_date4.setTextColor(resources.getColor(R.color.black))
                }

                if (weekListBoolean[5]) {
                    mDay6.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date6.setTextColor(resources.getColor(R.color.black))
                    mDay_day6.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay6.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day6.setTextColor(resources.getColor(R.color.black))
                    mDay_date6.setTextColor(resources.getColor(R.color.black))
                }

                if (weekListBoolean[6]) {
                    mDay7.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date7.setTextColor(resources.getColor(R.color.black))
                    mDay_day7.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay7.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day7.setTextColor(resources.getColor(R.color.black))
                    mDay_date7.setTextColor(resources.getColor(R.color.black))
                }
            }
            if(!weekListBoolean[4]) {
                selectedDate =
                    "${LocalDate.now().year}-${sdf.format(calendar.time.month + 1)}-${mDay_date5.text}"
            }else{
                Toast.makeText(this,"Invalid Date",Toast.LENGTH_SHORT).show()
//                Toast.makeText(this, "Day1", Toast.LENGTH_SHORT).show(
            }
        }

        mDay6.setOnClickListener {

            if (weekListBoolean[5])
                mDay6.background =
                    resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
            else {
                mDay6.background =
                    resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn)
                mDay_day6.setTextColor(resources.getColor(R.color.white))
                mDay_date6.setTextColor(resources.getColor(R.color.white))

                if (weekListBoolean[0]) {
                    mDay1.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date1.setTextColor(resources.getColor(R.color.black))
                    mDay_day1.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay1.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day1.setTextColor(resources.getColor(R.color.black))
                    mDay_date1.setTextColor(resources.getColor(R.color.black))
                }

                if (weekListBoolean[1]) {
                    mDay2.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date2.setTextColor(resources.getColor(R.color.black))
                    mDay_day2.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay2.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day2.setTextColor(resources.getColor(R.color.black))
                    mDay_date2.setTextColor(resources.getColor(R.color.black))
                }

                if (weekListBoolean[2]) {
                    mDay3.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date3.setTextColor(resources.getColor(R.color.black))
                    mDay_day3.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay3.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day3.setTextColor(resources.getColor(R.color.black))
                    mDay_date3.setTextColor(resources.getColor(R.color.black))
                }

                if (weekListBoolean[3]) {
                    mDay4.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date4.setTextColor(resources.getColor(R.color.black))
                    mDay_day4.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay4.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day4.setTextColor(resources.getColor(R.color.black))
                    mDay_date4.setTextColor(resources.getColor(R.color.black))
                }

                if (weekListBoolean[4]) {
                    mDay5.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date5.setTextColor(resources.getColor(R.color.black))
                    mDay_day5.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay5.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day5.setTextColor(resources.getColor(R.color.black))
                    mDay_date5.setTextColor(resources.getColor(R.color.black))
                }

                if (weekListBoolean[6]) {
                    mDay7.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date7.setTextColor(resources.getColor(R.color.black))
                    mDay_day7.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay7.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day7.setTextColor(resources.getColor(R.color.black))
                    mDay_date7.setTextColor(resources.getColor(R.color.black))
                }

            }
//
        //               Toast.makeText(this, "Day1", Toast.LENGTH_SHORT).show()
            if(!weekListBoolean[5]) {
                selectedDate =
                    "${LocalDate.now().year}-${sdf.format(calendar.time.month + 1)}-${mDay_date6.text}"
            }else{
                Toast.makeText(this,"Invalid Date",Toast.LENGTH_SHORT).show()
//                Toast.makeText(this, "Day1", Toast.LENGTH_SHORT).show(
            }
        }

        mDay7.setOnClickListener {

            if (weekListBoolean[6])
                mDay7.background =
                    resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
            else {
                mDay7.background =
                    resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn)
                mDay_day7.setTextColor(resources.getColor(R.color.white))
                mDay_date7.setTextColor(resources.getColor(R.color.white))

                if (weekListBoolean[0]) {
                    mDay1.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date1.setTextColor(resources.getColor(R.color.black))
                    mDay_day1.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay1.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day1.setTextColor(resources.getColor(R.color.black))
                    mDay_date1.setTextColor(resources.getColor(R.color.black))
                }

                if (weekListBoolean[1]) {
                    mDay2.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date2.setTextColor(resources.getColor(R.color.black))
                    mDay_day2.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay2.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day2.setTextColor(resources.getColor(R.color.black))
                    mDay_date2.setTextColor(resources.getColor(R.color.black))
                }

                if (weekListBoolean[2]) {
                    mDay3.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date3.setTextColor(resources.getColor(R.color.black))
                    mDay_day3.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay3.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day3.setTextColor(resources.getColor(R.color.black))
                    mDay_date3.setTextColor(resources.getColor(R.color.black))
                }

                if (weekListBoolean[3]) {
                    mDay4.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date4.setTextColor(resources.getColor(R.color.black))
                    mDay_day4.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay4.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day4.setTextColor(resources.getColor(R.color.black))
                    mDay_date4.setTextColor(resources.getColor(R.color.black))
                }

                if (weekListBoolean[4]) {
                    mDay5.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date5.setTextColor(resources.getColor(R.color.black))
                    mDay_day5.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay5.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day5.setTextColor(resources.getColor(R.color.black))
                    mDay_date5.setTextColor(resources.getColor(R.color.black))
                }

                if (weekListBoolean[5]) {
                    mDay7.background =
                        resources.getDrawable(R.drawable.default_all_rounded_corners_small_btn)
                    mDay_date7.setTextColor(resources.getColor(R.color.black))
                    mDay_day7.setTextColor(resources.getColor(R.color.black))
                } else {
                    mDay6.background =
                        resources.getDrawable(R.drawable.all_rounded_corners_small_btn)
                    mDay_day6.setTextColor(resources.getColor(R.color.black))
                    mDay_date6.setTextColor(resources.getColor(R.color.black))
                }
            }
            if(!weekListBoolean[6]) {
                selectedDate =
                    "${LocalDate.now().year}-${sdf.format(calendar.time.month + 1)}-${mDay_date7.text}"
            }else{
                Toast.makeText(this,"Invalid Date",Toast.LENGTH_SHORT).show()
//                Toast.makeText(this, "Day1", Toast.LENGTH_SHORT).show(
            }
        }

    }



    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Toast.makeText(this,parent?.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show()
        when(parent?.getItemAtPosition(position).toString()){
            "< 30 mins" -> duration = 30
            "< 1hr" -> duration = 60
            "1hrs - 2 hrs" -> duration = 120
            "2hrs - 3hrs" -> duration = 180
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun backToDashboard(view: View) {
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//        startActivity(Intent(this,MainActivity::class.java))
//        finish()
//    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun addTaskAndSchedule(view: View) {
        val title=binding.TitleET
        val description=binding.DescriptionET
        var priorityval=binding.AddPriorityTV
        val selctcategory=binding.SelectCategoryTV
        val starttimebtnval=binding.AssignStartTimeBTN
        val endtimebtnval=binding.AssignEndTimeBTN
        if(title.text.isEmpty()||title.text.startsWith(" ")) {
            title.error="Enter title for Task"
        }
        if(priorityval.text=="+")
        {
            Toast.makeText(this,"select priority for the task",Toast.LENGTH_SHORT).show()
        }
        if(description.text.isEmpty()||description.text.startsWith(" ")) {
            description.error="Enter Description for Task"
        }

        if(selctcategory.text=="Select Category of Task")
        {
            Toast.makeText(this,"select task category",Toast.LENGTH_SHORT).show()
        }
        if(starttimebtnval.text=="00:00")
        {
            Toast.makeText(this,"select start time",Toast.LENGTH_SHORT).show()
        }
        if(endtimebtnval.text=="00:00")
        {
            Toast.makeText(this,"select end time",Toast.LENGTH_SHORT).show()
        }

        else {
            mShowSameTaskError.show()
            mShowSameTaskError.window?.findViewById<ImageView>(R.id.close_dialog)
                ?.setOnClickListener{
                    mShowSameTaskError.dismiss()
                }
            mShowSameTaskError.window?.findViewById<Button>(R.id.sameTaskErrorOkBtn)?.setOnClickListener{
                mShowSameTaskError.dismiss()
            }
//            startActivity(Intent(this,MainActivity::class.java))
            insertDataToDatabase()
//            finish()
        }

    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n", "UseCompatLoadingForDrawable")
    @RequiresApi(Build.VERSION_CODES.O)
    fun insertDataToDatabase()
    {
        val timeFormatter = SimpleDateFormat("HH:mm")
        val title=binding.TitleET.text.toString()
        val description=binding.DescriptionET.text.toString()
        val currentDate = LocalDate.now().toString()
        val date_time_formatter = SimpleDateFormat("yyyy-MM-dd")
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
            "Category-1"-> {
                val collidedTask=MyApplication.checkCollide(startDate = date_time_formatter.parse(selectedDate),
                    deadlineDate = date_time_formatter.parse(selectedDate),
                    startTime = start_selected_time,end_selected_time,category=category
                )
                if(collidedTask.taskID!=null){
                    mShowSameTaskError.window?.findViewById<TextView>(R.id.Assign_priority_TV)!!.text = "#${collidedTask.priority}"
                    mShowSameTaskError.window?.findViewById<TextView>(R.id.Assign_title_TV)!!.text = collidedTask.title
                    mShowSameTaskError.window?.findViewById<TextView>(R.id.Assign_deadline_TV)!!.text = date_time_formatter.format(collidedTask.deadlineDate)
                    mShowSameTaskError.window?.findViewById<TextView>(R.id.Assign_start_time_TV)!!.text = collidedTask.startTime
                    mShowSameTaskError.window?.findViewById<TextView>(R.id.Assign_end_time_TV)!!.text = collidedTask.endTime
                    mShowSameTaskError.window?.findViewById<TextView>(R.id.Assign_category_TV)!!.text = collidedTask.category

                }else {
                    mShowSameTaskError.dismiss()
                    task = TaskData(
                        taskId = MyApplication.createTaskId(
                            createdTime = currentTime,
                            createdDate = currentDate
                        ),
                        title = title,
                        priority = priority,
                        category = category,
                        startDate = selectedDate,
                        deadlineDate = selectedDate,
                        startTime = start_selected_time,
                        endTime = end_selected_time,
                        description = description,
                        status = "pending",
                        duration = 0,
                        weekNumber = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR) - 1
                    )
                    mTaskViewModel.addTask(task)
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                    Toast.makeText(this,"Successfully added!",Toast.LENGTH_SHORT).show()
                }
            }
            else-> {
                start_selected_time = "00:00"
                end_selected_time = "00:00"
                val collidedTask=MyApplication.checkCollide(startDate = date_time_formatter.parse(selectedDate),
                    deadlineDate = date_time_formatter.parse(selectedDate),
                    startTime = start_selected_time,end_selected_time,category=category
                )
                if(collidedTask.taskID!=null){
                    mShowSameTaskError.window?.findViewById<TextView>(R.id.Assign_priority_TV)!!.text = "#${collidedTask.priority}"
                    mShowSameTaskError.window?.findViewById<TextView>(R.id.Assign_title_TV)!!.text = collidedTask.title
                    mShowSameTaskError.window?.findViewById<TextView>(R.id.Assign_deadline_TV)!!.text = date_time_formatter.format(collidedTask.deadlineDate)
                    mShowSameTaskError.window?.findViewById<TextView>(R.id.Assign_start_time_TV)!!.text = collidedTask.startTime
                    mShowSameTaskError.window?.findViewById<TextView>(R.id.Assign_end_time_TV)!!.text = collidedTask.endTime
                    mShowSameTaskError.window?.findViewById<TextView>(R.id.Assign_category_TV)!!.text = collidedTask.category

                }else {
                    mShowSameTaskError.dismiss()
                    task = TaskData(
                        taskId = MyApplication.createTaskId(
                            createdTime = currentTime,
                            createdDate = currentDate
                        ),
                        title = title,
                        priority = priority,
                        category = category,
                        startDate = currentDate,
                        deadlineDate = selectedDate,
                        startTime = start_selected_time,
                        endTime = end_selected_time,
                        description = description,
                        status = "pending",
                        duration = duration,
                        weekNumber = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR) - 1
                    )
                    mTaskViewModel.addTask(task)
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                    Toast.makeText(this,"Successfully added!",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun timeValidation(startHours:Int, startMinute:Int, endHours:Int, endMinute:Int) {
        val duration:Int=startMinute-endMinute
        if((endHours<=23&& endHours!=0) && (startHours<=23 && startHours!=0)){
            if (startHours < endHours && duration < 45) {
                mAssign_start_time_BTN.text = start_selected_time
                mAssign_end_time_BTN.text =
                    String.format(Locale.getDefault(), "%02d : %02d", endHours, endMinute)
                end_selected_time =
                    String.format(Locale.getDefault(), "%02d : %02d", endHours, endMinute)
            } else if (startHours == endHours && (endMinute >=(15 + startMinute))) {
                mAssign_start_time_BTN.text = start_selected_time
                mAssign_end_time_BTN.text =
                    String.format(Locale.getDefault(), "%02d : %02d", endHours, endMinute)
                end_selected_time =
                    String.format(Locale.getDefault(), "%02d:%02d", endHours, endMinute)
            } else {
                if (startHours > endHours) {
                    Toast.makeText(this, "Invalid End-time", Toast.LENGTH_SHORT).show()
                    mAssign_end_time_BTN.text =
                        String.format(Locale.getDefault(), "%02d : %02d", 0, 0)
                }
                if (endMinute < (startMinute + 15)) {
                    Toast.makeText(
                        this,
                        "Duration must be more than 15 minutes",
                        Toast.LENGTH_SHORT
                    ).show()
                    mAssign_end_time_BTN.text =
                        String.format(Locale.getDefault(), "%02d : %02d", 0, 0)
                }
            }
        } else {
            Toast.makeText(this, "End time or start time should not be greater than your working hours", Toast.LENGTH_SHORT).show()
            mAssign_end_time_BTN.text = String.format(Locale.getDefault(), "%02d : %02d", 0, 0)
            mAssign_start_time_BTN.text = String.format(Locale.getDefault(), "%02d : %02d", 0, 0)

        }
    }

    private fun startTaskTimeValidation(startHours: Int,startMinute: Int,deadlineDate:String) {	  val formatter=SimpleDateFormat("yyyy-MM-dd")
        val endDate=formatter.parse(deadlineDate)
        val calendar=Calendar.getInstance()
        val currentTimeHour=calendar.time.hours
        val currentTimeMinute=calendar.time.minutes
        if(calendar.time.date==endDate.date) {
            if(startHours in 9..23) {

                if(startHours==currentTimeHour&&startMinute >= (currentTimeMinute+5))
                {
                    val startSelectedTime=String.format(Locale.getDefault(), "%02d : %02d", startHours, startMinute)
                    mAssign_start_time_BTN.text = startSelectedTime
                    start_selected_time = String.format(Locale.getDefault(), "%02d:%02d", startHours, startMinute)
                }
                else if(startHours>currentTimeHour && (currentTimeMinute-startMinute)<=55){
                    val startSelectedTime=String.format(Locale.getDefault(), "%02d : %02d", startHours, startMinute)
                    mAssign_start_time_BTN.text = startSelectedTime
                    start_selected_time = String.format(Locale.getDefault(), "%02d:%02d", startHours, startMinute)
                }
              //  else if(if )
                else{
                    Toast.makeText(this,"start time should be 5 min more than current time",Toast.LENGTH_SHORT).show()
                    mAssign_start_time_BTN.text = String.format(Locale.getDefault(), "%02d : %02d", 0, 0)
                    mAssign_end_time_BTN.text = String.format(Locale.getDefault(), "%02d : %02d", 0, 0)
                }
            }
            else {
                Toast.makeText(this,"start time should be Greater than working start time/ start time should be lesser than your working end time",Toast.LENGTH_SHORT).show()
                mAssign_start_time_BTN.text = String.format(Locale.getDefault(), "%02d : %02d", 0, 0)
                mAssign_end_time_BTN.text = String.format(Locale.getDefault(), "%02d : %02d", 0, 0)
            }

        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}


