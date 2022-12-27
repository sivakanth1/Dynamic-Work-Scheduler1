package com.example.dynamicworkscheduler

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import androidx.annotation.RequiresApi
import android.os.Build
import android.os.Bundle
import com.example.dynamicworkscheduler.R
import android.view.ViewGroup
import android.app.TimePickerDialog.OnTimeSetListener
import android.app.TimePickerDialog
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.*
//import com.example.dynamicworkscheduler.MainActivity
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

@Suppress("DEPRECATION")

class CreateTask : AppCompatActivity() {
    lateinit var mDuration_Spinner: Spinner
    var selected_date_index = -1
    var category = "Select Category of Task"
    var priority = "+"
    lateinit var cat1_LL: LinearLayout
    lateinit var cat2_LL: LinearLayout
    lateinit var cat3_LL: LinearLayout
    lateinit var mPriority_List_LL: LinearLayout
    lateinit var cat1_title: TextView
    lateinit var cat2_title: TextView
    lateinit var cat3_title: TextView
    lateinit var cat1_des: TextView
    lateinit var cat2_des: TextView
    lateinit var cat3_des: TextView
    lateinit var pri_1: TextView
    lateinit var pri_2: TextView
    lateinit var pri_3: TextView
    lateinit var pri_4: TextView
    lateinit var pri_5: TextView
    lateinit var mSelectCategory_TV: TextView
    lateinit var mAdd_priority_TV: TextView
    lateinit var mShowTaskCategoryListDialog: Dialog
    lateinit var mDone_FAB: ExtendedFloatingActionButton

    //    TaskHelper task_data;
    lateinit var customDateFormat: String
    lateinit var mCategoryLV: ListView
    lateinit var mSelectDateBTN: Button
    lateinit var mStartSelectTimeBTN: Button
    lateinit var mEndSelectedTimeBTN: Button
    var startHours = 0
    var startMinute = 0
    var endHours = 0
    var endMinute = 0
    var DD = 0
    var MM = 0
    var YYYY = 0
    lateinit var start_selected_time: String
    lateinit var end_selected_time: String
    lateinit var selected_date: String
    lateinit var selected_time: String
    lateinit var arrayAdapter: ArrayAdapter<String>
    lateinit var task_title: String
    lateinit var task_description: String
    lateinit var mTitle_ET: EditText
    lateinit var mDescription_ET: EditText
    lateinit var mPriorityTV: AutoCompleteTextView
    lateinit var duration_array_adapter: ArrayAdapter<String>
    var duration_time = arrayOf("< 30 mins", "< 1hr", "1hrs - 2 hrs", "2hrs - 3hrs")
    var category_list =
        arrayOf("Design", "Develop", "Blog", "Sales", "Backend", "FrontEnd", "Business")
    lateinit var calendar: Calendar
    var datePickerYear = 0
    var datePickerMonth = 0
    var datePickerDay = 0
    lateinit var mDay1: LinearLayout
    lateinit var mDay2: LinearLayout
    lateinit var mDay3: LinearLayout
    lateinit var mDay4: LinearLayout
    lateinit var mDay5: LinearLayout
    lateinit var mDay6: LinearLayout
    lateinit var mDay7: LinearLayout
    lateinit var mDay_day1: TextView
    lateinit var mDay_day2: TextView
    lateinit var mDay_day3: TextView
    lateinit var mDay_day4: TextView
    lateinit var mDay_day5: TextView
    lateinit var mDay_day6: TextView
    lateinit var mDay_day7: TextView
    lateinit var mDay_date1: TextView
    lateinit var mDay_date2: TextView
    lateinit var mDay_date3: TextView
    lateinit var mDay_date4: TextView
    lateinit var mDay_date5: TextView
    lateinit var mDay_date6: TextView
    lateinit var mDay_date7: TextView
    lateinit var mAssign_end_time_BTN: Button
    lateinit var mAssign_start_time_BTN: Button
    @RequiresApi(api = Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)


