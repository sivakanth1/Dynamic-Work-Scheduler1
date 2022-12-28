package com.example.dynamicworkscheduler


import com.google.gson.reflect.TypeToken
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.mikephil.charting.charts.PieChart
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import android.os.Bundle
import android.view.ViewGroup
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieData
import android.graphics.Typeface
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import android.widget.*
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.gson.Gson
import com.google.gson.JsonArray
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var mCancel_dialog_YES_BTN: Button
    lateinit var mCancel_dialog_NO_BTN: Button
    lateinit var mUpdate_dialog_YES_BTN: Button
    lateinit var mUpdate_dialog_NO_BTN: Button
    lateinit var task_activity_cancel_dialog: Dialog
    lateinit var task_activity_update_dialog: Dialog
    lateinit var mInProgress_Layout: ConstraintLayout
    lateinit var mExpandable_pane: ConstraintLayout
    lateinit var mExpandable_pane_LL: LinearLayout
    lateinit var mLower_pane: ConstraintLayout
    lateinit var mExpand_upNext_IV: ImageView
    lateinit var chart_colors: IntArray
    lateinit var pieChart: PieChart
    lateinit var category_list: List<String>
    lateinit var mCategoryRV: RecyclerView
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var mTaskDate: TextView
    lateinit var mWeek_day1: TextView
    lateinit var mSeeFullReport: TextView
    lateinit var mUp_next_external_TV: TextView
    lateinit var mIn_progress_Tv: TextView
    val myApplication = MyApplication()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mIn_progress_Tv = findViewById(R.id.in_progress_Tv)
        mInProgress_Layout = findViewById(R.id.InProgress_Layout)
        //        mUp_next_external_TV = findViewById(R.id.up_next_external_TV);
        mExpandable_pane = findViewById(R.id.lower_pane)
        pieChart = findViewById(R.id.pie_chart)
        mExpandable_pane_LL = findViewById(R.id.Expandable_layout)
        mLower_pane = findViewById(R.id.lower_pane)
        //        mExpand_upNext_IV = findViewById(R.id.expand_upNext_IV);
        setUpPieChart()
        initPieChart()
        MyApplication.createTasksOfWeekList()
        MyApplication.createUserWorkingList("09:00","12:00")

//        //retrivingData
         lateinit var myList:MutableList<String>
//        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
        val gson = Gson()
//        val myDataList = gson.toJson(MyApplication.tasks_id_list_week[1]);
////        println(myDataList)
////        Log.d("test",myDataList)
//        editor.putString("myApplicationClass",myDataList)
//        editor.apply()
////
        val serializedObject =getPreferences(Context.MODE_PRIVATE)?:return
        val data = serializedObject.getString("myApplicationClass",null)
        if(data!=null){
            val type = object:TypeToken<MutableList<String>>(){}.type
            myList = gson.fromJson(data,type)
            println(myList)
        }

        MyApplication.deadlineInputsAdding(MyApplication.createTaskId(createdTime = "15:00", createdDate = "26-12-2022"),title="testObjectDeadline1", priority = 3, category = 3, startDate = MyApplication.date_time_formatter.parse("2022-12-26 15:00"), deadlineDate = MyApplication.date_time_formatter.parse("2022-12-29 10:45"), startTime = "", endTime = "", description = "", duration = 120)
        MyApplication.deadlineInputsAdding(MyApplication.createTaskId(createdTime = "15:51", createdDate = "26-12-2022"),title="testObjectDeadline2", priority = 2, category = 3, startDate = MyApplication.date_time_formatter.parse("2022-12-26 15:50"), deadlineDate = MyApplication.date_time_formatter.parse("2022-12-30 10:45"), startTime = "", endTime = "", description = "", duration = 180)
        MyApplication.deadlineInputsAdding(MyApplication.createTaskId(createdTime = "16:00", createdDate = "26-12-2022"),title="testObjectDeadline3", priority = 2, category = 2, startDate = MyApplication.date_time_formatter.parse("2022-12-26 10:00"), deadlineDate = MyApplication.date_time_formatter.parse("2022-12-30 10:45"), startTime = "10:00", endTime = "12:00", description = "", duration = 0)
//
//
//
//        findViewById<View>(R.id.today_task_TV).setOnClickListener { view: View? ->
//            startActivity(
//                Intent(this, ScheduleOverview::class.java)
//            )
//        }


