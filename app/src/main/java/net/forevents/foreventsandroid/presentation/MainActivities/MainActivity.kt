package net.forevents.foreventsandroid.presentation.MainActivities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*
import net.forevents.foreventsandroid.R
import net.forevents.foreventsandroid.Util.ShowAlert
import net.forevents.foreventsandroid.presentation.SingUpLoginRecovery.LoginActivity
import net.forevents.foreventsandroid.presentation.SingUpLoginRecovery.SingUpActivity


class MainActivity : Activity() {
companion object {
    val TAG = "TOKEN FIREBASE"
}

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        btn_register.setOnClickListener {
            val intent = Intent(this, SingUpActivity::class.java)
            startActivity(intent)
        }

        getTokenFireBase()
        subscribeFirebaseTopic()
    }

    private fun getTokenFireBase(){
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }
                // Get new Instance ID token
                val token = task.result?.token

                // Log and toast
                //val msg = getString(R.string.msg_token_fmt, token)
                Log.d(TAG, token)
                Toast.makeText(this@MainActivity, token, Toast.LENGTH_SHORT).show()
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
                /*val token_a = "eLM7ZeTEkwA:APA91bETqgO6VJLVGlOHm1g03oWOcQfYSzFxu5huYW46q8eoV4wH8NSZpCRNPLSJO-wkKrcL968Jpu2uoqw0EIPITtSzpHgwYBvwWtPfQnb6-YlGCQ5k4woyoVCvNddyXUMOWI_IIOFA"
                val token_b = "eLM7ZeTEkwA:APA91bETqgO6VJLVGlOHm1g03oWOcQfYSzFxu5huYW46q8eoV4wH8NSZpCRNPLSJO-wkKrcL968Jpu2uoqw0EIPITtSzpHgwYBvwWtPfQnb6-YlGCQ5k4woyoVCvNddyXUMOWI_IIOFA"
                val token_c = "eLM7ZeTEkwA:APA91bETqgO6VJLVGlOHm1g03oWOcQfYSzFxu5huYW46q8eoV4wH8NSZpCRNPLSJO-wkKrcL968Jpu2uoqw0EIPITtSzpHgwYBvwWtPfQnb6-YlGCQ5k4woyoVCvNddyXUMOWI_IIOFA"
                val token_d = "eLM7ZeTEkwA:APA91bETqgO6VJLVGlOHm1g03oWOcQfYSzFxu5huYW46q8eoV4wH8NSZpCRNPLSJO-wkKrcL968Jpu2uoqw0EIPITtSzpHgwYBvwWtPfQnb6-YlGCQ5k4woyoVCvNddyXUMOWI_IIOFA"
                  if (token_a == token_b) ShowAlert(this,"Token Firebase","Token Iguales")
                  */
                Log.d(TAG, msg)
                Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
            }

    }
//eLM7ZeTEkwA:APA91bETqgO6VJLVGlOHm1g03oWOcQfYSzFxu5huYW46q8eoV4wH8NSZpCRNPLSJO-wkKrcL968Jpu2uoqw0EIPITtSzpHgwYBvwWtPfQnb6-YlGCQ5k4woyoVCvNddyXUMOWI_IIOFA
    //eLM7ZeTEkwA:APA91bETqgO6VJLVGlOHm1g03oWOcQfYSzFxu5huYW46q8eoV4wH8NSZpCRNPLSJO-wkKrcL968Jpu2uoqw0EIPITtSzpHgwYBvwWtPfQnb6-YlGCQ5k4woyoVCvNddyXUMOWI_IIOFA
}
