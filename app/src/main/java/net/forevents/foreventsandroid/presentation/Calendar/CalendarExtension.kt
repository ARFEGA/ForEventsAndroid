package net.forevents.foreventsandroid.presentation.Calendar

import java.util.*

fun Calendar.isSameDay(newDate: Calendar): Boolean =
    this.get(Calendar.DAY_OF_MONTH) == newDate.get(Calendar.DAY_OF_MONTH)