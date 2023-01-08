package com.example.dynamicworkscheduler.data

import androidx.lifecycle.LiveData

class TaskRepository(private val dataDao: DataDao) {
 val readAllData:LiveData<MutableList<TaskData>> = dataDao.getAllData()

 suspend fun addTask(task:TaskData){
  dataDao.addTask(task)
 }

 suspend fun updateTask(task:TaskData){
  dataDao.updateTask(task)
 }

 suspend fun delete(task: TaskData){
  dataDao.delete(task)
 }

}