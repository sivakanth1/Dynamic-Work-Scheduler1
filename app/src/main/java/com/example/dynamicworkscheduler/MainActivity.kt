package com.example.dynamicworkscheduler



import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
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
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.dynamicworkscheduler.data.AppDatabase
import com.example.dynamicworkscheduler.data.DataDao
import com.example.dynamicworkscheduler.data.TaskData
import com.example.dynamicworkscheduler.data.TaskViewModel
import com.example.dynamicworkscheduler.databinding.ActivityMainBinding
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Calendar

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
    lateinit var chart_colors: IntArray
    lateinit var pieChart: PieChart
    lateinit var mIn_progress_Tv: TextView
    lateinit var mNoTasksScheduled_TV: TextView
    lateinit var mProgress_Tv: TextView
    lateinit var mAssignTitleTv: TextView
    lateinit var mAssignDescriptionTv: TextView
    lateinit var mAssignDeadLineTv: TextView
    lateinit var mAssignUpNextPriorityTv: TextView
    lateinit var mAssignUpNextTitleTv: TextView
    lateinit var mAssignUpNextDescriptionTv: TextView
    private lateinit var mTaskViewModel: TaskViewModel
    lateinit var db:AppDatabase
    lateinit var dataDao: DataDao
    lateinit var weekListData:MutableList<MutableList<Task>>
    private var weekListFromDB = mutableListOf<TaskData>()
    var finishedTaskCount=0
    var suspendedTaskCount=0
    val titles = mutableListOf<String>()
    val endTimes = mutableListOf<String>()
    val startTimes = mutableListOf<String>()
    val descriptions = mutableListOf<String>()
    val priorities = mutableListOf<Int>()
    val category = mutableListOf<String>()
    val taskIds = mutableListOf<String>()
    val status = mutableListOf<String>()
    val startDates= mutableListOf<String>()
    val deadLineDates = mutableListOf<String>()
    val durations = mutableListOf<Int>()
    val weekNumbers = mutableListOf<Int>()
    private lateinit var auth: FirebaseAuth
    lateinit var preferences: SharedPreferences
    var i=0
    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable
    var statusString="No In Progress Tasks"

    private lateinit var binding:ActivityMainBinding
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        if(auth.currentUser==null){
            startActivity(Intent(this,SignIn::class.java))
            finish()
        }
        else{
            val greetingsTv = binding.GreetingsUserTV
            greetingsTv.text = "Hi ${auth.currentUser!!.displayName}"
        }

        //Shared Preferences

        preferences = getSharedPreferences("iValue", MODE_PRIVATE)


//        preferences.edit().putString("day","${Calendar.getInstance().time.day}").apply()
//
//        if(preferences.getString("day",null)!="${Calendar.getInstance().time.day-1}"){
//            preferences.edit().putString("i","0").apply()
//        }

        //Shared Preferences

        //Database
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,"taskdb"
        ).build()

        dataDao=db.dataDao()
//        dataDao.getAllData().observe(this,Observer{task->
//            setData(task)
//        })

//        dataDao.getPresentWeekData(Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)-1).observe(this
//        ) { task ->
//            setData(task)
//        }

        //Database

        //Handler
        mHandler = Handler()
        mRunnable= Runnable {
            kotlin.run {
//           finish();
//           overridePendingTransition(0, 0);
//           startActivity(intent);
//           overridePendingTransition(0, 0);
                setInProgressWidget()
              //  getDataFromDataBase()
                mHandler.postDelayed(mRunnable,60000)
            }
        }

        //Handler

        //Initialization
        mAssignTitleTv=binding.AssignTitleTV
        mAssignDeadLineTv = binding.AssignDeadlineTV
        mAssignDescriptionTv = binding.AssignDescriptionTV
        mProgress_Tv = binding.progressTv
        mAssignUpNextTitleTv = binding.AssignUpNextTitleTv
        mAssignUpNextPriorityTv = binding.AssignUpNextPriorityTV
        mAssignUpNextDescriptionTv = binding.AssignUpNextDescriptionTv

        mNoTasksScheduled_TV = binding.noTasksScheduledTV
        //Initialization

        //Database

        mTaskViewModel= ViewModelProvider(this)[TaskViewModel::class.java]
//        mTaskViewModel.readAllData.observe(this, Observer {task->
//            setData(task)
//        })

