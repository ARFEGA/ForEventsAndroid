package net.forevents.foreventsandroid.presentation.MainActivities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.GravityCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_nucleus_activity.*
import net.forevents.foreventsandroid.R
import net.forevents.foreventsandroid.R.id.btn_login
import net.forevents.foreventsandroid.R.id.btn_register
import net.forevents.foreventsandroid.Util.Constants.PMANAGER_TOKEN_USER
import net.forevents.foreventsandroid.Util.getFromPreferenceManagerTypeString
import net.forevents.foreventsandroid.Util.setToPreferenceManagerTypeString
import net.forevents.foreventsandroid.presentation.MainActivities.MainActivity.Companion.TOKEN_FIREBASE
import net.forevents.foreventsandroid.presentation.Navigator.Navigator
import net.forevents.foreventsandroid.presentation.SingUpLoginRecovery.LoginActivity
import net.forevents.foreventsandroid.presentation.SingUpLoginRecovery.SingUpActivity


class MainActivity : AppCompatActivity() {
companion object {
    val TOKEN_FIREBASE = "TOKEN_FIREBASE"
}
    override fun onBackPressed() {
        //super.onBackPressed()
        return
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        //Stetho.initializeWithDefaults(this)

        setTheme(R.style.EventsTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getTokenFireBase()
        //subscribeFirebaseTopic()
        //If there's token, go to nucleusActivity
        getFromPreferenceManagerTypeString(this,PMANAGER_TOKEN_USER)?.let {
            if(it != "") {
                Log.d("PREFERENCE_MANAGER:", it)
                Navigator.OpenNucleusActivity(this)
            }
        }





        btn_login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        btn_register.setOnClickListener {
            val intent = Intent(this, SingUpActivity::class.java)
            startActivity(intent)
        }

    }

    private fun getTokenFireBase(){
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TOKEN_FIREBASE, "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }
                // Get new Instance ID token
                val token = task.result?.token
                Log.d(TOKEN_FIREBASE, token)
                token?.let {
                    setToPreferenceManagerTypeString(this,TOKEN_FIREBASE,token)
                    Log.d(TOKEN_FIREBASE, token)
                }

            })
    }

    private fun subscribeFirebaseTopic(){
        //Subscription to the topic News
        FirebaseMessaging.getInstance().subscribeToTopic("News")
            .addOnCompleteListener { task ->
                var msg = getString(R.string.msg_subscribed)
                if (!task.isSuccessful) {
                    msg = getString(R.string.msg_subscribe_failed)
                }
                Log.d(TOKEN_FIREBASE, msg)
                Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
            }
    }
}
