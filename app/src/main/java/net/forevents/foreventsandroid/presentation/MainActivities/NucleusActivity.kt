package net.forevents.foreventsandroid.presentation.MainActivities

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.*
import kotlinx.android.synthetic.main.activity_nucleus_activity.*
import kotlinx.android.synthetic.main.app_bar_nucleus_activity.*
import net.forevents.foreventsandroid.Data.CreateUser.User.AppEvents
import net.forevents.foreventsandroid.Data.Model.Transactions.AppTransactions
import net.forevents.foreventsandroid.R
import net.forevents.foreventsandroid.Util.SplashFragment
import net.forevents.foreventsandroid.Util.Constants.FIELDS_MEDIA
import net.forevents.foreventsandroid.Util.Constants.PMANAGER_ID_USER
import net.forevents.foreventsandroid.Util.Constants.PMANAGER_TOKEN_USER
import net.forevents.foreventsandroid.Util.getFromPreferenceManagerTypeString
import net.forevents.foreventsandroid.Util.logOut
import net.forevents.foreventsandroid.Util.removeAtPreferenceManagerTypeString
import net.forevents.foreventsandroid.presentation.EventDetail.EventDetailFragment
import net.forevents.foreventsandroid.presentation.EventList.EventListFragment
import net.forevents.foreventsandroid.presentation.MyEvents.MyEventsFragment
import net.forevents.foreventsandroid.presentation.Maps.FullMapFragment
import net.forevents.foreventsandroid.presentation.Settings.SettingsFragment
import net.forevents.foreventsandroid.presentation.SingUpLoginRecovery.LoginActivity
import net.forevents.foreventsandroid.presentation.SingUpLoginRecovery.UpdateUserFragment
import net.forevents.foreventsandroid.presentation.TabFragment.TabFragment