//        if (list != null) {
//            Toast.makeText(this,"$list",Toast.LENGTH_SHORT).show()
//        }else{
//            Toast.makeText(this,"$list",Toast.LENGTH_SHORT).show()
//        }
        //Database

        mIn_progress_Tv = binding.inProgressTv
        mInProgress_Layout = binding.InProgressLayout
        mExpandable_pane = binding.lowerPane
        pieChart = binding.pieChart
        mExpandable_pane_LL = binding.ExpandableLayout
        mLower_pane = binding.lowerPane

        MyApplication.createTasksOfWeekList()
     //   MyApplication.createUserWorkingList("09:00","17:00")
        MyApplication.createUserWorkingList("09:00","23:00")

        mInProgress_Layout.setOnClickListener {
                task_activity_update_dialog = Dialog(this)
                task_activity_update_dialog.setContentView(R.layout.task_activity_dialog)
                task_activity_update_dialog.window!!.setLayout(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                task_activity_update_dialog.window!!.setBackgroundDrawable(getDrawable(R.drawable.all_rounded_corners_big))
                task_activity_update_dialog.show()
                mUpdate_dialog_YES_BTN =
                    task_activity_update_dialog.findViewById(R.id.Update_dialog_YES_BTN)
                mUpdate_dialog_NO_BTN =
                    task_activity_update_dialog.findViewById(R.id.Update_dialog_NO_BTN)
                mUpdate_dialog_YES_BTN.setOnClickListener {
                    // val updateData = TaskData()
                    //     Toast.makeText(this, "${taskIds.size}", Toast.LENGTH_SHORT).show()
                    mTaskViewModel.updateTask(
                        TaskData(
                            taskId = taskIds[i],
                            title = titles[i],
                            priority = priorities[i],
                            category = category[i],
                            description = descriptions[i],
                            startTime = startTimes[i],
                            endTime = endTimes[i],
                            startDate = startDates[i],
                            deadlineDate = deadLineDates[i],
                            duration = durations[i],
                            weekNumber = weekNumbers[i],
                            status = "finished"
                        )
                    )
                    getDataFromDataBase()
                    task_activity_update_dialog.dismiss()
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
                mUpdate_dialog_NO_BTN.setOnClickListener { task_activity_update_dialog.dismiss() }
        }
        mIn_progress_Tv.setOnClickListener {
            task_activity_cancel_dialog = Dialog(this)
            task_activity_cancel_dialog.setContentView(R.layout.task_activity_cancel_dialog)
            task_activity_cancel_dialog.window!!.setLayout(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            task_activity_cancel_dialog.window!!.setBackgroundDrawable(getDrawable(R.drawable.all_rounded_corners_big))
            task_activity_cancel_dialog.show()
            mCancel_dialog_YES_BTN =
                task_activity_cancel_dialog.findViewById(R.id.Cancel_dialog_YES_BTN)
            mCancel_dialog_NO_BTN =
                task_activity_cancel_dialog.findViewById(R.id.Cancel_dialog_NO_BTN)
            mCancel_dialog_NO_BTN.setOnClickListener { task_activity_cancel_dialog.dismiss() }
            mCancel_dialog_YES_BTN.setOnClickListener {
                mTaskViewModel.updateTask(TaskData(
                    taskId = taskIds[i], title = titles[i], priority = priorities[i],
                    category = category[i], description = descriptions[i],startTime=startTimes[i],
                    endTime = endTimes[i], startDate = startDates[i], deadlineDate = deadLineDates[i],
                    duration = durations[i], weekNumber = weekNumbers[i], status = "suspended"
                ))
                getDataFromDataBase()
                task_activity_cancel_dialog.dismiss()
                finish();
                overridePendingTransition(0, 0);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setData(task:MutableList<TaskData>){
        this.weekListFromDB=task
        MyApplication.completeData = weekListFromDB
        MyApplication.splitAccordingToWeek()
        getTodayTasks()
    }

    private fun initPieChart() {
        chart_colors = IntArray(2)
        chart_colors[0] = resources.getColor(R.color.compli_royal_blue)
        chart_colors[1] = resources.getColor(R.color.orange)
        val pieEntries = ArrayList<PieEntry>()
        pieEntries.add(PieEntry(finishedTaskCount.toFloat(), ""))
        pieEntries.add(PieEntry(suspendedTaskCount.toFloat(), ""))
        val pieDataSet = PieDataSet(pieEntries, "")
        pieDataSet.setColors(*chart_colors)
        pieDataSet.setDrawIcons(false)
        val pieData = PieData(pieDataSet)
        pieData.setValueTypeface(Typeface.DEFAULT_BOLD)
        pieData.setValueTextSize(0f)
        pieChart.data = pieData
        pieChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry, h: Highlight) {}
            override fun onNothingSelected() {}
        })
    }

    private fun setUpPieChart() {
        pieChart.rotationAngle = 90f
        pieChart.setUsePercentValues(true)
        pieChart.animateXY(1000, 1000)
        pieChart.isHapticFeedbackEnabled = true
        pieChart.description.isEnabled = false
        pieChart.holeRadius = 85f
        pieChart.isDrawHoleEnabled = true
        pieChart.setEntryLabelTextSize(16f)
        pieChart.setEntryLabelTypeface(Typeface.DEFAULT_BOLD)
        pieChart.transparentCircleRadius = 0f
        pieChart.isRotationEnabled = false
        pieChart.centerText = if(titles.size>0) {"${((finishedTaskCount.toDouble()/titles.size)*100).toInt()}%"} else {"0"}
        pieChart.setCenterTextSize(21f)
        pieChart.setCenterTextTypeface(Typeface.DEFAULT_BOLD)
        pieChart.legend.isEnabled = false
        pieChart.invalidate()
    }

    @SuppressLint("SimpleDateFormat", "CommitPrefEdits", "SetTextI18n")
    fun setInProgressWidget(){
        mHandler.postDelayed(mRunnable,5000)
        i = preferences.getString("i",null)?.toInt() ?: 0
        if(preferences.getInt("day",-1)== Calendar.getInstance().time.day - 1){
            i=0
        }
//      i=0
//        println("i value----------->$i")
//        println("day value---------->${preferences.getInt("day",-1)}")
//        println("today day value------->${Calendar.getInstance().time.day}")
        val timeFormatter = SimpleDateFormat("HH:mm")
        val currentTimeArray = timeFormatter.format(Calendar.getInstance().time).filter { it.isDigit() }.chunked(2)
        val currentTimeInt = (currentTimeArray[0].toInt()*60)+currentTimeArray[1].toInt()
        mAssignTitleTv.text = statusString
        mAssignTitleTv.gravity = Gravity.CENTER
        mAssignDescriptionTv.text = ""
        mAssignDeadLineTv.visibility = View.GONE
        mAssignUpNextTitleTv.text = "No Upcoming Tasks"
        mAssignUpNextDescriptionTv.text = ""
        mAssignUpNextPriorityTv.text = "#0"
        if(titles.size>0){
            val startTimeArrayOfI = startTimes[i].filter { it.isDigit() }.chunked(2)
            val endTimeArrayOfI = endTimes[i].filter { it.isDigit() }.chunked(2)
            val startTimeOfIValue = (startTimeArrayOfI[0].toInt()*60)+startTimeArrayOfI[1].toInt()
            val endTimeOfIValue = (endTimeArrayOfI[0].toInt()*60)+endTimeArrayOfI[1].toInt()

//            mAssignDeadLineTv.visibility = View.GONE
            mInProgress_Layout.visibility = View.GONE
            mIn_progress_Tv.visibility = View.GONE
            if(currentTimeInt<startTimeOfIValue){
//                if(i-1>=0){
//                    mAssignTitleTv.text = titles[i-1]
//                    mAssignDeadLineTv.text = endTimes[i-1]
//                    mAssignDescriptionTv.text = descriptions[i-1]
//                }
                mInProgress_Layout.visibility = View.GONE
                mIn_progress_Tv.visibility = View.GONE

                mNoTasksScheduled_TV.visibility = View.VISIBLE

                mAssignUpNextTitleTv.text = titles[i]
                mAssignUpNextDescriptionTv.text = descriptions[i]
                mAssignUpNextPriorityTv.text = "#${priorities[i]}"
            }else if(status[i] == "pending" && currentTimeInt >= startTimeOfIValue && currentTimeInt <= endTimeOfIValue){

                println("when current time >= statTime $i")
                mInProgress_Layout.visibility = View.VISIBLE
                mIn_progress_Tv.visibility =View.VISIBLE
                mAssignDeadLineTv.visibility =View.VISIBLE

                mNoTasksScheduled_TV.visibility = View.GONE

                mAssignTitleTv.text = titles[i]
                mAssignDeadLineTv.text = endTimes[i]
                mAssignDescriptionTv.text = descriptions[i]

            }
            else if(currentTimeInt > endTimeOfIValue){
                if(i+1 >= titles.size){

                    mNoTasksScheduled_TV.visibility = View.VISIBLE

                    mAssignUpNextTitleTv.text = "No Upcoming Tasks"
                    mAssignUpNextDescriptionTv.text = ""
                    mAssignUpNextPriorityTv.text = "#0"
                }else{
                    i+=1
                    preferences.edit().putString("i","$i").apply()
//                    preferences.edit().putString("day","${Calendar.getInstance().time.day}")
                    preferences.edit().putInt("day",Calendar.getInstance().time.day)
                }
            }
            else if(status[i] == "finished" || status[i] == "pending")
            {
                if(i+1>= titles.size){
                    println("No upcoming Tasks")
                    mAssignUpNextTitleTv.text = "No Upcoming Tasks"
                    mAssignUpNextDescriptionTv.text = ""
                    mAssignUpNextPriorityTv.text = "#0"
                }else{
                    println("upcoming task is ${titles[i+1]}")
                    mAssignUpNextTitleTv.text = titles[i+1]
                    mAssignUpNextDescriptionTv.text = descriptions[i+1]
                    mAssignUpNextPriorityTv.text = "#${priorities[i+1]}"
                }
            }
        }
        //println("i value---------> $i")
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    fun getTodayTasks(){
        weekListData = MyApplication.getDayTasksObject()
        titles.clear()
        descriptions.clear()
        startTimes.clear()
        startDates.clear()
        endTimes.clear()
        priorities.clear()
        category.clear()
        status.clear()
        durations.clear()
        weekNumbers.clear()
        deadLineDates.clear()
        taskIds.clear()
        finishedTaskCount = 0
        suspendedTaskCount = 0
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
        if(weekListData[Calendar.getInstance().time.day].isNotEmpty()) {
            weekListData[Calendar.getInstance().time.day].forEach {
                titles.add(it.title.toString())
                descriptions.add(it.description.toString())
                startTimes.add(it.startTime.toString())
                endTimes.add(it.endTime.toString())
                priorities.add(it.priority.toString().toInt())
                category.add(it.category.toString())
                status.add(it.status.toString())
                durations.add(it.duration.toString().toInt())
                weekNumbers.add(it.weekNumber.toString().toInt())
                startDates.add(dateFormatter.format(it.startDate))
                deadLineDates.add(dateFormatter.format(it.deadlineDate))
                taskIds.add(it.taskID.toString())
                when (it.status) {
                    "finished" -> finishedTaskCount += 1
                    "suspended" -> suspendedTaskCount += 1
                }
            }
        }
        setInProgressWidget()
        setUpPieChart()
        initPieChart()
        mProgress_Tv.text = "$finishedTaskCount/${titles.size}"
        pieChart.centerText = if(titles.size>0) {"${((finishedTaskCount.toDouble()/titles.size)*100).toInt()}%"} else {"0"}
        if(titles.size==0){
            mIn_progress_Tv.visibility = View.GONE
            mInProgress_Layout.visibility = View.GONE
        }
        println("titles----->$status")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        getDataFromDataBase()
        setInProgressWidget()
        initPieChart()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDataFromDataBase(){
        this.weekListFromDB.clear()
        MyApplication.completeData.clear()
        dataDao.getPresentWeekData(Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)-1).observe(this
        ) { task ->
            setData(task)
        }
    }

    fun expand(view: View?) {
        val visibility =
            if (mExpandable_pane_LL.visibility == View.GONE) View.VISIBLE else View.GONE
        TransitionManager.beginDelayedTransition(mLower_pane, AutoTransition())
        mExpandable_pane_LL.visibility = visibility
    }

    private var doubleBackToExitPressedOnce = false
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

    fun openReportScreen(view: View) {
        startActivity(Intent(this,ReportScreen::class.java))
    }

    fun openCreateTaskScreen(view: View) {
        startActivity(Intent(baseContext,CreateTask::class.java))
      //  recreate()
//        finish()
    }

    fun openScheduleScreen(view: View) {
        startActivity(Intent(this,Schedule::class.java))
        //  Toast.makeText(this,"Schedule screen building is in progress",Toast.LENGTH_SHORT).show()
    }

//    override fun onResume() {
//        super.onResume()
//        recreate()
//    }

}