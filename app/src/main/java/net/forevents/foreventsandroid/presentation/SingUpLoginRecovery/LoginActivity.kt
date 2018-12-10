package net.forevents.foreventsandroid.presentation.SingUpLoginRecovery


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*

import kotlinx.android.synthetic.main.activity_login.*

import net.forevents.foreventsandroid.Data.CreateUser.User.AppUser
import net.forevents.foreventsandroid.presentation.Navigator.Navigator
import net.forevents.foreventsandroid.R
import net.forevents.foreventsandroid.Util.*
import net.forevents.foreventsandroid.Util.Constants.PMANAGER_ID_USER
import net.forevents.foreventsandroid.Util.Constants.PMANAGER_TOKEN_USER
import net.forevents.foreventsandroid.presentation.Navigator.Navigator.openAnActivity


class LoginActivity : AppCompatActivity(),LifecycleOwner {

    companion object {

        val EXTRA_LOGIN_USER = "EXTRA_LOGIN_USER"
    }

    //val alias:String?
    //    get()= intent.getStringExtra(LoginActivity.EXTRA_LOGIN_USER)

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
        //setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_login)

        mLifecycleRegistry = LifecycleRegistry(this)
        mLifecycleRegistry.markState(Lifecycle.State.CREATED)

        btn_login.setOnClickListener {
            //Navigator.OpenNucleusActivity(this@LoginActivity)
            //login_user_text.setText("anisgaro.gr@gmail.com")
            //login_password_text.setText("1234aaaA")

            userViewModel.loginUser(login_user_text.text.toString(),login_password_text.text.toString())
            }
        recovery_password.setOnClickListener {
            openAnActivity(this,RecoveryPasswordActivity::class.java )
        }
        btn_register.setOnClickListener {
            openAnActivity(this,SingUpActivity::class.java )
            //val intent = Intent(this, SingUpActivity::class.java)
            //startActivity(intent)
        }

        setUpViewModel()

        //init()
    }
    lateinit var userViewModel: UserVM

   /* private fun setUpViewModel() {
        userViewModel = withViewModel {
            observe(userState) { dataUser ->
                dataUser?.let {
                    saveDataInPreferenceManager(it)
                    //showDialog(this@LoginActivity, "Datos desde API Message: ${it.ok}", "El Token: ${it.token}")

                }
            }

        }
    }*/

    private  fun setUpViewModel(){
        userViewModel = ViewModelProviders.of(this).get(UserVM::class.java)
        bindEvents()
        //userListViewModel.loadUserList()
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
       // showDialog(this,"","Desde resume")
    }


    private fun saveDataInPreferenceManager(datUser:AppUser){
        setToPreferenceManagerTypeString(this, PMANAGER_ID_USER,datUser.id)
        setToPreferenceManagerTypeString(this, PMANAGER_TOKEN_USER,datUser.token)
        //showDialog(this,"Data User from Preference Manager", "USER ID: ${getFromPreferenceManagerTypeString(this,PMANAGER_ID_USER)} \n TOKEN USER: ${getFromPreferenceManagerTypeString(this,PMANAGER_ID_USER)} ")
        Navigator.OpenNucleusActivity(this)
    }
    fun init()  {
        //ShowAlert(this,"4Events",alias)
        //alias?.let{
        //    Log.v("EXTRA",alias)
        //    login_user_text.setText(it)
        //    login_password_text.requestFocus()
        //    showDialog(this,"4Events","Bienvenido ${alias}")
       // }

    }


}
