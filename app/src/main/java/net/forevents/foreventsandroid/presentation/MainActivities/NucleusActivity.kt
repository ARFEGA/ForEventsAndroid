package net.forevents.foreventsandroid.presentation.MainActivities

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.*
import kotlinx.android.synthetic.main.activity_nucleus_activity.*
import kotlinx.android.synthetic.main.app_bar_nucleus_activity.*
import kotlinx.android.synthetic.main.fragment_list_events.*
import net.forevents.foreventsandroid.Data.CreateUser.User.AppEvents
import net.forevents.foreventsandroid.R
import net.forevents.foreventsandroid.R.id.*
import net.forevents.foreventsandroid.UpdateProfileFragment
import net.forevents.foreventsandroid.presentation.EventDetail.EventDetailFragment
import net.forevents.foreventsandroid.presentation.EventList.EventListFragment
import net.forevents.foreventsandroid.presentation.Maps.FullMapFragment
import net.forevents.foreventsandroid.presentation.MyEventsFragment
import net.forevents.foreventsandroid.presentation.SearchFragment
import net.forevents.foreventsandroid.presentation.TabFragment.TabFragment
import net.forevents.foreventsandroid.presentation.TabFragment.TabVM

class NucleusActivity : AppCompatActivity(),LifecycleOwner, NavigationView.OnNavigationItemSelectedListener ,EventListFragment.OnEventClickedListener{

    private lateinit var mLifecycleRegistry: LifecycleRegistry
    private lateinit var viewModel : NucleusActivityVM
    private lateinit var events:List<AppEvents>

    //Call to EventDetailFragment
    override fun OnEventClicked(event: AppEvents) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.content_fragment,
                EventDetailFragment.newInstance(event)
            ).commit()    }

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

        mLifecycleRegistry = LifecycleRegistry(this)
        mLifecycleRegistry.markState(Lifecycle.State.CREATED)

        setSupportActionBar(toolbar)
        setUpViewModel()
        viewModel.loadEventList()



        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    private  fun setUpViewModel(){
        viewModel = ViewModelProviders.of(this).get(NucleusActivityVM::class.java)
        bindEvents()
        //userListViewModel.loadUserList()
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
    }

    override fun onResume() {
        super.onResume()
    }



    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
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
            R.id.action_settings -> {
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.content_fragment,
                        SearchFragment()
                    ).commit()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_gallery -> {
                viewModel.loadEventList()
            }
            R.id.nav_camera -> {
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.content_fragment,
                        MyEventsFragment()
                    ).commit()
            }

            R.id.nav_settings_searches -> {
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.content_fragment,
                        MyEventsFragment()
                    ).commit()
        }
            R.id.nav_profile_update-> {
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.content_fragment,
                        UpdateProfileFragment()
                    ).commit()
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
                FullMapFragment.newInstance(1,event)
            ).commit()
    }


}
