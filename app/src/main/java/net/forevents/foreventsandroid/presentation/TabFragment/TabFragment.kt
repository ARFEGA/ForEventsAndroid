package net.forevents.foreventsandroid.presentation.TabFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayout
import com.google.android.material.snackbar.Snackbar
import net.forevents.foreventsandroid.presentation.EventList.EventListFragment
import kotlinx.android.synthetic.main.activity_tab_show.*
import kotlinx.android.synthetic.main.fragment_list_events.*
import net.forevents.foreventsandroid.Data.CreateUser.User.AppEvents
import net.forevents.foreventsandroid.R
import net.forevents.foreventsandroid.presentation.Maps.FullMapFragment


class TabFragment : Fragment() , LifecycleOwner{
    companion object {
        val fragmentsForTabs = listOf(R.layout.fragment_list_events, R.layout.fragment_big_map, R.layout.fragment_big_map)
        val iconsForTabs = listOf(R.drawable.ic_party_filled_white_50, R.drawable.ic_map_marker_filled_white_50, R.drawable.ic_calendar_filled_white_50)
        val titleForTabs = listOf("Lista","Ubicación","Agenda")
        val EXTRA_EVENTS = "EXTRA_EVENTS"

        fun newInstance(events:List<AppEvents>):TabFragment{

            val fragment = TabFragment()
            val args = Bundle()
            args.putParcelableArrayList(EXTRA_EVENTS, ArrayList(events))
            fragment.arguments = args
            return fragment


        }

    }


    private lateinit var mLifecycleRegistry: LifecycleRegistry

    private  lateinit var events :List<AppEvents>
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null


    override fun onStart() {
        super.onStart()
        mLifecycleRegistry.markState(Lifecycle.State.STARTED)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mLifecycleRegistry = LifecycleRegistry(this)
        mLifecycleRegistry.markState(Lifecycle.State.CREATED)


    }


    override fun getLifecycle(): Lifecycle {
        return mLifecycleRegistry
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.activity_tab_show,container,false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        events =  (arguments?.getParcelableArrayList(EXTRA_EVENTS)!!)
    }
    override fun onResume() {
        super.onResume()
        mSectionsPagerAdapter = SectionsPagerAdapter(childFragmentManager)

        // Set up the ViewPager with the sections adapter.
        pager.adapter = mSectionsPagerAdapter

        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(group_tabs))
        group_tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(pager))

        //Todo:Hacer con map
        for(i in 0..group_tabs.tabCount -1)
            group_tabs.getTabAt(i)?.setIcon(iconsForTabs[i])?.setText(
                titleForTabs[i])

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }


    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return getFragment(position)
        }
        override fun getCount(): Int {
            return 3
        }
    }
    private fun getFragment(position: Int):Fragment{
        when(position){
            0-> return EventListFragment.newInstance(position,events)
            1-> return FullMapFragment.newInstance(position,events)
            else -> return EventListFragment.newInstance(position,events)
        }

    }



}




