package net.forevents.foreventsandroid.Pruebas


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager

import net.forevents.foreventsandroid.R

class MyOldEventsFragment : Fragment() {
    companion object {
        val ARG_INITIAL_CITY_INDEX = "ARG_INITIAL_CITY_INDEX"


        fun newInstance(initialCityIndex: Int): MyOldEventsFragment {
            val arguments = Bundle()
            arguments.putInt(ARG_INITIAL_CITY_INDEX, initialCityIndex)

            val fragment = MyOldEventsFragment()
            fragment.arguments = arguments

            return fragment
        }
    }

    lateinit var root: View
    lateinit var pager: ViewPager

    var initialCityIndex = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        inflater?.let {
            root = it.inflate(R.layout.fragment_my_events, container, false)

            /*

            // Operador Elvis!! oh yeah!
            initialCityIndex = arguments?.getInt(ARG_INITIAL_CITY_INDEX,0)!!

            pager = root.findViewById<ViewPager>(R.id.view_pager_my_events)
            val adapter = object: FragmentPagerAdapter(fragmentManager) {
                override fun getItem(position: Int): android.app.Fragment = ForecastFragment.newInstance(Cities[position])

                override fun getCount(): Int = Cities.count

                override fun getPageTitle(position: Int): CharSequence = Cities[position].name
            }

            // Le damos al viewPager su adapter para que muestre tantos fragments como diga el modelo
            pager.adapter = adapter

            pager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {}
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

                override fun onPageSelected(position: Int) {
                    updateCityInfo(position)
                }
            })

            moveToCity(initialCityIndex)
            updateCityInfo(initialCityIndex)
        }
        */
            return root
        }

        /* override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater?.inflate(R.menu.menu_pager, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when(item?.itemId) {
        R.id.previous -> {
            pager.currentItem = pager.currentItem - 1
            true
        }
        R.id.next -> {
            pager.currentItem = pager.currentItem + 1
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)

        menu?.findItem(R.id.previous)?.isEnabled = pager.currentItem > 0
        menu?.findItem(R.id.next)?.isEnabled = pager.currentItem < Cities.count - 1
    }

    fun moveToCity(position: Int) {
        pager.currentItem = position
    }

    fun updateCities() {
        pager.adapter.notifyDataSetChanged()
    }

    fun updateCityInfo(position: Int) {
        if (activity is AppCompatActivity) {
            val supportActionBar = (activity as? AppCompatActivity)?.supportActionBar
            supportActionBar?.title = Cities[position].name
        }
    }
    */
    }
}
