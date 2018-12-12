package net.forevents.foreventsandroid.presentation.SingUpLoginRecovery

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sing_up.*
import net.forevents.foreventsandroid.Data.CreateUser.User.AppUser
import net.forevents.foreventsandroid.presentation.Navigator.Navigator
import net.forevents.foreventsandroid.R
import net.forevents.foreventsandroid.R.id.login_user_text
import net.forevents.foreventsandroid.Util.*
import net.forevents.foreventsandroid.Util.Constants.PMANAGER_ID_USER
import net.forevents.foreventsandroid.Util.Constants.PMANAGER_TOKEN_USER
import net.forevents.foreventsandroid.presentation.Navigator.Navigator.openAnActivity


class LoginActivity : AppCompatActivity(),LifecycleOwner {

    companion object {
        val EXTRA_LOGIN_USER = "EXTRA_LOGIN_USER"
    }

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
            userViewModel.loginUser(login_user_text.text.toString(),login_password_text.text.toString())
            }
        recovery_password.setOnClickListener {
            openAnActivity(this,RecoveryPasswordActivity::class.java )
        }

        btn_register.setOnClickListener {
            openAnActivity(this,SingUpActivity::class.java )
        }




//        root_layout.setOnClickListener {
  //          cerrarTeclado()
    //    }

        setUpViewModel()
        login_user_text.setOnFocusChangeListener{ v, hasFocus ->
            if (!hasFocus) {
                hideKeyboard(v)
            }
        }
        login_password_text.setOnFocusChangeListener{ v, hasFocus ->
            if (!hasFocus) {
                hideKeyboard(v)
            }
        }

    }

    fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0)
    }



    lateinit var userViewModel: UserVM

    private  fun setUpViewModel(){
        userViewModel = ViewModelProviders.of(this).get(UserVM::class.java)
        bindEvents()
    }

    private fun bindEvents() {
        userViewModel.userState.observe(this, Observer { dataUser ->
            dataUser?.let {
                saveDataInPreferenceManager(it)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        login_user_text.requestFocus()
    }

    private fun saveDataInPreferenceManager(datUser:AppUser){
        setToPreferenceManagerTypeString(this, PMANAGER_ID_USER,datUser.id)
        setToPreferenceManagerTypeString(this, PMANAGER_TOKEN_USER,datUser.token)
        Navigator.OpenNucleusActivity(this)
    }
}
