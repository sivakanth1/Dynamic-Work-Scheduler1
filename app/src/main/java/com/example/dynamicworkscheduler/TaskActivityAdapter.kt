package com.example.dynamicworkscheduler

import android.content.Context
import com.example.dynamicworkscheduler.TaskActivity
import android.widget.ArrayAdapter
import com.example.dynamicworkscheduler.R
import com.example.dynamicworkscheduler.ReportScreen
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import java.util.ArrayList


class TaskActivityAdapter(context: Context, objects: ArrayList<TaskActivity?>) :
    ArrayAdapter<TaskActivity?>(context, R.layout.user_task_activity_list_item, objects) {
    var reportscreen: ReportScreen? = null
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val taskActivity = getItem(position)
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                .inflate(R.layout.user_task_activity_list_item, parent, false)
        }
        val item_start_time = convertView!!.findViewById<TextView>(R.id.Item_StartTime)
        val item_inside_start_time = convertView.findViewById<TextView>(R.id.Item_inside_StartTime)
        val item_inside_end_time = convertView.findViewById<TextView>(R.id.Item_inside_EndTime)
        val title_TV = convertView.findViewById<TextView>(R.id.Item_Title)
        val state_IV = convertView.findViewById<ImageView>(R.id.Item_state)
        val item_dynamic_ll = convertView.findViewById<LinearLayout>(R.id.Dynamic_layout_LL)
        item_start_time.text = taskActivity!!.item_start_time
        item_inside_start_time.text = taskActivity.item_inside_startTime
        item_inside_end_time.text = taskActivity.item_inside_EndTime
        title_TV.text = taskActivity.item_title
        state_IV.setImageResource(taskActivity.item_state_image)
        item_dynamic_ll.background =
            ContextCompat.getDrawable(context, taskActivity.item_dynamic_bg)
        return convertView
    }
}