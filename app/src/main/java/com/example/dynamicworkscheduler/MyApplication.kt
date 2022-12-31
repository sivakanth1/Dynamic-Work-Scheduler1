package com.example.dynamicworkscheduler

import android.annotation.SuppressLint
import android.app.Application
import com.example.dynamicworkscheduler.data.TaskData
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

class MyApplication: Application() {
    companion object {
        var completeData= mutableListOf<TaskData>()
        private var taskDataWeek= mutableListOf<MutableList<String>>()
        var taskDataObjectsWeek= mutableListOf<MutableList<Task>>()
        @SerializedName("tasks_objects_list_week")val tasks_objects_list_week=mutableListOf<MutableList<Task>>()
        @SerializedName("tasks_objects_list_week_deadlines_c2")val tasks_objects_list_week_c2=mutableListOf<MutableList<Task>>()
        @SerializedName("tasks_objects_list_week_deadlines_c3")val tasks_objects_list_week_c3=mutableListOf<MutableList<Task>>()
        @SerializedName("tasks_objects_list_week_schedules")val tasks_objects_list_week_c1=mutableListOf<MutableList<Task>>()
        @SerializedName("tasks_objects_list_week_reschedule")val tasks_objects_list_week_reschedule=mutableListOf<MutableList<Task>>()
        @SerializedName("tasks_id_list_week")val tasks_id_list_week=mutableListOf<MutableList<String>>()
        @SuppressLint("SimpleDateFormat")
        val date_time_formatter = SimpleDateFormat("yyyy-MM-dd")
        private lateinit var chunkedStartTime:List<String>
        private lateinit var chunkedEndTime:List<String>

        //creating Task Id's
        fun createTaskId(createdTime:String,createdDate:String):String{
            return createdTime.filter { it.isDigit() }+createdDate.filter { it.isDigit()}
        }

        //creating week array on loading of application
        fun createTasksOfWeekList(){
            repeat(7){
                val rowObjects = mutableListOf<Task>()
                val rowObjectsDeadlinesC2 = mutableListOf<Task>()
                val rowObjectsDeadlinesC3 = mutableListOf<Task>()
                val rowObjectsSchedules = mutableListOf<Task>()
                val rowForWeekData = mutableListOf<String>()
                val rowObjectsForWeekData = mutableListOf<Task>()
                val rowObjectsReschedules = mutableListOf<Task>()
                val rowIds = mutableListOf<String>()
                tasks_objects_list_week.add(rowObjects)
                tasks_objects_list_week_c2.add(rowObjectsDeadlinesC2)
                tasks_objects_list_week_c3.add(rowObjectsDeadlinesC3)
                tasks_objects_list_week_c1.add(rowObjectsSchedules)
                taskDataWeek.add(rowForWeekData)
                taskDataObjectsWeek.add(rowObjectsForWeekData)
                tasks_objects_list_week_reschedule.add(rowObjectsReschedules)
                tasks_id_list_week.add(rowIds)
            }
        }

        //creating week array according to the user pattern timings
        fun createUserWorkingList(wStartTime:String,wEndTime:String) {
            var c=0
            chunkedStartTime= wStartTime.filter { it.isDigit() }.chunked(2)
            chunkedEndTime= wEndTime.filter { it.isDigit() }.chunked(2)
            val size=(chunkedEndTime[0].toInt()-chunkedStartTime[0].toInt())*60+(chunkedEndTime[1].toInt()-chunkedStartTime[1].toInt())
            for(i in 0 until size)
            {
                tasks_id_list_week[0].add("n")
                tasks_id_list_week[1].add("n")
                tasks_id_list_week[2].add("n")
                tasks_id_list_week[3].add("n")
                tasks_id_list_week[4].add("n")
                tasks_id_list_week[5].add("n")
                tasks_id_list_week[6].add("n")
                tasks_objects_list_week[0].add(Task(null,null,null,null,null,null))
                tasks_objects_list_week[1].add(Task(null,null,null,null,null,null))
                tasks_objects_list_week[2].add(Task(null,null,null,null,null,null))
                tasks_objects_list_week[3].add(Task(null,null,null,null,null,null))
                tasks_objects_list_week[4].add(Task(null,null,null,null,null,null))
                tasks_objects_list_week[5].add(Task(null,null,null,null,null,null))
                tasks_objects_list_week[6].add(Task(null,null,null,null,null,null))
                c += 1
            }
        }

        fun splitAccordingToWeek(){
            println("Hi")
            tasks_objects_list_week_c1.forEach {
                it.clear()
            }
            tasks_objects_list_week_c2.forEach {
                it.clear()
            }
            tasks_objects_list_week_c3.forEach {
                it.clear()
            }
//            val currentWeekNumber = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)-1
            completeData.forEach{
                    when(date_time_formatter.parse(it.deadlineDate)!!.day){
                        0->{
                            when(it.category){
                                "Category-1"-> {
                                    tasks_objects_list_week_c1[0].add(Task(taskID =it.taskId, title = it.title,
                                    priority = it.priority, category = it.category, startDate = date_time_formatter.parse(it.startDate),
                                    deadlineDate = date_time_formatter.parse(it.deadlineDate),startTime=it.startTime, endTime = it.endTime,
                                    description = it.description, duration = it.duration, weekNumber = it.weekNumber, status = it.status))
                                    }
                                "Category-2"-> tasks_objects_list_week_c2[0].add(Task(taskID =it.taskId, title = it.title,
                                    priority = it.priority, category = it.category, startDate = date_time_formatter.parse(it.startDate),
                                    deadlineDate = date_time_formatter.parse(it.deadlineDate),startTime=it.startTime, endTime = it.endTime,
                                    description = it.description, duration = it.duration, weekNumber = it.weekNumber, status = it.status))
                                "Category-3"-> tasks_objects_list_week_c3[0].add(Task(taskID =it.taskId, title = it.title,
                                    priority = it.priority, category = it.category, startDate = date_time_formatter.parse(it.startDate),
                                    deadlineDate = date_time_formatter.parse(it.deadlineDate),startTime=it.startTime, endTime = it.endTime,
                                    description = it.description, duration = it.duration, weekNumber = it.weekNumber, status = it.status))
                            }
                        }
                        1->{
                            when(it.category){
                                "Category-1"-> tasks_objects_list_week_c1[1].add(Task(taskID =it.taskId, title = it.title,
                                    priority = it.priority, category = it.category, startDate = date_time_formatter.parse(it.startDate),
                                    deadlineDate = date_time_formatter.parse(it.deadlineDate),startTime=it.startTime, endTime = it.endTime,
                                    description = it.description, duration = it.duration, weekNumber = it.weekNumber, status = it.status))
                                "Category-2"-> tasks_objects_list_week_c2[1].add(Task(taskID =it.taskId, title = it.title,
                                    priority = it.priority, category = it.category, startDate = date_time_formatter.parse(it.startDate),
                                    deadlineDate = date_time_formatter.parse(it.deadlineDate),startTime=it.startTime, endTime = it.endTime,
                                    description = it.description, duration = it.duration, weekNumber = it.weekNumber, status = it.status))
                                "Category-3"-> tasks_objects_list_week_c3[1].add(Task(taskID =it.taskId, title = it.title,
                                    priority = it.priority, category = it.category, startDate = date_time_formatter.parse(it.startDate),
                                    deadlineDate = date_time_formatter.parse(it.deadlineDate),startTime=it.startTime, endTime = it.endTime,
                                    description = it.description, duration = it.duration, weekNumber = it.weekNumber, status = it.status))
                            }
                        }
                        2->{
                            when(it.category){
                                "Category-1"-> tasks_objects_list_week_c1[2].add(Task(taskID =it.taskId, title = it.title,
                                    priority = it.priority, category = it.category, startDate = date_time_formatter.parse(it.startDate),
                                    deadlineDate = date_time_formatter.parse(it.deadlineDate),startTime=it.startTime, endTime = it.endTime,
                                    description = it.description, duration = it.duration, weekNumber = it.weekNumber, status = it.status))
                                "Category-2"-> tasks_objects_list_week_c2[2].add(Task(taskID =it.taskId, title = it.title,
                                    priority = it.priority, category = it.category, startDate = date_time_formatter.parse(it.startDate),
                                    deadlineDate = date_time_formatter.parse(it.deadlineDate),startTime=it.startTime, endTime = it.endTime,
                                    description = it.description, duration = it.duration, weekNumber = it.weekNumber, status = it.status))
                                "Category-3"-> tasks_objects_list_week_c3[2].add(Task(taskID =it.taskId, title = it.title,
                                    priority = it.priority, category = it.category, startDate = date_time_formatter.parse(it.startDate),
                                    deadlineDate = date_time_formatter.parse(it.deadlineDate),startTime=it.startTime, endTime = it.endTime,
                                    description = it.description, duration = it.duration, weekNumber = it.weekNumber, status = it.status))
                            }
                        }
                        3->{
                            when(it.category){
                                "Category-1"-> tasks_objects_list_week_c1[3].add(Task(taskID =it.taskId, title = it.title,
                                    priority = it.priority, category = it.category, startDate = date_time_formatter.parse(it.startDate),
                                    deadlineDate = date_time_formatter.parse(it.deadlineDate),startTime=it.startTime, endTime = it.endTime,
                                    description = it.description, duration = it.duration, weekNumber = it.weekNumber, status = it.status))
                                "Category-2"-> tasks_objects_list_week_c2[3].add(Task(taskID =it.taskId, title = it.title,
                                    priority = it.priority, category = it.category, startDate = date_time_formatter.parse(it.startDate),
                                    deadlineDate = date_time_formatter.parse(it.deadlineDate),startTime=it.startTime, endTime = it.endTime,
                                    description = it.description, duration = it.duration, weekNumber = it.weekNumber, status = it.status))
                                "Category-3"-> tasks_objects_list_week_c3[3].add(Task(taskID =it.taskId, title = it.title,
                                    priority = it.priority, category = it.category, startDate = date_time_formatter.parse(it.startDate),
                                    deadlineDate = date_time_formatter.parse(it.deadlineDate),startTime=it.startTime, endTime = it.endTime,
                                    description = it.description, duration = it.duration, weekNumber = it.weekNumber, status = it.status))
                            }
                        }
                        4->{
                            when(it.category){
                                "Category-1"-> tasks_objects_list_week_c1[4].add(Task(taskID =it.taskId, title = it.title,
                                    priority = it.priority, category = it.category, startDate = date_time_formatter.parse(it.startDate),
                                    deadlineDate = date_time_formatter.parse(it.deadlineDate),startTime=it.startTime, endTime = it.endTime,
                                    description = it.description, duration = it.duration, weekNumber = it.weekNumber, status = it.status))
                                "Category-2"-> tasks_objects_list_week_c2[4].add(Task(taskID =it.taskId, title = it.title,
                                    priority = it.priority, category = it.category, startDate = date_time_formatter.parse(it.startDate),
                                    deadlineDate = date_time_formatter.parse(it.deadlineDate),startTime=it.startTime, endTime = it.endTime,
                                    description = it.description, duration = it.duration, weekNumber = it.weekNumber, status = it.status))
                                "Category-3"-> tasks_objects_list_week_c3[4].add(Task(taskID =it.taskId, title = it.title,
                                    priority = it.priority, category = it.category, startDate = date_time_formatter.parse(it.startDate),
                                    deadlineDate = date_time_formatter.parse(it.deadlineDate),startTime=it.startTime, endTime = it.endTime,
                                    description = it.description, duration = it.duration, weekNumber = it.weekNumber, status = it.status))
                            }
                        }
                        5->{
                            when(it.category){
                                "Category-1"-> tasks_objects_list_week_c1[5].add(Task(taskID =it.taskId, title = it.title,
                                    priority = it.priority, category = it.category, startDate = date_time_formatter.parse(it.startDate),
                                    deadlineDate = date_time_formatter.parse(it.deadlineDate),startTime=it.startTime, endTime = it.endTime,
                                    description = it.description, duration = it.duration, weekNumber = it.weekNumber, status = it.status))
                                "Category-2"-> tasks_objects_list_week_c2[5].add(Task(taskID =it.taskId, title = it.title,
                                    priority = it.priority, category = it.category, startDate = date_time_formatter.parse(it.startDate),
                                    deadlineDate = date_time_formatter.parse(it.deadlineDate),startTime=it.startTime, endTime = it.endTime,
                                    description = it.description, duration = it.duration, weekNumber = it.weekNumber, status = it.status))
                                "Category-3"-> tasks_objects_list_week_c3[5].add(Task(taskID =it.taskId, title = it.title,
                                    priority = it.priority, category = it.category, startDate = date_time_formatter.parse(it.startDate),
                                    deadlineDate = date_time_formatter.parse(it.deadlineDate),startTime=it.startTime, endTime = it.endTime,
                                    description = it.description, duration = it.duration, weekNumber = it.weekNumber, status = it.status))
                            }
                        }
                        6->{
                            when(it.category){
                                "Category-1"-> tasks_objects_list_week_c1[6].add(Task(taskID =it.taskId, title = it.title,
                                    priority = it.priority, category = it.category, startDate = date_time_formatter.parse(it.startDate),
                                    deadlineDate = date_time_formatter.parse(it.deadlineDate),startTime=it.startTime, endTime = it.endTime,
                                    description = it.description, duration = it.duration, weekNumber = it.weekNumber, status = it.status))
                                "Category-2"-> tasks_objects_list_week_c2[6].add(Task(taskID =it.taskId, title = it.title,
                                    priority = it.priority, category = it.category, startDate = date_time_formatter.parse(it.startDate),
                                    deadlineDate = date_time_formatter.parse(it.deadlineDate),startTime=it.startTime, endTime = it.endTime,
                                    description = it.description, duration = it.duration, weekNumber = it.weekNumber, status = it.status))
                                "Category-3"-> tasks_objects_list_week_c3[6].add(Task(taskID =it.taskId, title = it.title,
                                    priority = it.priority, category = it.category, startDate = date_time_formatter.parse(it.startDate),
                                    deadlineDate = date_time_formatter.parse(it.deadlineDate),startTime=it.startTime, endTime = it.endTime,
                                    description = it.description, duration = it.duration, weekNumber = it.weekNumber, status = it.status))
                            }
                        }
                    }
            }
            for(i in 0..6){
                tasks_objects_list_week_c3[i].sortBy {
                    it.priority
                }
            }
//            println("C1 list----->${tasks_objects_list_week_c1[5]}")
//            println("C2 list----->${tasks_objects_list_week_c2[5]}")
//            println("C3 list----->${tasks_objects_list_week_c3[5]}")
            completeData.forEach {
                println("week Data ${it.deadlineDate} ${it.category} ${it.startTime} ${it.endTime}")
            }
            addingTasksToList()
        }

        //assigning breaks to the user pattern list by the taking user break timings
        fun assigningBreaks(breakStartTime:String,breakEndTime:String){
            val chunkedTaskEndTime= breakEndTime.filter { it.isDigit() }.chunked(2).toMutableList()
            val chunkedTaskStartTime= breakStartTime.filter { it.isDigit() }.chunked(2).toMutableList()
            val bStartTime:Int=(chunkedTaskStartTime[0].toInt()-chunkedStartTime[0].toInt())*60+(chunkedTaskStartTime[1].toInt()-chunkedStartTime[1].toInt())
            val bEndTime:Int=(chunkedTaskEndTime[0].toInt()-chunkedTaskStartTime[0].toInt())*60+(chunkedTaskEndTime[1].toInt()-chunkedTaskStartTime[1].toInt())+bStartTime
            for (i in bStartTime until bEndTime){
                tasks_id_list_week[0][i]="br"
                tasks_id_list_week[1][i]="br"
                tasks_id_list_week[2][i]="br"
                tasks_id_list_week[3][i]="br"
                tasks_id_list_week[4][i]="br"
                tasks_id_list_week[5][i]="br"
                tasks_id_list_week[6][i]="br"
                tasks_objects_list_week[0].add(Task("br",null,null,null,null,null))
                tasks_objects_list_week[1].add(Task("br",null,null,null,null,null))
                tasks_objects_list_week[2].add(Task("br",null,null,null,null,null))
                tasks_objects_list_week[3].add(Task("br",null,null,null,null,null))
                tasks_objects_list_week[4].add(Task("br",null,null,null,null,null))
                tasks_objects_list_week[5].add(Task("br",null,null,null,null,null))
                tasks_objects_list_week[6].add(Task("br",null,null,null,null,null))
            }
        }

        private fun indexCalculatorForCategory3(tasksIdDayWeek:MutableList<String>):MutableList<Int>{
            tasksIdDayWeek.add(0, "091525122022")
            tasksIdDayWeek.add("091525122022")
            var prev=0
            val ptr=1
            var firstOcc = -1
            var lastOcc: Int
            val list1 = mutableListOf<Int>()
            for(i in ptr until tasksIdDayWeek.size)
            {
                if(tasksIdDayWeek[prev].length > 2 && tasksIdDayWeek[i]=="n")
                    firstOcc = i
                if(tasksIdDayWeek[prev]=="n" && tasksIdDayWeek[i].length > 2)
                {
                    lastOcc = prev
                    list1.add(firstOcc-1)
                    list1.add(lastOcc-1)

                    firstOcc = -1
                }
                prev = i
            }
            tasksIdDayWeek.removeAt(0)
            tasksIdDayWeek.removeLast()
            return list1
        }

        private fun category3DeadlineAddingToWeekList(taskId: String, task: Task){
            //   println("called category3 deadline adding")
            //   println(task.duration)
            if(task.duration!! >0){
                for (j in task.startDate!!.day until task.deadlineDate!!.day) {
                    if(task.duration!!>0){
                        val indexes = indexCalculatorForCategory3(tasks_id_list_week[j])
                        var i = ((0..(indexes.size)).random() * 2)
                        //   println(i)
                        while (i < indexes.size && task.duration!! >0) {
                            if (indexes[i] == 0) {
                                if ((indexes[i + 1] - indexes[i]) >= 15) {
                                    for (t in indexes[i] until indexes[i + 1]) {
                                        if(task.duration!!>0) {
                                            whenCode(j = j, t = t, taskId = taskId, task = task)
                                            task.duration = task.duration?.minus(1)
                                        }
                                    }
                                }
                            } else {
                                when (task.priority) {
                                    1 -> {
                                        if ((indexes[i + 1] - indexes[i]) >= 20)
                                            for (t in indexes[i] + 5 until indexes[i + 1]) {
                                                if (task.duration!! > 0) {
                                                    whenCode(j = j, t = t, taskId = taskId, task = task)
                                                    task.duration = task.duration?.minus(1)
                                                }
                                            }
                                    }

                                    2 -> {
                                        if ((indexes[i + 1] - indexes[i]) >= 25)
                                            for (t in indexes[i] + 5 until indexes[i + 1] - 5) {
                                                if (task.duration!! > 0) {
                                                    whenCode(j = j, t = t, taskId = taskId, task = task)
                                                    task.duration = task.duration?.minus(1)
                                                }
                                            }
                                    }

                                    3 -> {
                                        if ((indexes[i + 1] - indexes[i]) >= 25)
                                            for (t in indexes[i] + 5 until indexes[i + 1] - 5) {
                                                if (task.duration!! > 0) {
                                                    whenCode(j = j, t = t, taskId = taskId, task = task)
                                                    task.duration = task.duration?.minus(1)
                                                }
                                            }
                                    }

                                    else -> {
                                        if ((indexes[i + 1] - indexes[i]) >= 30) {
                                            for (t in indexes[i] + 5 until indexes[i + 1] - 5) {
                                                if (task.duration!! > 0) {
                                                    whenCode(j = j, t = t, taskId = taskId, task = task)
                                                    task.duration = task.duration?.minus(1)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            i += 2
                        }
                    }
                }
            }
        }

        private fun whenCode(j:Int,t:Int,taskId:String, task: Task){
            when (j) {
                0 -> {
                    if (tasks_id_list_week[0][t] !="br")
                        tasks_id_list_week[0][t] = taskId
                    if (tasks_objects_list_week[0][t].taskID!="br")
                        tasks_objects_list_week[0][t] = task
                }

                1 -> {
                    if (tasks_id_list_week[0][t] !="br")
                        tasks_id_list_week[1][t] = taskId
                    if (tasks_objects_list_week[0][t].taskID!="br")
                        tasks_objects_list_week[1][t] = task
                }

                2 -> {
                    if (tasks_id_list_week[0][t] !="br")
                        tasks_id_list_week[2][t] = taskId
                    if (tasks_objects_list_week[0][t].taskID!="br")
                        tasks_objects_list_week[2][t] = task
                }

                3 -> {
                    if (tasks_id_list_week[0][t] !="br")
                        tasks_id_list_week[3][t] = taskId
                    if (tasks_objects_list_week[0][t].taskID!="br")
                        tasks_objects_list_week[3][t] = task
                }

                4 -> {
                    if (tasks_id_list_week[0][t] !="br")
                        tasks_id_list_week[4][t] = taskId
                    if (tasks_objects_list_week[0][t].taskID!="br")
                        tasks_objects_list_week[4][t] = task
                }

                5 -> {
                    if (tasks_id_list_week[0][t] !="br")
                        tasks_id_list_week[5][t] = taskId
                    if (tasks_objects_list_week[0][t].taskID!="br")
                        tasks_objects_list_week[5][t] = task
                }

                6 -> {
                    if (tasks_id_list_week[0][t] !="br")
                        tasks_id_list_week[6][t] = taskId
                    if (tasks_objects_list_week[0][t].taskID!="br")
                        tasks_objects_list_week[6][t] = task
                }
            }
        }

//        fun deadlineInputsAdding(taskId: String, title: String, priority: Int, category: Int, startDate: Date, deadlineDate:Date, startTime: String, endTime: String,description: String,duration:Int){
//            when (deadlineDate.day) {
//                0 -> {
//                    if(category==2)
//                        tasks_objects_list_week_c2[0].add(Task(taskID =taskId, title = title, priority = priority, category = category, startDate = startDate, deadlineDate = deadlineDate,startTime=startTime, endTime = endTime, description = description, duration = duration))
//                    else
//                        tasks_objects_list_week_c3[0].add(Task(taskID =taskId, title = title, priority = priority, category = category, startDate = startDate, deadlineDate = deadlineDate,startTime=startTime, endTime = endTime, description = description, duration = duration))
//                }
//                1 -> {
//                    if(category==2)
//                        tasks_objects_list_week_c2[1].add(Task(taskID =taskId, title = title, priority = priority, category = category, startDate = startDate, deadlineDate = deadlineDate,startTime=startTime, endTime = endTime, description = description, duration = duration))
//                    else
//                        tasks_objects_list_week_c3[1].add(Task(taskID =taskId, title = title, priority = priority, category = category, startDate = startDate, deadlineDate = deadlineDate,startTime=startTime, endTime = endTime, description = description, duration = duration))
//                }
//                2 -> {
//                    if(category==2)
//                        tasks_objects_list_week_c2[2].add(Task(taskID =taskId, title = title, priority = priority, category = category, startDate = startDate, deadlineDate = deadlineDate,startTime=startTime, endTime = endTime, description = description, duration = duration))
//                    else
//                        tasks_objects_list_week_c3[2].add(Task(taskID =taskId, title = title, priority = priority, category = category, startDate = startDate, deadlineDate = deadlineDate,startTime=startTime, endTime = endTime, description = description, duration = duration))
//                }
//                3 -> {
//                    if(category==2)
//                        tasks_objects_list_week_c2[3].add(Task(taskID =taskId, title = title, priority = priority, category = category, startDate = startDate, deadlineDate = deadlineDate,startTime=startTime, endTime = endTime, description = description, duration = duration))
//                    else
//                        tasks_objects_list_week_c3[3].add(Task(taskID =taskId, title = title, priority = priority, category = category, startDate = startDate, deadlineDate = deadlineDate,startTime=startTime, endTime = endTime, description = description, duration = duration))
//                }
//                4 -> {
//                    if(category==2)
//                        tasks_objects_list_week_c2[4].add(Task(taskID =taskId, title = title, priority = priority, category = category, startDate = startDate, deadlineDate = deadlineDate,startTime=startTime, endTime = endTime, description = description, duration = duration))
//                    else
//                        tasks_objects_list_week_c3[4].add(Task(taskID =taskId, title = title, priority = priority, category = category, startDate = startDate, deadlineDate = deadlineDate,startTime=startTime, endTime = endTime, description = description, duration = duration))
//                }
//                5 -> {
//                    if(category==2)
//                        tasks_objects_list_week_c2[5].add(Task(taskID =taskId, title = title, priority = priority, category = category, startDate = startDate, deadlineDate = deadlineDate,startTime=startTime, endTime = endTime, description = description, duration = duration))
//                    else
//                        tasks_objects_list_week_c3[5].add(Task(taskID =taskId, title = title, priority = priority, category = category, startDate = startDate, deadlineDate = deadlineDate,startTime=startTime, endTime = endTime, description = description, duration = duration))
//                }
//                6 -> {
//                    if(category==2)
//                        tasks_objects_list_week_c2[6].add(Task(taskID =taskId, title = title, priority = priority, category = category, startDate = startDate, deadlineDate = deadlineDate,startTime=startTime, endTime = endTime, description = description, duration = duration))
//                    else
//                        tasks_objects_list_week_c3[6].add(Task(taskID =taskId, title = title, priority = priority, category = category, startDate = startDate, deadlineDate = deadlineDate,startTime=startTime, endTime = endTime, description = description, duration = duration))
//                }
//            }
//            for(i in 0..6){
//                tasks_objects_list_week_c2[i].sortBy {
//                    it.priority
//                }
//            }
//            addingTasksToList()
//        }

//        fun submissionInputsAdding(taskId: String, title: String, priority: Int, category: Int, startDate: Date, deadlineDate:Date, startTime: String, endTime: String,description: String,duration:Int){
//            when (deadlineDate.day) {
//                0 -> tasks_objects_list_week_c1[0].add(Task(taskID =taskId, title = title, priority = priority, category = category, startDate = startDate, deadlineDate = deadlineDate,startTime=startTime, endTime = endTime, description = description, duration = duration))
//                1 -> tasks_objects_list_week_c1[1].add(Task(taskID =taskId, title = title, priority = priority, category = category, startDate = startDate, deadlineDate = deadlineDate,startTime=startTime, endTime = endTime, description = description, duration = duration))
//                2 -> tasks_objects_list_week_c1[2].add(Task(taskID =taskId, title = title, priority = priority, category = category, startDate = startDate, deadlineDate = deadlineDate,startTime=startTime, endTime = endTime, description = description, duration = duration))
//                3 -> tasks_objects_list_week_c1[3].add(Task(taskID =taskId, title = title, priority = priority, category = category, startDate = startDate, deadlineDate = deadlineDate,startTime=startTime, endTime = endTime, description = description, duration = duration))
//                4 -> tasks_objects_list_week_c1[4].add(Task(taskID =taskId, title = title, priority = priority, category = category, startDate = startDate, deadlineDate = deadlineDate,startTime=startTime, endTime = endTime, description = description, duration = duration))
//                5 -> tasks_objects_list_week_c1[5].add(Task(taskID =taskId, title = title, priority = priority, category = category, startDate = startDate, deadlineDate = deadlineDate,startTime=startTime, endTime = endTime, description = description, duration = duration))
//                6 -> tasks_objects_list_week_c1[6].add(Task(taskID =taskId, title = title, priority = priority, category = category, startDate = startDate, deadlineDate = deadlineDate,startTime=startTime, endTime = endTime, description = description, duration = duration))
//            }
//            addingTasksToList()
//        }

         fun addingTasksToList(){
            for(i in 0..6){
                if(tasks_objects_list_week_c1[i].isNotEmpty()) {
                    tasks_objects_list_week_c1[i].forEach {
                        rearrangingAndAssigningTasks(it.taskID.toString(), task = it)
                    }
                }
                else if(tasks_objects_list_week_c2[i].isNotEmpty()){
                    tasks_objects_list_week_c2[i].forEach {
                        rearrangingAndAssigningTasks(it.taskID.toString(), task = it)
                    }
                }
                else if(tasks_objects_list_week_c3[i].isNotEmpty()){
                    tasks_objects_list_week_c3[i].forEach {
                        rearrangingAndAssigningTasks(it.taskID.toString(), task = it)
                    }
                }
            }
        }

        private fun rearrangingRemaining(){
            for(i in 0..6) {
                if (tasks_objects_list_week_reschedule[i].isNotEmpty()) {
                    tasks_objects_list_week_reschedule[i].forEach {
                        rearrangingAndAssigningTasks(it.taskID.toString(), task = it)
                    }
                }
            }
        }

        private fun rearrangingAndAssigningTasks(taskId: String,task: Task){
            val chunkedTaskEndTime = task.endTime!!.filter { it.isDigit() }.chunked(2).toMutableList()
            val chunkedTaskStartTime = task.startTime!!.filter { it.isDigit() }.chunked(2).toMutableList()
            var tStartTime = 0
            var tEndTime = 0
            if(task.startTime!="" && task.endTime!=""){
                tStartTime =
                    (chunkedTaskStartTime[0].toInt() - chunkedStartTime[0].toInt()) * 60 + (chunkedTaskStartTime[1].toInt() - chunkedStartTime[1].toInt())
                tEndTime =
                    (chunkedTaskEndTime[0].toInt() - chunkedTaskStartTime[0].toInt()) * 60 + (chunkedTaskEndTime[1].toInt() - chunkedTaskStartTime[1].toInt()) + tStartTime
            }
            when(task.category){
                "Category-1"->{
                    for(t in tStartTime until tEndTime){
                        if(tasks_objects_list_week[task.startDate!!.day][t].category=="Category-2" || tasks_id_list_week[task.startDate!!.day][t]=="n"){
                            whenCode(j=task.startDate!!.day,t=t,taskId=taskId,task=task)
                        }
                        else if(tasks_objects_list_week[task.startDate!!.day][t].category == "Category-3"){
                            tasks_objects_list_week[task.startDate!!.day][t].duration = tasks_objects_list_week[task.startDate!!.day][t].duration?.plus(1)
                            whenCode(j=task.startDate!!.day,t=t,taskId=taskId,task=task)
                        }
                    }
                    rearrangingRemaining()
                }
                "Category-2"->{
                    for(j in task.startDate!!.day until task.deadlineDate!!.day+1) {
                        for (t in tStartTime until tEndTime) {
                            if (tasks_objects_list_week[j][t].category == "Category-2") {
                                tasks_objects_list_week[j][t].duration = tasks_objects_list_week[j][t].duration?.plus(1)
                                tasks_objects_list_week_reschedule[j].add(tasks_objects_list_week[j][t])
                                whenCode(j = j, t = t, taskId = taskId, task = task)
                            }else if(tasks_id_list_week[j][t]=="n"){
                                whenCode(j = j, t = t, taskId = taskId, task = task)
                            }
                        }
                        tasks_objects_list_week_reschedule[j]=tasks_objects_list_week_reschedule[j].toSet().toMutableList()
                    }
                    rearrangingRemaining()
                }
                "Category-3"->{
                    category3DeadlineAddingToWeekList(taskId=taskId,task=task)
                }
            }
        }

        fun getDayTasksString(): MutableList<MutableList<String>> {
            for(i in 0..6){
                if(tasks_id_list_week[i].isNotEmpty()) {
                    tasks_id_list_week[i].forEach {
                        if(taskDataWeek[i].isEmpty()){
                            if(it!="n"&&it!="br") {
                                taskDataWeek[i].add(it)
                            }
                        }else {
                            if(it!="n"&&it!="br") {
                                if (it != taskDataWeek[i].last()) {
                                    taskDataWeek[i].add(it)
                                }
                            }
                        }
                    }
                }
            }
            return taskDataWeek
        }

        fun getDayTasksObject():MutableList<MutableList<Task>>{
     //   fun getDayTasksObject(){
            for(i in 0..6){
                taskDataObjectsWeek[i].clear()
                if(tasks_objects_list_week[i].isNotEmpty()) {
                    tasks_objects_list_week[i].forEach {
                        if(taskDataObjectsWeek[i].isEmpty()&&it.taskID!=null&&it.taskID!="br"){
                                taskDataObjectsWeek[i].add(it)
                        }else {
                            if(it.taskID!=null&&it.taskID!="br"&&it.taskID != taskDataObjectsWeek[i].last().taskID) {
                                    taskDataObjectsWeek[i].add(it)
                               // println(taskDataObjectsWeek[i].last().taskID)
                            }
                        }
                    }
                }
            }
            return taskDataObjectsWeek
        }
    }
}