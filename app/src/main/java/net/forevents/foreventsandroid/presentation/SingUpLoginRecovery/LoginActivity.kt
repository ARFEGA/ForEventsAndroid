package net.forevents.foreventsandroid.presentation.SingUpLoginRecovery


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*

import kotlinx.android.synthetic.main.activity_login.*
import net.forevents.foreventsandroid.presentation.Navigator.Navigator
import net.forevents.foreventsandroid.R
import net.forevents.foreventsandroid.Util.observe
import net.forevents.foreventsandroid.Util.showDialog
import net.forevents.foreventsandroid.Util.withViewModel
import net.forevents.foreventsandroid.presentation.Navigator.Navigator.openAnActivity


class LoginActivity : AppCompatActivity(),LifecycleOwner {

    companion object {
        val EXTRA_LOGIN_USER = "LOGIN_USER"
    }

    val alias:String?
        get()= intent.getStringExtra(LoginActivity.EXTRA_LOGIN_USER)

    private lateinit var mLifecycleRegistry: LifecycleRegistry

    override fun onStart() {
        super.onStart()
        mLifecycleRegistry.markState(Lifecycle.State.STARTED)
    }

    override fun getLifecycle(): Lifecycle {
        return mLifecycleRegistry
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mLifecycleRegistry = LifecycleRegistry(this)
        mLifecycleRegistry.markState(Lifecycle.State.CREATED)

        btn_login.setOnClickListener {
            Navigator.OpenNucleusActivity(this@LoginActivity)
            //userViewModel.loginUser(login_user_text.text.toString(),login_password_text.text.toString())
            }
        recovery_password.setOnClickListener {
            openAnActivity(this,RecoveryPasswordActivity::class.java )
        }

        setUpViewModel()

        //init()
    }
    lateinit var userViewModel: UserVM

    private fun setUpViewModel() {
        userViewModel = withViewModel {
            observe(userState) { dataUser ->
                dataUser?.let {
                    showDialog(this@LoginActivity, "Datos desde API Message: ${it.ok}", "El Token: ${it.token}")
                    Navigator.OpenNucleusActivity(this@LoginActivity)
                }
            }

        }
    }

    fun init()  {
        //ShowAlert(this,"4Events",alias)
        alias?.let{
            Log.v("EXTRA",alias)
            login_user_text.setText(it)
            login_password_text.requestFocus()
            showDialog(this,"4Events","Bienvenido ${alias}")
        }

    }

    override fun onResume() {
        super.onResume()

    }

}
