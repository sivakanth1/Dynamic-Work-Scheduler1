package com.example.dynamicworkscheduler.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dynamicworkscheduler.data.DataDao
import com.example.dynamicworkscheduler.data.TaskData

@Database(entities = [TaskData::class], version = 1, exportSchema = false)
abstract  class AppDatabase: RoomDatabase(){
    abstract fun dataDao(): DataDao

    companion object{
        @Volatile
        private var INSTANCE:AppDatabase?=null
        fun getDatabase(context: Context):AppDatabase{
            val tempInstance= INSTANCE
            if(tempInstance!=null)
            {
                return tempInstance
            }
            synchronized(this){
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "taskdb"
                ).build()
                INSTANCE=instance
                return instance
            }
        }
    }
}