package net.forevents.foreventsandroid.presentation.Calendar


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.os.AsyncTask
import com.ognev.kotlin.agendacalendarview.CalendarController
import com.ognev.kotlin.agendacalendarview.CalendarManager
import com.ognev.kotlin.agendacalendarview.builder.CalendarContentManager
import com.ognev.kotlin.agendacalendarview.models.*
import com.ognev.kotlin.agendacalendarview.render.DefaultEventAdapter
import com.ognev.kotlin.agendacalendarview.utils.DateHelper
import net.forevents.foreventsandroid.R
import java.util.*
import com.ognev.kotlin.agendacalendarview.models.CalendarEvent
import com.ognev.kotlin.agendacalendarview.models.DayItem
import com.ognev.kotlin.agendacalendarview.models.IDayItem

import kotlinx.android.synthetic.main.fragment_calendar.*
import net.forevents.foreventsandroid.Data.CreateUser.User.AppEvents
import net.forevents.foreventsandroid.Util.showDialog
import net.forevents.foreventsandroid.presentation.EventList.EventListFragment

import java.text.SimpleDateFormat

import kotlin.collections.ArrayList


class CalendarEventsFragment : Fragment(), CalendarController {
    companion object {

        //private val EXTRA_SECTION_NUMBER = "section_number"
        private val EXTRA_EVENTS="EXTRA_EVENTS"

        fun newInstance(events: List<AppEvents>): CalendarEventsFragment {
            val fragment = CalendarEventsFragment()
            val args = Bundle()
            //args.putInt(EXTRA_SECTION_NUMBER, sectionNumber)
            args.putParcelableArrayList(EXTRA_EVENTS, ArrayList(events))
            fragment.arguments = args
            return fragment
        }
    }



    private var oldDate: Calendar? = null
    private lateinit var listEvents: List<AppEvents> //Events that comes from Tabfragment
    private var eventList: MutableList<CalendarEvent> = ArrayList()
    private lateinit var minDate: Calendar
    private lateinit var maxDate: Calendar
    private lateinit var contentManager: CalendarContentManager
    private var startMonth: Int = Calendar.getInstance().get(Calendar.MONTH)
    private var endMonth: Int = Calendar.getInstance().get(Calendar.MONTH)

    private var loadingTask: LoadingTask? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view=  inflater.inflate(R.layout.fragment_calendar,container,false)


        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val auxListEvents:ArrayList<AppEvents> = arguments?.getParcelableArrayList(EXTRA_EVENTS)!!

        listEvents= auxListEvents.toList()



        oldDate = Calendar.getInstance()
        minDate = Calendar.getInstance()
        maxDate = Calendar.getInstance()

        minDate.add(Calendar.MONTH, -1)
        minDate.add(Calendar.YEAR, 0)
        minDate.set(Calendar.DAY_OF_MONTH, 1)
        maxDate.add(Calendar.YEAR, 1)


        contentManager = CalendarContentManager(this, agenda_calendar_view, EventAdapter(activity!!))

        contentManager.locale = Locale.forLanguageTag("es-ES")
        contentManager.setDateRange(minDate, maxDate)


        val maxLength = Calendar.getInstance().getMaximum(Calendar.DAY_OF_MONTH)

        for (i in 1..maxLength) {
            val day = Calendar.getInstance(Locale.forLanguageTag("es-ES"))
            day.timeInMillis = System.currentTimeMillis()
            day.set(Calendar.DAY_OF_MONTH, i)

            eventList.add(MyCalendarEvent(day, day,
                DayItem.buildDayItemFromCal(day), null).setEventInstanceDay(day))
        }