//        findViewById(R.id.add_task).setOnClickListener(view -> {
//            Log.d("TEST", "on call create task");
////            activityCallingFlag = "createTask";
//            startActivity(new Intent(Dashboard.this, CreateTask.class));
////            finish();
//        });
        mInProgress_Layout.setOnClickListener(View.OnClickListener { view: View? ->
            Toast.makeText(this, "$myList", Toast.LENGTH_SHORT).show()
            task_activity_update_dialog = Dialog(this)
            task_activity_update_dialog.setContentView(R.layout.task_activity_dialog)
            task_activity_update_dialog.window!!.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            task_activity_update_dialog.window!!.setBackgroundDrawable(getDrawable(R.drawable.all_rounded_corners_big))
            task_activity_update_dialog.show()
            mUpdate_dialog_YES_BTN =
                task_activity_update_dialog.findViewById(R.id.Update_dialog_YES_BTN)
            mUpdate_dialog_NO_BTN =
                task_activity_update_dialog.findViewById(R.id.Update_dialog_NO_BTN)
            mUpdate_dialog_YES_BTN.setOnClickListener(View.OnClickListener { view1: View? ->
                Toast.makeText(this, "Update", Toast.LENGTH_SHORT).show()
                task_activity_update_dialog.dismiss()
            })
            mUpdate_dialog_NO_BTN.setOnClickListener(View.OnClickListener { view1: View? -> task_activity_update_dialog.dismiss() })
        })
        mIn_progress_Tv.setOnClickListener(View.OnClickListener { view: View? ->
            Toast.makeText(this, "${MyApplication.tasks_objects_list_week[1][1].title}", Toast.LENGTH_SHORT).show()
            task_activity_cancel_dialog = Dialog(this)
            task_activity_cancel_dialog.setContentView(R.layout.task_activity_cancel_dialog)
            task_activity_cancel_dialog.window!!.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            task_activity_cancel_dialog.window!!.setBackgroundDrawable(getDrawable(R.drawable.all_rounded_corners_big))
            task_activity_cancel_dialog.show()
            mCancel_dialog_YES_BTN =
                task_activity_cancel_dialog.findViewById(R.id.Cancel_dialog_YES_BTN)
            mCancel_dialog_NO_BTN =
                task_activity_cancel_dialog.findViewById(R.id.Cancel_dialog_NO_BTN)
            mCancel_dialog_NO_BTN.setOnClickListener(View.OnClickListener { view1: View? -> task_activity_cancel_dialog.dismiss() })
            mCancel_dialog_YES_BTN.setOnClickListener(View.OnClickListener { view1: View? ->
                Toast.makeText(this, "YES Cancel", Toast.LENGTH_SHORT).show()
                task_activity_cancel_dialog.dismiss()
            })
        })

//        Log.d("TEST", "flag in create "+collectTaskData);

//        if(collectTaskData)
//        {
//            TaskHelper newObj =   getIntent().getParcelableExtra("NewTaskObj");
////            String temp = getIntent().getStringExtra("title");
//            Log.d("TEST",newObj.toString());
//
//        }
//        if(temp == null)
//            Log.d("title1", "null");
//        else
//            Log.d("title1", temp);
//        Log.d("NewTask",newObj.toString());


//        boolean isDataAvailable = SyncHelper.isDataAvailable;
//        if(isDataAvailable)
//            mTaskDate.setText(SyncHelper.getTask().toString());
    }

    private fun initPieChart() {
        chart_colors = IntArray(2)
        chart_colors[0] = resources.getColor(R.color.compli_royal_blue)
        chart_colors[1] = resources.getColor(R.color.orange)
        val pieEntries = ArrayList<PieEntry>()
        pieEntries.add(PieEntry(0.78f, ""))
        pieEntries.add(PieEntry(0.22f, ""))
        val pieDataSet = PieDataSet(pieEntries, "")
        pieDataSet.setColors(*chart_colors)
        pieDataSet.setDrawIcons(false)
        val pieData = PieData(pieDataSet)
        pieData.setValueTypeface(Typeface.DEFAULT_BOLD)
        pieData.setValueTextSize(0f)
        pieChart.data = pieData
        pieChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry, h: Highlight) {
//                Toast.makeText(Report_Screen.this, e.get, Toast.LENGTH_SHORT).show();
            }

            override fun onNothingSelected() {}
        })
    }

    private fun setUpPieChart() {
        pieChart.rotationAngle = 90f
        pieChart.setUsePercentValues(true)
        pieChart.animateXY(1000, 1000)
        pieChart.isHapticFeedbackEnabled = true
        //        pieChart.getDescription().setTextColor(getResources().getColor(R.color.black));
        pieChart.description.isEnabled = false
        pieChart.holeRadius = 85f
        //        pieChart.setHoleColor(R.color.white);
        pieChart.isDrawHoleEnabled = true
        pieChart.setEntryLabelTextSize(16f)
        pieChart.setEntryLabelTypeface(Typeface.DEFAULT_BOLD)
        pieChart.transparentCircleRadius = 0f
        pieChart.isRotationEnabled = false
        pieChart.centerText = "78%"
//        pieChart.centerText = myApplication.testString
        pieChart.setCenterTextSize(21f)
        pieChart.setCenterTextTypeface(Typeface.DEFAULT_BOLD)
        pieChart.legend.isEnabled = false
        pieChart.invalidate()
    }

    //
    //    private void intiCategoryData() {
    //        String[] s = new String[]{"Design", "Develop", "Blog", "Sales", "Backend", "FrontEnd", "Business"};
    //        category_list = Arrays.asList(s);
    //    }
    fun expand(view: View?) {
        val visibility =
            if (mExpandable_pane_LL.visibility == View.GONE) View.VISIBLE else View.GONE
        TransitionManager.beginDelayedTransition(mLower_pane, AutoTransition())

//        // if visible
//        if (visibility == 0) {
////            mExpand_upNext_IV.setRotation(180f);
////            getWindow().setStatusBarColor(Color.parseColor("#e1b941"));
//        }
//        // if gone
//        if (visibility == 8) {
////            mExpand_upNext_IV.setRotation(360f);
////            getWindow().setStatusBarColor(Color.parseColor("#ffffff"));
//        }
        mExpandable_pane_LL.visibility = visibility
    }

    fun openReportScreen(view: View) {
        startActivity(Intent(this,ReportScreen::class.java))
    }

    fun openCreateTaskScreen(view: View) {
        startActivity(Intent(this,CreateTask::class.java))
    }

    fun openScheduleScreen(view: View) {
        startActivity(Intent(this,Schedule::class.java))
    }
}