package com.example.dynamicworkscheduler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat

class TotalTaskAdapter(context: Context, objects: ArrayList<TaskFormat>?) :
    ArrayAdapter<TaskFormat?>(context, R.layout.total_tasks_list_item,
        objects!! as List<TaskFormat?>
    ) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val taskFormat: TaskFormat? = getItem(position)
        if (convertView == null) {
            convertView =
                LayoutInflater.from(context).inflate(R.layout.total_tasks_list_item, parent, false)
        }
        val Item_title = convertView!!.findViewById<TextView>(R.id.Item_Title)
        val Item_des = convertView.findViewById<TextView>(R.id.Item_des)
        val Item_duration = convertView.findViewById<TextView>(R.id.Duration_TV)
        val Item_state = convertView.findViewById<ImageView>(R.id.task_state_active_IV)
        val Item_content_ll = convertView.findViewById<LinearLayout>(R.id.Content_LL)
        Item_title.setText(taskFormat!!.title)
        Item_des.setText(taskFormat.description)
        Item_duration.setText(taskFormat.duration)
        Item_content_ll.background = ContextCompat.getDrawable(context, taskFormat.background_color)
        Item_state.setImageResource(taskFormat.state_IV)
        return convertView
    }
}