        contentManager.loadItemsFromStart(eventList)
        agenda_calendar_view.agendaView.agendaListView.setOnItemClickListener({ parent: AdapterView<*>, view: View, position: Int, id: Long ->
            Toast.makeText(view.context, "item: ".plus(position), Toast.LENGTH_SHORT).show()
        })

    }

    override fun onStop() {
        super.onStop()
        loadingTask?.cancel(true)
    }


    override fun getEmptyEventLayout() = R.layout.fragment_calendar_empty_event

    override fun getEventLayout() = R.layout.fragment_calendar_event

    override fun onDaySelected(dayItem: IDayItem) {
        showDialog(activity!!,"Pulsado un día",dayItem.date.toString())
        Toast.makeText(activity!!, "Pulsado un dia", Toast.LENGTH_LONG)
    }

    override fun onScrollToDate(calendar: Calendar) {
        val lastPosition = agenda_calendar_view.agendaView.agendaListView.lastVisiblePosition + 1

        val isSameDay = oldDate?.isSameDay(calendar) ?: false
        if (isSameDay && lastPosition == CalendarManager.getInstance(activity!!).events.size) {
            if (!agenda_calendar_view.isCalendarLoading()) { // condition to prevent asynchronous requests
                loadItemsAsync(false)
            }
        }

        if (agenda_calendar_view.agendaView.agendaListView.firstVisiblePosition == 0) {
            val minCal = Calendar.getInstance()
            minCal.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH))
            if (calendar.get(Calendar.DAY_OF_MONTH) == minCal.get(Calendar.DAY_OF_MONTH)) {
                if (!agenda_calendar_view.isCalendarLoading()) { // condition to prevent asynchronous requests
                    loadItemsAsync(true)
                }
            }
        }

        oldDate = calendar
    }

    private fun loadItemsAsync(addFromStart: Boolean) {
        loadingTask?.cancel(true)

        loadingTask = LoadingTask(addFromStart)
        loadingTask?.execute()
    }

    inner class LoadingTask(private val addFromStart: Boolean) : AsyncTask<Unit, Unit, Unit>() {

        private val startMonthCal: Calendar = Calendar.getInstance()
        private val endMonthCal: Calendar = Calendar.getInstance()

        override fun onPreExecute() {
            super.onPreExecute()
            agenda_calendar_view.showProgress()
            eventList.clear()
        }

        override fun doInBackground(vararg params: Unit?) {
            Thread.sleep(2000) // simulating requesting json via rest api

            if (addFromStart) {
                if (startMonth == 0) {
                    startMonth = 12
                } else {
                    startMonth--
                }

                startMonthCal.set(Calendar.MONTH, startMonth)
                if (startMonth == 12) {
                    var year = startMonthCal.get(Calendar.YEAR)
                    startMonthCal.set(Calendar.YEAR, ++year)
                }


                for (i in 1..startMonthCal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                    val day = Calendar.getInstance(Locale.forLanguageTag("es-ES"))
                    day.timeInMillis = System.currentTimeMillis()
                    day.set(Calendar.MONTH, startMonth)
                    day.set(Calendar.DAY_OF_MONTH, i)
                    if (endMonth == 12) {
                        day.set(Calendar.YEAR, day.get(Calendar.YEAR) - 1)
                    }

                    eventList.add(
                        MyCalendarEvent(
                            day, day,
                            DayItem.buildDayItemFromCal(day),
                            SampleEvent(name = "Extraordinario $i", description = "Evento $i")
                        )
                            .setEventInstanceDay(day)
                    )
                }
            } else {
                if (endMonth >= 12) {
                    endMonth = 0
                } else {
                    endMonth++
                }

                endMonthCal.set(Calendar.MONTH, endMonth)
                if (endMonth == 0) {
                    var year = endMonthCal.get(Calendar.YEAR)
                    endMonthCal.set(Calendar.YEAR, ++year)
                }

                for (i in 1..endMonthCal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                    val day = Calendar.getInstance(Locale.ENGLISH)
                    day.timeInMillis = System.currentTimeMillis()
                    day.set(Calendar.MONTH, endMonth)
                    day.set(Calendar.DAY_OF_MONTH, i)
                    if (endMonth == 0) {
                        day.set(Calendar.YEAR, day.get(Calendar.YEAR) + 1)
                    }

                    if (i % 4 == 0) {
                        val day1 = Calendar.getInstance()
                        day1.timeInMillis = System.currentTimeMillis()
                        day1.set(Calendar.MONTH, endMonth)
                        day1.set(Calendar.DAY_OF_MONTH, i)
                        eventList.add(
                            MyCalendarEvent(
                                day, day,
                                DayItem.buildDayItemFromCal(day),
                                SampleEvent(name = "Extraordinario $i", description = "Evento $i")
                            )
                                .setEventInstanceDay(day)
                        )
                    }

                    eventList.add(
                        MyCalendarEvent(
                            day, day,
                            DayItem.buildDayItemFromCal(day),
                            SampleEvent(name = "Extraordinario $i", description = "Evento $i")
                        )
                            .setEventInstanceDay(day)
                    )
                }
            }
        }

        override fun onPostExecute(user: Unit) {
            if (addFromStart) {
                contentManager.loadItemsFromStart(eventList)
            } else {
                contentManager.loadFromEndCalendar(eventList)
            }
            agenda_calendar_view.hideProgress()
        }
    }







}

class SampleEvent(name: String, description: String) {
    var id: Long = 0
    var name: String = name
    var desciption: String = description
}




