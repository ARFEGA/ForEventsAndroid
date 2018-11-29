package net.forevents.foreventsandroid.Util

import android.content.Context
import android.preference.PreferenceManager

fun getFromPreferenceManagerTypestring(context: Context,key:String):String{
   return PreferenceManager.getDefaultSharedPreferences(context)
        .getString(key, "")


}

fun setToPreferenceManagerTypestring(context: Context, key:String,value:String){
    PreferenceManager.getDefaultSharedPreferences(context)
        .edit()
        .putString(key, value)
        .apply()
}