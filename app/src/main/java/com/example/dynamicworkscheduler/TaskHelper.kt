package com.example.dynamicworkscheduler

import android.os.Parcelable
import android.os.Parcel
import android.os.Parcelable.Creator

class TaskHelper : Parcelable {
    var title: String?
    var date: String?
    var start_time: String?
    var end_time: String?
    var description: String?
    var category: String?
    var isDeadline: Boolean

    constructor(
        title: String?,
        date: String?,
        start_time: String?,
        end_time: String?,
        description: String?,
        category: String?,
        isDeadline: Boolean
    ) {
        this.title = title
        this.date = date
        this.start_time = start_time
        this.end_time = end_time
        this.description = description
        this.category = category
        this.isDeadline = isDeadline
    }

    protected constructor(`in`: Parcel) {
        title = `in`.readString()
        date = `in`.readString()
        start_time = `in`.readString()
        end_time = `in`.readString()
        description = `in`.readString()
        category = `in`.readString()
        isDeadline = `in`.readByte().toInt() != 0
    }

    override fun toString(): String {
        return "TaskHelper{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", isDeadline=" + isDeadline +
                '}'
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeString(title)
        parcel.writeString(date)
        parcel.writeString(start_time)
        parcel.writeString(end_time)
        parcel.writeString(description)
        parcel.writeString(category)
        parcel.writeByte((if (isDeadline) 1 else 0).toByte())
    }

    companion object {
        @JvmField
        val CREATOR: Creator<TaskHelper> = object : Creator<TaskHelper> {
            override fun createFromParcel(`in`: Parcel): TaskHelper? {
                return TaskHelper(`in`)
            }

            override fun newArray(size: Int): Array<TaskHelper?> {
                return arrayOfNulls(size)
            }
        }
    }
}