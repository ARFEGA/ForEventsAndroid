package net.forevents.foreventsandroid.Util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import kotlinx.android.synthetic.main.activity_login.*
import net.forevents.foreventsandroid.R.id.login_user_text
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



