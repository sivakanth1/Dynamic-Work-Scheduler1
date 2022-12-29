package com.example.dynamicworkscheduler.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel (application: Application):AndroidViewModel(application){
    val readAllData:LiveData<MutableList<TaskData>>
    private val repository:TaskRepository

    init{
        val dataDao=AppDatabase.getDatabase(application).dataDao()
        repository= TaskRepository(dataDao)
        readAllData=repository.readAllData
    }

    fun addTask(task: TaskData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTask(task)
        }
    }
}
