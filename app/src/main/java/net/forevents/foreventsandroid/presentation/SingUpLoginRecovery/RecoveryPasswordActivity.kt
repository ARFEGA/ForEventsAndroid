package net.forevents.foreventsandroid.presentation.SingUpLoginRecovery


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import kotlinx.android.synthetic.main.activity_login.*
import net.forevents.foreventsandroid.R
import net.forevents.foreventsandroid.Util.showDialog
import kotlinx.android.synthetic.main.activity_recovery_password.*



class RecoveryPasswordActivity : AppCompatActivity(),LifecycleOwner {

    private lateinit var mLifecycleRegistry: LifecycleRegistry
    lateinit var userViewModel : UserVM

    override fun onStart() {
        super.onStart()
        mLifecycleRegistry.markState(Lifecycle.State.STARTED)
    }

    override fun getLifecycle(): Lifecycle {
        return mLifecycleRegistry
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recovery_password)

        mLifecycleRegistry = LifecycleRegistry(this)
        mLifecycleRegistry.markState(Lifecycle.State.CREATED)

        btn_recovery_password.setOnClickListener {
            userViewModel.recoveryPassword(text_email_recovery_password.text.toString())
        }

        text_email_recovery_password.setOnFocusChangeListener{ v, hasFocus ->
            if (!hasFocus)  hideKeyboard(v)
        }
        setupViewModel()
    }

    fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }



    private fun setupViewModel(){
        userViewModel = ViewModelProviders.of(this).get(UserVM::class.java)
        BindEvents()
    }
    private fun BindEvents(){
        userViewModel.recoveryPasswordState.observe(this, Observer { userCreated->
            userCreated?.let {
                showDialog(this,"'CREATE' Datos desde API ","El EMAIL: ${it}")
                finish()
            }
        })
    }

}
