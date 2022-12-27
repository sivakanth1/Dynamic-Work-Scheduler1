package com.example.dynamicworkscheduler

class SyncHelper internal constructor(task: TaskHelper, flag: Boolean) {


    private var task: TaskHelper

    companion object {
        var isDataAvailable: Boolean = false
        lateinit var task: TaskHelper
    }

    init {
        this.task = task
        isDataAvailable = flag
    }
}