        // hooks
//        mSelectDateBTN = findViewById(R.id.SelectDateBTN);
//        mStartSelectTimeBTN = findViewById(R.id.SelectStartTimeBTN);
//        mEndSelectedTimeBTN = findViewById(R.id.SelectEndTimeBTN);
//        mTitle_ET = findViewById(R.id.Title_ET);
//        mDescription_ET = findViewById(R.id.Description_ET);
//        mDone_FAB = findViewById(R.id.Done_FAB);
//        mCategoryLV = findViewById(R.id.category_LV);
        mDuration_Spinner = findViewById(R.id.Duration_Spinner)
        duration_array_adapter = ArrayAdapter(this, R.layout.priority_dropdown_item, duration_time)
        mDuration_Spinner.setAdapter(duration_array_adapter)
        mAssign_start_time_BTN = findViewById(R.id.Assign_start_time_BTN)
        mAssign_end_time_BTN = findViewById(R.id.Assign_end_time_BTN)
        mSelectCategory_TV = findViewById(R.id.SelectCategory_TV)
        mAdd_priority_TV = findViewById(R.id.Add_priority_TV)
        mPriority_List_LL = findViewById(R.id.Priority_List_LL)
        pri_1 = findViewById(R.id.Priority_1_TV)
        pri_2 = findViewById(R.id.Priority_2_TV)
        pri_3 = findViewById(R.id.Priority_3_TV)
        pri_4 = findViewById(R.id.Priority_4_TV)
        pri_5 = findViewById(R.id.Priority_5_TV)
        mDay1 = findViewById(R.id.day1)
        mDay2 = findViewById(R.id.day2)
        mDay3 = findViewById(R.id.day3)
        mDay4 = findViewById(R.id.day4)
        mDay5 = findViewById(R.id.day5)
        mDay6 = findViewById(R.id.day6)
        mDay7 = findViewById(R.id.day7)
        mDay_day1 = findViewById(R.id.Week_day1)
        mDay_day2 = findViewById(R.id.Week_day2)
        mDay_day3 = findViewById(R.id.Week_day3)
        mDay_day4 = findViewById(R.id.Week_day4)
        mDay_day5 = findViewById(R.id.Week_day5)
        mDay_day6 = findViewById(R.id.Week_day6)
        mDay_day7 = findViewById(R.id.Week_day7)
        mDay_date1 = findViewById(R.id.Week_date1)
        mDay_date2 = findViewById(R.id.Week_date2)
        mDay_date3 = findViewById(R.id.Week_date3)
        mDay_date4 = findViewById(R.id.Week_date4)
        mDay_date5 = findViewById(R.id.Week_date5)
        mDay_date6 = findViewById(R.id.Week_date6)
        mDay_date7 = findViewById(R.id.Week_date7)

        /* SOME BASIC INITIALISATIONS */calendar = Calendar.getInstance()


        // initialize globals
//        selected_date = new StringBuilder("");
//        start_selected_time= new StringBuilder("");
//        end_selected_time= new StringBuilder("");

