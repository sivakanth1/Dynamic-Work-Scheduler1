package com.example.dynamicworkscheduler.data

import androidx.lifecycle.LiveData

class TaskRepository(private val dataDao: DataDao) {
 val readAllData:LiveData<MutableList<TaskData>> = dataDao.getAllData()

 suspend fun addTask(task:TaskData){
  dataDao.addTask(task)
 }
}