package net.forevents.foreventsandroid.Util

import android.content.SharedPreferences

class SettingsManager(val sharedPreferences: SharedPreferences) {
    companion object {
        const val PREF_FIRST_LOAD = "PREF_FIRST_LOAD"
    }
    var firstLoad: Boolean
    get()=sharedPreferences.getBoolean(PREF_FIRST_LOAD,true)
    set(value){
        sharedPreferences
            .edit()
            .putBoolean(PREF_FIRST_LOAD,value)
            .apply()
    }
}