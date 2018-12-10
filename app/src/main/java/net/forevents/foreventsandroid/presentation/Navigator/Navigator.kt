package net.forevents.foreventsandroid.presentation.Navigator

import android.app.Activity
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import net.forevents.foreventsandroid.presentation.MainActivities.NucleusActivity
import net.forevents.foreventsandroid.presentation.SingUpLoginRecovery.LoginActivity


object Navigator {


    fun <T:Any>openAnActivity(activityOrigin:Activity,activityDestiny:Class<T>){
        val intent = Intent(activityOrigin,activityDestiny)
        activityOrigin.startActivity(intent)
    }

    fun OpenLogin(activityOrigin:Activity, alias: String){
        val intent = Intent(activityOrigin,
            LoginActivity::class.java)
        intent.putExtra(LoginActivity.EXTRA_LOGIN_USER,alias)
        activityOrigin.startActivity(intent)
    }


    fun OpenNucleusActivity(activityOrigin:Activity){
        val intent = Intent(activityOrigin,
            NucleusActivity::class.java)
        activityOrigin.startActivity(intent)
    }

}