        // select date for task

//        mSelectDateBTN.setOnClickListener(view -> {
//            datePickerDay = calendar.get(Calendar.DATE);
//            datePickerMonth = calendar.get(Calendar.MONDAY);
//            datePickerYear = calendar.get(Calendar.YEAR);
//            DatePickerDialog datePickerDialog = new DatePickerDialog(CreateTask.this, new DatePickerDialog.OnDateSetListener() {
//                @RequiresApi(api = Build.VERSION_CODES.O)
//                @Override
//                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//
//                    DD = i2;
//                    MM = i1 + 1;
//                    YYYY = i;
//
////                    selected_date = (i + "-" + (i1 + 1) + "-" + i2);
//                    selected_date = String.format(Locale.getDefault(), "%02d-%02d-%02d", i, i1 + 1, i2);
//                    Toast.makeText(CreateTask.this, selected_date, Toast.LENGTH_SHORT).show();
//
//                    customDateFormat = getSelectedDateCustom(DD, MM, YYYY, selected_date);
//                    Toast.makeText(CreateTask.this, selected_date, Toast.LENGTH_SHORT).show();
//                    mSelectDateBTN.setText(customDateFormat);
//                }
//            }, datePickerYear, datePickerMonth, datePickerDay);
//            datePickerDialog.setTitle("Select Date");
//            datePickerDialog.show();
//        });

////        Log.d("AFTSEL",customDateFormat);


//        final Calendar myCalendar = Calendar.getInstance();
        //code for datepicker Dialog
//        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, month);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                updateDate();
//            }
//        };

//        @Override
//                mDay1.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                DatePickerDialog datePicker;
//                int year = myCalendar.get(Calendar.YEAR);
//                int month = myCalendar.get(Calendar.MONTH);
//                int day = myCalendar.get(Calendar.DAY_OF_MONTH);
//                datePicker = new DatePickerDialog(CreateTask.this, date, year, month, day);
//                // This code is used for disable previous date but you can select the date
//                datePicker.getDatePicker().setMinDate(System.currentTimeMillis());
//                datePicker.getDatePicker().setMaxDate(System.currentTimeMillis() +);
//                // This code is used to prevent the date selection
//                datePicker.getDatePicker().setCalendarViewShown(false);
//                datePicker.show();
//            }
//        });


//*/


        /* INIT() CATEGORY DIALOG */mShowTaskCategoryListDialog = Dialog(this@CreateTask)
        mShowTaskCategoryListDialog!!.setContentView(R.layout.task_category_selection_dialog)
        mShowTaskCategoryListDialog!!.window!!
            .setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        mShowTaskCategoryListDialog!!.window!!.setBackgroundDrawable(getDrawable(R.drawable.all_rounded_corners_big))


        /* TASK PRIORITY SELECTION */mAdd_priority_TV.setOnClickListener(View.OnClickListener { view: View? ->
            mPriority_List_LL.setVisibility(View.VISIBLE)
            pri_1.setOnClickListener(View.OnClickListener { v: View? ->
                priority = pri_1.getText() as String
                mAdd_priority_TV.setText(priority)
                mPriority_List_LL.setVisibility(View.GONE)
            })
            pri_2.setOnClickListener(View.OnClickListener { v: View? ->
                priority = pri_2.getText() as String
                mAdd_priority_TV.setText(priority)
                mPriority_List_LL.setVisibility(View.GONE)
            })
            pri_3.setOnClickListener(View.OnClickListener { v: View? ->
                priority = pri_3.getText() as String
                mAdd_priority_TV.setText(priority)
                mPriority_List_LL.setVisibility(View.GONE)
            })
            pri_4.setOnClickListener(View.OnClickListener { v: View? ->
                priority = pri_4.getText() as String
                mAdd_priority_TV.setText(priority)
                mPriority_List_LL.setVisibility(View.GONE)
            })
            pri_5.setOnClickListener(View.OnClickListener { v: View? ->
                priority = pri_5.getText() as String
                mAdd_priority_TV.setText(priority)
                mPriority_List_LL.setVisibility(View.GONE)
            })
        })


