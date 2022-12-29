package com.example.dynamicworkscheduler.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "taskdb")
data class TaskData(
    @ColumnInfo(name="taskId") @PrimaryKey val taskId:String,
    @ColumnInfo(name="title") val title:String,
    @ColumnInfo(name="priority") val priority:String,
    @ColumnInfo(name="category") val category:String,
    @ColumnInfo(name="description") val description:String,
    @ColumnInfo(name="startTime") val startTime:String,
    @ColumnInfo(name="endTime") val endTime:String,
    @ColumnInfo(name="status") val status:String,
    @ColumnInfo(name="startDate") val startDate: String,
    @ColumnInfo(name="deadlineDate") val deadlineDate: String,
    @ColumnInfo(name="duration") val duration:Int,
)