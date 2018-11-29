package net.forevents.foreventsandroid.presentation.SingUpLoginRecovery


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import net.forevents.foreventsandroid.R
import net.forevents.foreventsandroid.Util.showDialog
import kotlinx.android.synthetic.main.activity_recovery_password.*



class RecoveryPasswordActivity : AppCompatActivity(),LifecycleOwner {



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
        setContentView(R.layout.activity_recovery_password)

        mLifecycleRegistry = LifecycleRegistry(this)
        mLifecycleRegistry.markState(Lifecycle.State.CREATED)

        btn_recovery_password.setOnClickListener {
            userViewModel.recoveryPassword(text_email_recovery_password.text.toString())
        }

        setupViewModel()
    }




    lateinit var userViewModel : UserVM

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