class NucleusActivity : AppCompatActivity(),
    LifecycleOwner,
    NavigationView.OnNavigationItemSelectedListener ,
    EventListFragment.OnEventClickedListener,
    EventDetailFragment.OnBookButtonClickedListenerDetailFragment,
    MyEventsFragment.OnListFragmentInteractionListener {



    override fun onListFragmentInteraction(event: AppTransactions) {
        //Toast.makeText(this,event.toString(),Toast.LENGTH_LONG).show()
        viewModel.getEvent(FIELDS_MEDIA,event.eventId,userId)
    }

    private lateinit var mLifecycleRegistry: LifecycleRegistry
    private lateinit var viewModel : NucleusActivityVM
    private lateinit var events:List<AppEvents>
    private lateinit var userId:String
    private lateinit var token:String

    override fun onStart() {
        super.onStart()
        mLifecycleRegistry.markState(Lifecycle.State.STARTED)
    }
    override fun getLifecycle(): Lifecycle {
        return mLifecycleRegistry
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nucleus_activity)
        userId=getFromPreferenceManagerTypeString(this, PMANAGER_ID_USER)!!
        token= getFromPreferenceManagerTypeString(this, PMANAGER_TOKEN_USER)!!
        mLifecycleRegistry = LifecycleRegistry(this)
        mLifecycleRegistry.markState(Lifecycle.State.CREATED)

        setSupportActionBar(toolbar)
        setUpViewModel()
        viewModel.loadEventList(userId)


        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        this.title = "Eventos disponibles"

    }

    private  fun setUpViewModel(){
        viewModel = ViewModelProviders.of(this).get(NucleusActivityVM::class.java)
        bindEvents()
    }

    private fun bindEvents(){
       /* viewModel.isLoadingState.observe(this, Observer { isLoading ->
            isLoading?.let{
                user_list_loading.visibility = if(it)  View.VISIBLE else View.GONE
            }
        })*/

        viewModel.eventListState.observe(this, Observer { eventsList ->
            eventsList?.let{
                events = it
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.content_fragment,
                        TabFragment.newInstance(it)
                    ).commit()
            }
        })

        viewModel.deleteUserState.observe(this, Observer {deleteState ->
            deleteState?.let{
                Toast.makeText(this,"PERFIL ELIMINADO CORRECTAMENTE",Toast.LENGTH_LONG).show()
                ñapa()
                logOut(this)
                finish()
            }
        })

        viewModel.saveCreateTransactionState.observe(this, Observer { saveState->
            saveState?.let {
                Toast.makeText(this,"RESERVA REALIZADA",Toast.LENGTH_LONG).show()
                //EventDetailFragment().responseFromSaveTransation(true)
            }
        })

        viewModel.deleteTransactionState.observe(this, Observer { deleteState ->
            deleteState?.let {
                Toast.makeText(this,"RESERVA ANULADA",Toast.LENGTH_LONG).show()

            }
         })
        viewModel.getTransactionsByUserState.observe(this, Observer { myEvents->
            myEvents?.let {
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.content_fragment,
                        MyEventsFragment.newInstance(it)
                    ).commit()

            }
        })
        viewModel.getEventState.observe(this, Observer { event->
            event?.let {
                openDetailScreen(it)
            }
        })
    }
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            ñapa()
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.nucleus_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_logout -> {
                ñapa()
                removeAtPreferenceManagerTypeString(this,PMANAGER_TOKEN_USER)
                val intent = Intent(this, LoginActivity::class.java)
                this.startActivity(intent)
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_gallery -> {
                viewModel.loadEventList(userId)
            }
            R.id.nav_mis_eventos -> {
                this.title ="Eventos Reservados"
                viewModel.getTransactionsByUser(token)
            }

            R.id.nav_settings_searches -> {
                this.title ="Configuración busquedas"
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.content_fragment,
                        SettingsFragment()
                    ).commit()
            }
            R.id.nav_favorite_searches -> {
                this.title ="Busquedas favoritas"
               viewModel.getTransactionsByUser(token)
            }

            R.id.nav_profile_update-> {

                this.title ="Actualización perfil"
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.content_fragment,
                        UpdateUserFragment()
                    ).commit()
            }
            R.id.nav_profile_delete ->{
                this.title ="Borrado de perfil"
                showDeleteDialog(this)
            }
            R.id.nav_share -> {

                Toast.makeText(this,"nav_share", Toast.LENGTH_LONG).show()
            }
            R.id.nav_send -> {
                Toast.makeText(this,"nav_send", Toast.LENGTH_LONG).show()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun openFullScreenMap(event:List<AppEvents>){
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.content_fragment,
                FullMapFragment.newInstance(event)
            ).commit()
    }

    override fun onBookBtnClickedFragmentDetail(typeAction: String,eventOrTransactionID: String) {
        when(typeAction){
            "del"->{
                viewModel.delTransaction(
                    getFromPreferenceManagerTypeString(this,PMANAGER_TOKEN_USER)!!,
                    eventOrTransactionID)
            }
            "save"->{
                viewModel.saveTransaction(
                    getFromPreferenceManagerTypeString(this,PMANAGER_TOKEN_USER)!!,
                    eventOrTransactionID)
            }
        }

    }

    fun openDetailScreen(event: AppEvents){
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.content_fragment,
                EventDetailFragment.newInstance(event)
            ).commit()
    }

    fun showDeleteDialog(context: Context){
        // Late initialize an alert dialog object
        lateinit var dialog: androidx.appcompat.app.AlertDialog
        context.setTheme(R.style.AlertDialogDeleteUser)
        // Initialize a new instance of alert dialog builder object
        val builder = androidx.appcompat.app.AlertDialog.Builder(context)
        //ContextThemeWrapper(context, R.style.AlertDialogCustom))
        // Set a title for alert dialog
        builder.setTitle(R.string.title_alert_delete_profile)
        // Set a message for alert dialog
        builder.setMessage(R.string.msg_alert_delete_profile)
        // On click listener for dialog buttons
        val resultDialogClickListener = DialogInterface.OnClickListener{ _, which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE ->
                   viewModel.deleteProfile(
                        getFromPreferenceManagerTypeString(this,PMANAGER_ID_USER)!!,
                        getFromPreferenceManagerTypeString(this,PMANAGER_TOKEN_USER)!!
                    )
                //DialogInterface.BUTTON_NEGATIVE -> Toast.makeText(context,"ENJOY IT",Toast.LENGTH_LONG)
                DialogInterface.BUTTON_NEUTRAL -> {}
            }
        }
        // Set the alert dialog positive/yes button
        builder.setPositiveButton("BORRAR PERFIL",resultDialogClickListener)
        builder.setNeutralButton("CANCELAR",resultDialogClickListener)
        // Initialize the AlertDialog using builder object
        dialog = builder.create()
        dialog.setIcon(R.drawable.delete_user)
        // Finally, display the alert dialog
        dialog.show()
    }

    //Call to EventDetailFragment
    override fun onEventClicked(event: AppEvents) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.content_fragment,
                EventDetailFragment.newInstance(event)
            ).commit()    }
    private fun ñapa(){
        //TODO investigar la causa por la que si no asigno un fragment distinto al de tab's, al volver a la pantalla de login
        //Todo no puedo interactuar con ella
        supportFragmentManager.beginTransaction().replace(R.id.content_fragment,
            SplashFragment()
        ).commit()
    }

    fun logOut(){
        ñapa()
        logOut(this)
    }




}
