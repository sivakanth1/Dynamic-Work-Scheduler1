package com.example.dynamicworkscheduler;

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.dynamicworkscheduler.databinding.ActivityScheduleBinding

class Schedule : AppCompatActivity() {
//    private lateinit var mDay1: LinearLayout
//    private lateinit var mDay2: LinearLayout
//    private lateinit var mDay3: LinearLayout
//    private lateinit var mDay4: LinearLayout
//    private lateinit var mDay5: LinearLayout
//    private lateinit var mDay6: LinearLayout
//    private lateinit var mDay7: LinearLayout
    private lateinit var binding: ActivityScheduleBinding
    var mAdd_task_BTN: Button? = null
    var Active_Item = 0
    var task_item_list: ArrayList<TaskFormat>? = null
    var mTaskActivity_LV: ListView? = null
    lateinit var weekListData:MutableList<MutableList<Task>>
    val titles = mutableListOf<String>()
    val des = mutableListOf<String>()
    val durations = mutableListOf<String>()
    lateinit var bgColor:IntArray
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityScheduleBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        mTaskActivity_LV = findViewById(R.id.TaskActivity_LV)
        findViewById<View>(R.id.add_task_BTN).setOnClickListener { v: View? ->
            startActivity(
                Intent(
                    this@Schedule,
                    CreateTask::class.java
                )
            )
        }
        bgColor = intArrayOf(
            R.drawable.soft_blue_total_task_bg,
            R.drawable.soft_lavender_total_task_bg,
            R.drawable.soft_mustard_total_task_bg,
        )
        initUserTaskList()
    }

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