        /* TASK CATEGORY SELECTION */mSelectCategory_TV.setOnClickListener(View.OnClickListener { view: View? ->

            // hooking the elements
            cat1_LL = mShowTaskCategoryListDialog.findViewById(R.id.category_1_LL)
            cat2_LL = mShowTaskCategoryListDialog.findViewById(R.id.category_2_LL)
            cat3_LL = mShowTaskCategoryListDialog.findViewById(R.id.category_3_LL)
            cat1_title = mShowTaskCategoryListDialog.findViewById(R.id.category_1_title_TV)
            cat2_title = mShowTaskCategoryListDialog.findViewById(R.id.category_2_title_TV)
            cat3_title = mShowTaskCategoryListDialog.findViewById(R.id.category_3_title_TV)
            cat1_des = mShowTaskCategoryListDialog.findViewById(R.id.category_1_des_TV)
            cat2_des = mShowTaskCategoryListDialog.findViewById(R.id.category_2_des_TV)
            cat3_des = mShowTaskCategoryListDialog.findViewById(R.id.category_3_des_TV)

            // popup
            mShowTaskCategoryListDialog.show()
            cat1_LL.setOnClickListener(View.OnClickListener { v1: View? ->

                // change this layout and its element's tint to royal blue and soft black and others to white and soft black
                cat1_LL.setBackground(resources.getDrawable(R.drawable.tinted_all_rounded_corners))
                cat1_title.setTextColor(resources.getColor(R.color.white))
                cat1_des.setTextColor(resources.getColor(R.color.white))


                //
                cat2_LL.setBackground(resources.getDrawable(R.drawable.all_rounded_corners))
                cat2_title.setTextColor(resources.getColor(R.color.soft_black))
                cat2_des.setTextColor(resources.getColor(R.color.soft_black))
                cat3_LL.setBackground(resources.getDrawable(R.drawable.all_rounded_corners))
                cat3_title.setTextColor(resources.getColor(R.color.soft_black))
                cat3_des.setTextColor(resources.getColor(R.color.soft_black))
                category = "Category-1"
            })
            cat2_LL.setOnClickListener(View.OnClickListener { v1: View? ->

                // change this layout and its element's tint to royal blue and soft black and others to white and soft black
                cat2_LL.setBackground(resources.getDrawable(R.drawable.tinted_all_rounded_corners))
                cat2_title.setTextColor(resources.getColor(R.color.white))
                cat2_des.setTextColor(resources.getColor(R.color.white))


                //
                cat1_LL.setBackground(resources.getDrawable(R.drawable.all_rounded_corners))
                cat1_title.setTextColor(resources.getColor(R.color.soft_black))
                cat1_des.setTextColor(resources.getColor(R.color.soft_black))
                cat3_LL.setBackground(resources.getDrawable(R.drawable.all_rounded_corners))
                cat3_title.setTextColor(resources.getColor(R.color.soft_black))
                cat3_des.setTextColor(resources.getColor(R.color.soft_black))
                category = "Category-2"
            })
            cat3_LL.setOnClickListener(View.OnClickListener { v1: View? ->

                // change this layout and its element's tint to royal blue and soft black and others to white and soft black
                cat3_LL.setBackground(resources.getDrawable(R.drawable.tinted_all_rounded_corners))
                cat3_title.setTextColor(resources.getColor(R.color.white))
                cat3_des.setTextColor(resources.getColor(R.color.white))


                //
                cat1_LL.setBackground(resources.getDrawable(R.drawable.all_rounded_corners))
                cat1_title.setTextColor(resources.getColor(R.color.soft_black))
                cat1_des.setTextColor(resources.getColor(R.color.soft_black))
                cat2_LL.setBackground(resources.getDrawable(R.drawable.all_rounded_corners))
                cat2_title.setTextColor(resources.getColor(R.color.soft_black))
                cat2_des.setTextColor(resources.getColor(R.color.soft_black))
                category = "Category-3"
            })
            val mOk_BTN = mShowTaskCategoryListDialog!!.findViewById<Button>(R.id.Ok_BTN)
            mOk_BTN.setOnClickListener { view1: View? ->
                mSelectCategory_TV.setText(category)
                mShowTaskCategoryListDialog!!.dismiss()
            }
        })

        /* TASK DEADLINE SELECTION */

        /* TASK INIT() DEADLINE DATES */
//        initWeekLayout();

