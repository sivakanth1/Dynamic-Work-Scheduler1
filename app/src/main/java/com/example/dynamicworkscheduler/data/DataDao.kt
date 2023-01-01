package com.example.dynamicworkscheduler.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DataDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(vararg data: TaskData)

    @Delete
    suspend fun delete(data: TaskData)

    @Query("SELECT * FROM taskdb")
    fun getAllData(): LiveData<MutableList<TaskData>>

    @Query("SELECT * FROM taskdb where weekOfYear==:weekNumber")
    fun getPresentWeekData(weekNumber: Int):LiveData<MutableList<TaskData>>

    @Update
    suspend fun updateTask(data: TaskData)
}