package net.forevents.foreventsandroid.presentation.Calendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import net.forevents.foreventsandroid.R


class CalendarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        //calendar_events.
            net.forevents.foreventsandroid.Util.showDialog(this,"Datos calendar","")
        }
   // }
}