        /* Add Listeners and change the colors accordingly*/mDay1.setOnClickListener(View.OnClickListener { v: View? ->

            // change background of selected layout to tinted background and change all other layouts to non-tinted background
            mDay1.setBackground(resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn))
            mDay_day1.setTextColor(resources.getColor(R.color.white))
            mDay_date1.setTextColor(resources.getColor(R.color.white))
            mDay2.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day2.setTextColor(resources.getColor(R.color.black))
            mDay_date2.setTextColor(resources.getColor(R.color.black))
            mDay3.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day3.setTextColor(resources.getColor(R.color.black))
            mDay_date3.setTextColor(resources.getColor(R.color.black))
            mDay4.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day4.setTextColor(resources.getColor(R.color.black))
            mDay_date4.setTextColor(resources.getColor(R.color.black))
            mDay5.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day5.setTextColor(resources.getColor(R.color.black))
            mDay_date5.setTextColor(resources.getColor(R.color.black))
            mDay6.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day6.setTextColor(resources.getColor(R.color.black))
            mDay_date6.setTextColor(resources.getColor(R.color.black))
            mDay7.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day7.setTextColor(resources.getColor(R.color.black))
            mDay_date7.setTextColor(resources.getColor(R.color.black))
            selected_date_index = 1
        })
        mDay2.setOnClickListener(View.OnClickListener { v: View? ->

            // change background of selected layout to tinted background and change all other layouts to non-tinted background
            mDay2.setBackground(resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn))
            mDay_day2.setTextColor(resources.getColor(R.color.white))
            mDay_date2.setTextColor(resources.getColor(R.color.white))
            mDay1.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day1.setTextColor(resources.getColor(R.color.black))
            mDay_date1.setTextColor(resources.getColor(R.color.black))
            mDay3.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day3.setTextColor(resources.getColor(R.color.black))
            mDay_date3.setTextColor(resources.getColor(R.color.black))
            mDay4.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day4.setTextColor(resources.getColor(R.color.black))
            mDay_date4.setTextColor(resources.getColor(R.color.black))
            mDay5.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day5.setTextColor(resources.getColor(R.color.black))
            mDay_date5.setTextColor(resources.getColor(R.color.black))
            mDay6.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day6.setTextColor(resources.getColor(R.color.black))
            mDay_date6.setTextColor(resources.getColor(R.color.black))
            mDay7.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day7.setTextColor(resources.getColor(R.color.black))
            mDay_date7.setTextColor(resources.getColor(R.color.black))
            selected_date_index = 2
        })
        mDay3.setOnClickListener(View.OnClickListener { v: View? ->

            // change background of selected layout to tinted background and change all other layouts to non-tinted background
            mDay3.setBackground(resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn))
            mDay_day3.setTextColor(resources.getColor(R.color.white))
            mDay_date3.setTextColor(resources.getColor(R.color.white))
            mDay2.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day2.setTextColor(resources.getColor(R.color.black))
            mDay_date2.setTextColor(resources.getColor(R.color.black))
            mDay1.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day1.setTextColor(resources.getColor(R.color.black))
            mDay_date1.setTextColor(resources.getColor(R.color.black))
            mDay4.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day4.setTextColor(resources.getColor(R.color.black))
            mDay_date4.setTextColor(resources.getColor(R.color.black))
            mDay5.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day5.setTextColor(resources.getColor(R.color.black))
            mDay_date5.setTextColor(resources.getColor(R.color.black))
            mDay6.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day6.setTextColor(resources.getColor(R.color.black))
            mDay_date6.setTextColor(resources.getColor(R.color.black))
            mDay7.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day7.setTextColor(resources.getColor(R.color.black))
            mDay_date7.setTextColor(resources.getColor(R.color.black))
            selected_date_index = 3
        })
        mDay4.setOnClickListener(View.OnClickListener { v: View? ->

            // change background of selected layout to tinted background and change all other layouts to non-tinted background
            mDay4.setBackground(resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn))
            mDay_day4.setTextColor(resources.getColor(R.color.white))
            mDay_date4.setTextColor(resources.getColor(R.color.white))
            mDay2.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day2.setTextColor(resources.getColor(R.color.black))
            mDay_date2.setTextColor(resources.getColor(R.color.black))
            mDay3.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day3.setTextColor(resources.getColor(R.color.black))
            mDay_date3.setTextColor(resources.getColor(R.color.black))
            mDay1.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day1.setTextColor(resources.getColor(R.color.black))
            mDay_date1.setTextColor(resources.getColor(R.color.black))
            mDay5.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day5.setTextColor(resources.getColor(R.color.black))
            mDay_date5.setTextColor(resources.getColor(R.color.black))
            mDay6.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day6.setTextColor(resources.getColor(R.color.black))
            mDay_date6.setTextColor(resources.getColor(R.color.black))
            mDay7.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day7.setTextColor(resources.getColor(R.color.black))
            mDay_date7.setTextColor(resources.getColor(R.color.black))
            selected_date_index = 4
        })
        mDay5.setOnClickListener(View.OnClickListener { v: View? ->

            // change background of selected layout to tinted background and change all other layouts to non-tinted background
            mDay5.setBackground(resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn))
            mDay_day5.setTextColor(resources.getColor(R.color.white))
            mDay_date5.setTextColor(resources.getColor(R.color.white))
            mDay2.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day2.setTextColor(resources.getColor(R.color.black))
            mDay_date2.setTextColor(resources.getColor(R.color.black))
            mDay3.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day3.setTextColor(resources.getColor(R.color.black))
            mDay_date3.setTextColor(resources.getColor(R.color.black))
            mDay1.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day1.setTextColor(resources.getColor(R.color.black))
            mDay_date1.setTextColor(resources.getColor(R.color.black))
            mDay4.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day4.setTextColor(resources.getColor(R.color.black))
            mDay_date4.setTextColor(resources.getColor(R.color.black))
            mDay6.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day6.setTextColor(resources.getColor(R.color.black))
            mDay_date6.setTextColor(resources.getColor(R.color.black))
            mDay7.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day7.setTextColor(resources.getColor(R.color.black))
            mDay_date7.setTextColor(resources.getColor(R.color.black))
            selected_date_index = 5
        })
        mDay6.setOnClickListener(View.OnClickListener { v: View? ->

            // change background of selected layout to tinted background and change all other layouts to non-tinted background
            mDay6.setBackground(resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn))
            mDay_day6.setTextColor(resources.getColor(R.color.white))
            mDay_date6.setTextColor(resources.getColor(R.color.white))
            mDay2.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day2.setTextColor(resources.getColor(R.color.black))
            mDay_date2.setTextColor(resources.getColor(R.color.black))
            mDay3.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day3.setTextColor(resources.getColor(R.color.black))
            mDay_date3.setTextColor(resources.getColor(R.color.black))
            mDay4.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day4.setTextColor(resources.getColor(R.color.black))
            mDay_date4.setTextColor(resources.getColor(R.color.black))
            mDay5.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day5.setTextColor(resources.getColor(R.color.black))
            mDay_date5.setTextColor(resources.getColor(R.color.black))
            mDay1.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day1.setTextColor(resources.getColor(R.color.black))
            mDay_date1.setTextColor(resources.getColor(R.color.black))
            mDay7.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day7.setTextColor(resources.getColor(R.color.black))
            mDay_date7.setTextColor(resources.getColor(R.color.black))
            selected_date_index = 6
        })
        mDay7.setOnClickListener(View.OnClickListener { v: View? ->

            // change background of selected layout to tinted background and change all other layouts to non-tinted background
            mDay7.setBackground(resources.getDrawable(R.drawable.tinted_all_rounded_corners_small_btn))
            mDay_day7.setTextColor(resources.getColor(R.color.white))
            mDay_date7.setTextColor(resources.getColor(R.color.white))
            mDay2.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day2.setTextColor(resources.getColor(R.color.black))
            mDay_date2.setTextColor(resources.getColor(R.color.black))
            mDay3.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day3.setTextColor(resources.getColor(R.color.black))
            mDay_date3.setTextColor(resources.getColor(R.color.black))
            mDay4.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day4.setTextColor(resources.getColor(R.color.black))
            mDay_date4.setTextColor(resources.getColor(R.color.black))
            mDay5.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day5.setTextColor(resources.getColor(R.color.black))
            mDay_date5.setTextColor(resources.getColor(R.color.black))
            mDay6.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day6.setTextColor(resources.getColor(R.color.black))
            mDay_date6.setTextColor(resources.getColor(R.color.black))
            mDay1.setBackground(resources.getDrawable(R.drawable.all_rounded_corners_small_btn))
            mDay_day1.setTextColor(resources.getColor(R.color.black))
            mDay_date1.setTextColor(resources.getColor(R.color.black))
            selected_date_index = 7
        })
        Toast.makeText(this, selected_date_index.toString(), Toast.LENGTH_SHORT).show()


        /* TASK TIME SELECTION */

        /* TASK START TIME SELECTION */mAssign_start_time_BTN.setOnClickListener(View.OnClickListener { view: View? ->
            val onTimeSetListener =
                OnTimeSetListener { timePicker: TimePicker?, selected_start_hour: Int, selected_startMinute: Int ->
                    startHours = selected_start_hour
                    startMinute = selected_startMinute
                    start_selected_time =
                        String.format(Locale.getDefault(), "%02d : %02d", startHours, startMinute)
                    mAssign_start_time_BTN.setText(start_selected_time)
                    Toast.makeText(this@CreateTask, start_selected_time, Toast.LENGTH_SHORT).show()
                }
            val timePickerDialog =
                TimePickerDialog(this, onTimeSetListener, startHours, startMinute, true)
            timePickerDialog.setTitle("Select time")
            timePickerDialog.show()
        })

        /* TASK END TIME SELECTION */mAssign_end_time_BTN.setOnClickListener(View.OnClickListener { view: View? ->
            val onTimeSetListener =
                OnTimeSetListener { timePicker, selected_end_hour, selectedEndMinute ->
                    endHours = selected_end_hour
                    endMinute = selectedEndMinute
                    end_selected_time =
                        String.format(Locale.getDefault(), "%02d : %02d", endHours, endMinute)
                    Toast.makeText(this@CreateTask, end_selected_time, Toast.LENGTH_SHORT).show()
                    mAssign_end_time_BTN.setText(
                        String.format(
                            Locale.getDefault(),
                            "%02d : %02d",
                            endHours,
                            endMinute
                        )
                    )
                }
            val timePickerDialog =
                TimePickerDialog(this, onTimeSetListener, endHours, endMinute, true)
            timePickerDialog.setTitle("Select time")
            timePickerDialog.show()
        })


