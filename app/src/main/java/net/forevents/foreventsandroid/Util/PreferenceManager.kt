package net.forevents.foreventsandroid.Util

import android.content.Context
import android.content.SyncContext
import android.preference.PreferenceManager
import net.forevents.foreventsandroid.Data.Model.PreferenceSearches


fun getFromPreferenceManagerTypeString(context: Context, key:String):String?{
   return PreferenceManager.getDefaultSharedPreferences(context)
        .getString(key, "")


}

fun setToPreferenceManagerTypeString(context: Context, key:String, value:String){
    PreferenceManager.getDefaultSharedPreferences(context)
        .edit()
        .putString(key, value)
        .apply()
}

fun removeAtPreferenceManagerTypeString(context: Context, key:String){
    PreferenceManager.getDefaultSharedPreferences(context)
        .edit()
        .remove(key)
        .apply()
}

/*fun setToPrefenceManagerTypeList(context:Context,key:String , preferenceSearch:PreferenceSearches){
    PreferenceManager.getDefaultSharedPreferences(context)
        .edit()
        .putList(preferenceSearch)
}*/