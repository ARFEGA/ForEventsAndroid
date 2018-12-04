package net.forevents.foreventsandroid.presentation.Calendar

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.ognev.kotlin.agendacalendarview.CalendarManager
import com.ognev.kotlin.agendacalendarview.R
import com.ognev.kotlin.agendacalendarview.models.CalendarEvent
import com.ognev.kotlin.agendacalendarview.render.DefaultEventAdapter
import com.ognev.kotlin.agendacalendarview.utils.DateHelper
import java.text.SimpleDateFormat
import java.util.*

class EventAdapter(var context: Context) : DefaultEventAdapter() {
    private var format: SimpleDateFormat? = null

    init {
        format = SimpleDateFormat(context.getString(R.string.header_date),
            Locale.forLanguageTag("es-ES"))
    }

    override fun getHeaderLayout(): Int {
        return net.forevents.foreventsandroid.R.layout.fragment_calendar_header
    }

    override fun getHeaderItemView(view: View, day: Calendar) {
        val txtDayOfMonth = view.findViewById(net.forevents.foreventsandroid.R.id.view_agenda_day_of_month) as TextView
        val today = CalendarManager.instance!!.today

        if (DateHelper.sameDate(day, today)) {
            txtDayOfMonth.setTextColor(context.resources.getColor(net.forevents.foreventsandroid.R.color.white))
        } else{
            txtDayOfMonth.setTextColor(context.resources.getColor(net.forevents.foreventsandroid.R.color.text_light_color))
        }

        txtDayOfMonth.text = format!!.format(day.time)
    }

    override fun getEventItemView(view: View, event: CalendarEvent, position: Int) {
        val myEvent = event as MyCalendarEvent
        val myObject: SampleEvent? = myEvent.event as SampleEvent?

        if(myEvent.hasEvent()) {
            (view.findViewById(net.forevents.foreventsandroid.R.id.name)
                    as TextView).text = myObject!!.name

            (view.findViewById(net.forevents.foreventsandroid.R.id.description)
                    as TextView).text = myObject.desciption
        }

        view.setOnClickListener {
            Toast.makeText(view.context, "Item: ".plus(position), Toast.LENGTH_SHORT).show()
        }
    }

    override fun getEventLayout(hasEvent: Boolean): Int {
        return if(hasEvent) net.forevents.foreventsandroid.R.layout.fragment_calendar_event else net.forevents.foreventsandroid.R.layout.fragment_calendar_empty_event
    }
}


