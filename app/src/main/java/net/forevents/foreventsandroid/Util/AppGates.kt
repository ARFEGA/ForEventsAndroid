package net.forevents.foreventsandroid.Util

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import net.forevents.foreventsandroid.presentation.SingUpLoginRecovery.LoginActivity
import net.forevents.foreventsandroid.presentation.SingUpLoginRecovery.SingUpActivity

fun closeApp(){
   System.exit(0)
}

fun logOut(context:Context){
   removeAtPreferenceManagerTypeString(context, Constants.PMANAGER_TOKEN_USER)
   removeAtPreferenceManagerTypeString(context, Constants.PMANAGER_ID_USER)
   val intent = Intent(context, LoginActivity::class.java)
   context.startActivity(intent)
}

