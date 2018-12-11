package net.forevents.foreventsandroid.presentation.MyEvents

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.forevents.foreventsandroid.Data.CreateUser.User.AppEvents
import net.forevents.foreventsandroid.R
import net.forevents.foreventsandroid.presentation.EventList.EventListFragment

import net.forevents.foreventsandroid.presentation.MyEvents.dummy.DummyContent
import net.forevents.foreventsandroid.presentation.MyEvents.dummy.DummyContent.DummyItem

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [MyEventsFragment.OnListFragmentInteractionListener] interface.
 */
class MyEventsFragment : Fragment() {

    companion object {
        private val EXTRA_EVENTS="EXTRA_EVENTS"

        fun newInstance(events: List<AppEvents>): MyEventsFragment {
            val fragment = MyEventsFragment()
            val args = Bundle()
            args.putParcelableArrayList(EXTRA_EVENTS, ArrayList(events))
            fragment.arguments = args
            return fragment
        }
    }


    // TODO: Customize parameters

    private lateinit var listEvents: List<AppEvents>

    private var bookingEvents = 1

    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        val auxListEvents:ArrayList<AppEvents> = arguments?.getParcelableArrayList(EXTRA_EVENTS)!!

        listEvents= auxListEvents.toList()


        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    bookingEvents <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, bookingEvents)
                }
                adapter = MyItemRecyclerViewAdapter(
                    listEvents,
                    listener
                )
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: AppEvents?)
    }

}
