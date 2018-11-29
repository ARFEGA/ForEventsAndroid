package net.forevents.foreventsandroid.presentation.SingUpLoginRecovery


import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.*
import kotlinx.android.synthetic.main.activity_sing_up.*
import net.forevents.foreventsandroid.R

import net.forevents.foreventsandroid.Util.showDialog


class SingUpActivity : AppCompatActivity() , LifecycleOwner {

    private lateinit var mLifecycleRegistry: LifecycleRegistry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)

        mLifecycleRegistry = LifecycleRegistry(this)
        mLifecycleRegistry.markState(Lifecycle.State.CREATED)

        create_btn_sing_up.setOnClickListener {

            //todo Asegurar la información necesaria
            //todo registrar user en api
            //todo notificar el resultado del alta y pasar a pantalla de login si el alta ha sido satisfactoria
            if(true){
                userViewModel.createUser(
                    create_email_text.text.toString(),
                    create_pass_text.text.toString(),
                    create_name_text.text.toString(),
                    create_last_name_text.text.toString(),
                    create_address_text.text.toString(),
                    create_city_text.text.toString(),
                    create_zip_code_text.text.toString(),
                    create_province_text.text.toString(),
                    create_country_text.text.toString(),
                    create_alias_text.text.toString())
                //todo pasar parametro alias a la pantalla login par que el user no tenga que introducirlo y situarle en pws
                //Navigator.OpenLogin(this,alias_text.text.toString())
            }else{
                setResult(Activity.RESULT_CANCELED)
                finish()
            }

        }
        setupViewModel()

    }

    override fun onStart() {
        super.onStart()

        mLifecycleRegistry.markState(Lifecycle.State.STARTED)
    }

    override fun getLifecycle(): Lifecycle {
        return mLifecycleRegistry
    }





    lateinit var userViewModel : UserVM

    private fun setupViewModel(){
        userViewModel = ViewModelProviders.of(this).get(UserVM::class.java)
        BindEvents()
    }
    private fun BindEvents(){
        userViewModel.createUserState.observe(this, Observer {userCreated->
            userCreated?.let {
                showDialog(this,"'CREATE' Datos desde API ","Message: ${it.message} El id: ${it.id}")
            }
        })
    }
}
