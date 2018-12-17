package net.forevents.foreventsandroid.presentation.EventList



import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_list_events.*
import net.forevents.foreventsandroid.Data.Model.Events.AppEvents
import net.forevents.foreventsandroid.R



class EventListFragment : Fragment(), LifecycleOwner {

    companion object {

        private val EXTRA_EVENTS="EXTRA_EVENTS"
        fun newInstance(events: List<AppEvents>): EventListFragment {
            val fragment = EventListFragment()
            val args = Bundle()
            args.putParcelableArrayList(EXTRA_EVENTS, ArrayList(events))
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var mLifecycleRegistry: LifecycleRegistry
    private lateinit var listEvents: List<AppEvents> //Events that comes from Tabfragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_events, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipe_refresh_events.setOnRefreshListener {
            //swipe_refresh_events.setEnabled(false) //disable/enabled
            swipe_refresh_events.setColorSchemeResources(R.color.colorPrimary)
            swipe_refresh_events.setProgressBackgroundColorSchemeResource(R.color.orange_700)
            onRefreshEventsListener?.onSwipeRefreshEvents()
        }

        mLifecycleRegistry = LifecycleRegistry(this)
        mLifecycleRegistry.markState(Lifecycle.State.CREATED)
        val auxListEvents:ArrayList<AppEvents> = arguments?.getParcelableArrayList(EXTRA_EVENTS)!!

        listEvents= auxListEvents.toList()
        setUpRecycler()
        adapter.submitList(listEvents)
    }

    override fun onStart() {
        super.onStart()
        mLifecycleRegistry.markState(Lifecycle.State.STARTED)
    }

    override fun getLifecycle(): Lifecycle {
        return mLifecycleRegistry
    }

    private val adapter = RecyclerAdapter { onEventClick(it) }

    private fun onEventClick(appEvents: AppEvents){
        onEventClickedListener?.onEventClicked(appEvents)
    }
    private fun setUpRecycler(){
        recycler_view.layoutManager = LinearLayoutManager(activity!!,RecyclerView.VERTICAL,false)
        recycler_view.itemAnimator = DefaultItemAnimator()
        recycler_view.adapter = adapter
        //It's triggered on bottom reached
        adapter.setterOnBottomReachedListener(object : OnBottomReachedListener {
            override fun onBottomReached(position: Int) {
                //onRefreshEventsListener?.onSwipeRefreshEvents()
                Toast.makeText(activity!!,"I'm on the bottom list",Toast.LENGTH_LONG).show()
            }
        })

    }
    //########## Code that's associated interface  ###############
    var onEventClickedListener:OnEventClickedListener? = null
    var onRefreshEventsListener:OnRefreshEventsListener? = null


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        commonAttach(context as? Activity)
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        commonAttach(activity)
    }

    fun commonAttach(activity: Activity?){
        if(activity is OnEventClickedListener)
            onEventClickedListener = activity
        else onEventClickedListener = null
        if(activity is OnRefreshEventsListener)
            onRefreshEventsListener = activity
        else onRefreshEventsListener = null

    }

    override fun onDetach() {
        super.onDetach()
        onEventClickedListener = null
        onRefreshEventsListener = null

    }
//#################   interface for connect fragment with activity  ##############
    interface OnRefreshEventsListener{
        fun onSwipeRefreshEvents()
 }

    interface OnEventClickedListener{
        fun onEventClicked(event:AppEvents)
    }
}



