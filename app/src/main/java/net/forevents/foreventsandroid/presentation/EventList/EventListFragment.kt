package net.forevents.foreventsandroid.presentation.EventList



import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.Layout
import android.text.Layout.JUSTIFICATION_MODE_INTER_WORD
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_list_events.*
import kotlinx.android.synthetic.main.fragment_list_events.view.*
import kotlinx.android.synthetic.main.row_item.*
import net.forevents.foreventsandroid.Data.CreateUser.RandomUser.UserEntity
import net.forevents.foreventsandroid.Data.CreateUser.User.ApiEvents
import net.forevents.foreventsandroid.Data.CreateUser.User.AppEvents
import net.forevents.foreventsandroid.presentation.Navigator.Navigator
import net.forevents.foreventsandroid.R


class EventListFragment() : Fragment(), LifecycleOwner {

    companion object {

        //private val EXTRA_SECTION_NUMBER = "section_number"
        private val EXTRA_EVENTS="EXTRA_EVENTS"

        fun newInstance(events: List<AppEvents>): EventListFragment {
            val fragment = EventListFragment()
            val args = Bundle()
            //args.putInt(EXTRA_SECTION_NUMBER, sectionNumber)
            args.putParcelableArrayList(EXTRA_EVENTS, ArrayList(events))
            fragment.arguments = args
            return fragment
        }
    }


    private lateinit var mLifecycleRegistry: LifecycleRegistry
    private lateinit var eventTaped:AppEvents  //Event taped
    private lateinit var listEvents: List<AppEvents> //Events that comes from Tabfragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list_events, container, false)


        view.ghost_text.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                onEventClickedListener?.onEventClicked(eventTaped)
               // showDialog(activity!!, "Come from text box", "Come on"); true
            }
        })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




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
        //Las líneas anteriores, las sustituimos, por la llamada a un singleton
        eventTaped=appEvents
        //onEventClickedListener?.OnEventClicked(eventTaped)
        ghost_text.text = appEvents.id
        //Navigator.OpenEventDetail(activity!!,appEvents)
    }
    private fun setUpRecycler(){
        recycler_view.layoutManager = LinearLayoutManager(activity!!,RecyclerView.VERTICAL,false)
        recycler_view.itemAnimator = DefaultItemAnimator()
        recycler_view.adapter = adapter
    }
    //########## Code that's associated interface  ###############
    var onEventClickedListener:OnEventClickedListener? = null

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
    }

    override fun onDetach() {
        super.onDetach()
        onEventClickedListener = null
    }
//#################   interface for connect fragment with qctivity  ##############


    interface OnEventClickedListener{
        fun onEventClicked(event:AppEvents)
    }


    override fun onResume() {
        super.onResume()

    }
}



