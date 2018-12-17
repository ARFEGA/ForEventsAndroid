package net.forevents.foreventsandroid.Util

import android.content.Context
import android.content.SyncContext
import android.preference.PreferenceManager
import com.google.gson.Gson
import net.forevents.foreventsandroid.Data.Model.PreferenceSearches
import net.forevents.foreventsandroid.presentation.Settings.SettingsFragment


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

fun getSearchPreferencesFromPManager(context:Context):PreferenceSearches?{
    var preferenceSearch : PreferenceSearches? = null
    val gson:Gson
    val jsonToFromPManager = getFromPreferenceManagerTypeString(context, Constants.PMANAGER_FAVORITE_PARAMETERS_SEARCH)
    jsonToFromPManager?.let {
        gson =  Gson()
        preferenceSearch =  gson.fromJson(jsonToFromPManager,PreferenceSearches::class.java)
    }
    return preferenceSearch
}

/*fun setToPrefenceManagerTypeList(context:Context,key:String , preferenceSearch:PreferenceSearches){
    PreferenceManager.getDefaultSharedPreferences(context)
        .edit()
        .putList(preferenceSearch)
}*/