//        Log.d("TEST", "on create create task");

        // category list
//        arrayAdapter = new ArrayAdapter<>(this, R.layout.catrgory_list_item, category_list);
//        mCategoryLV.setAdapter(arrayAdapter);
//
//        mDone_FAB.setOnClickListener(view -> {
//
//            // send Data
//            task_title = mTitle_ET.getText().toString();
//            task_description = mDescription_ET.getText().toString();
//
//            Toast.makeText(this, "Title " + mTitle_ET.getText().toString(), Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, "Des " + mDescription_ET.getText().toString(), Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, "Date " + selected_date, Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, "start time " + start_selected_time, Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, "end time " + end_selected_time, Toast.LENGTH_SHORT).show();
//
//
//            Log.d("TEST", "on done create task");
//
//
////            System.out.println(task_data.toString());
//            TaskHelper newTaskObj = new TaskHelper(task_title,
//                    selected_date,
//                    start_selected_time,
//                    end_selected_time,
//                    task_description,
//                    "idk",
//                    true);
//
//
////            Log.d("PREVTITLE", newTaskObj.getTitle());
////            Log.d("PREVDATE ", newTaskObj.getDate());
////            Log.d("PREVST", newTaskObj.getStart_time());
////            Log.d("PREVET", newTaskObj.getEnd_time());
////            Log.d("PREVDES", newTaskObj.getDescription());
//            TaskHelper task_data = new TaskHelper(task_title, selected_date, start_selected_time, end_selected_time, task_description, "idk", true);
//
//            Intent new_task = new Intent(CreateTask.this, MainActivity.class);
////            new_task.putExtra("title",newTaskObj.getTitle());
////            Log.d("title",newTaskObj.getTitle());
////            new_task.putExtra("NewTaskObj", newTaskObj);
////            Log.d("NEWOBJ", newTaskObj.toString());
////            new_task.putExtra("TEST","TEXT");
//            new SyncHelper(task_data, true);
//            startActivity(new_task);
//            finish();
//        });
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private String getSelectedDateCustom(int dd, int mm, int yyyy, String selected_date) {
//        LocalDate date = LocalDate.parse(selected_date);
//        DayOfWeek dayOfWeek = date.getDayOfWeek();
//
//        String temp = String.valueOf(dayOfWeek).toLowerCase(Locale.ENGLISH);
//        String res = temp.substring(0, 1).toUpperCase(Locale.ENGLISH) + temp.substring(1) + ", " + dd + " " + getCustomMonth(mm) + " " + yyyy;
//
//        return res;
//    }

//    private String getCustomMonth(int m) {
//        switch (m) {
//            case 1:
//                return "Jan";
//            case 2:
//                return "Feb";
//            case 3:
//                return "Mar";
//            case 4:
//                return "Apr";
//            case 5:
//                return "May";
//            case 6:
//                return "Jun";
//            case 7:
//                return "Jul";
//            case 8:
//                return "Aug";
//            case 9:
//                return "Sep";
//            case 10:
//                return "Oct";
//            case 11:
//                return "Nov";
//            case 12:
//                return "Dec";
//        }
//        return "";
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun initWeekLayout() {
        val trimmed_dates = arrayOfNulls<String>(7)
        val trimmed_days = arrayOfNulls<String>(7)
        val days_of_week = arrayOfNulls<String>(7)
        val formatDate = SimpleDateFormat("dd/MM/yyyy")
        val formatDayOfWeek = SimpleDateFormat("EE")
        val str = LocalDate.now().toString()
        val temp = str.split("-").toTypedArray()
        calendar!![temp[0].toInt(), temp[1].toInt() - 1] = temp[2].toInt()
        Toast.makeText(this, calendar!!.firstDayOfWeek.toString(), Toast.LENGTH_SHORT).show()
        //        Toast.makeText(this, str+" "+String.valueOf(calendar.get(calendar.DAY_OF_WEEK)), Toast.LENGTH_SHORT).show();
        for (i in 0..6) {
            days_of_week[i] = formatDate.format(calendar!!.time)
            trimmed_dates[i] = days_of_week[i]?.substring(8)
            //                trimmed_days[i] = formatDayOfWeek.format(calendar.getTime()).substring(0,1);
            calendar!!.add(Calendar.DAY_OF_MONTH, 1)
        }
        mDay_date1!!.text = trimmed_dates[0]
        mDay_date2!!.text = trimmed_dates[1]
        mDay_date3!!.text = trimmed_dates[2]
        mDay_date4!!.text = trimmed_dates[3]
        mDay_date5!!.text = trimmed_dates[4]
        mDay_date6!!.text = trimmed_dates[5]
        mDay_date7!!.text = trimmed_dates[6]
        Log.d("TEST_WEEK_DAYS", Arrays.toString(days_of_week))
        Log.d("TEST_WEEK_DAYS", Arrays.toString(trimmed_dates))
        Log.d("TEST_WEEK_DAYS", Arrays.toString(trimmed_days))
    }

//    fun backToDashboard(view: View?) {
//        startActivity(Intent(this@CreateTask,MainActivity.class))
//        finish()
//    } //    private void updateDate() {
//    //        String myFormat = "dd/MM/yy"; //put your date format in which you need to display
//    //        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
//    //        etDate.setText(sdf.format(myCalendar.getTime()));
//    //    }
}