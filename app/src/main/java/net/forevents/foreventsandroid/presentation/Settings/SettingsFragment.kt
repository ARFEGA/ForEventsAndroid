package net.forevents.foreventsandroid.presentation.Settings

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_settings.*
import net.forevents.foreventsandroid.Data.CreateUser.User.AppCity
import net.forevents.foreventsandroid.Data.CreateUser.User.AppEvents
import net.forevents.foreventsandroid.R
import net.forevents.foreventsandroid.R.id.city_autoComplete_TextView
import net.forevents.foreventsandroid.presentation.MainActivities.NucleusActivityVM
import net.forevents.foreventsandroid.presentation.TabFragment.TabFragment

class SettingsFragment: Fragment() {

companion object {
    val cities_ = mutableListOf("Madrid","Lugo")
    val paises = mutableListOf("España","Francia")
}


    private lateinit var viewModel : CityListVM
    private lateinit var cities:List<AppCity>
    private   var listCities=  emptyList<String>().toMutableList()
    private  var adapter : ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpViewModel()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_settings,container,false)



        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        adapter = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, listCities)
        city_autoComplete_TextView.setAdapter(adapter)
        city_autoComplete_TextView.threshold = 1

        city_autoComplete_TextView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            Toast.makeText(activity!!, "Selected : ${selectedItem}", Toast.LENGTH_LONG).show()

        }

        city_autoComplete_TextView.setOnDismissListener {
            Toast.makeText(activity!!, "Suggestion closed", Toast.LENGTH_LONG).show()
        }

        root_layout.setOnClickListener {

            val text = city_autoComplete_TextView.text
            Toast.makeText(activity!!, "SInputted : ${text}", Toast.LENGTH_LONG).show()
        }
        city_autoComplete_TextView.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                city_autoComplete_TextView.showDropDown()
            }
        }
        city_autoComplete_TextView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                viewModel.loadCitiestList(s.toString(),"50")







            }
        })

    }

    private  fun setUpViewModel(){
        viewModel = ViewModelProviders.of(this).get(CityListVM::class.java)
        bindEvents()
        //userListViewModel.loadUserList()
    }

    private fun bindEvents(){
         /*viewModel.isLoadingState.observe(this, Observer { isLoading ->
             isLoading?.let{
                 user_list_loading.visibility = if(it)  View.VISIBLE else View.GONE
             }
         })*/

        viewModel.citiesListState.observe(this, Observer { citiesList ->
            citiesList?.let{
                cities = it
                listCities.clear()
                adapter!!.clear()
                //adapter!!.notifyDataSetChanged()
                it.map { listCities.add("${it.city}       (${it.province})") }
                adapter!!.addAll(listCities)
                //adapter!!.notifyDataSetChanged()
                //adapter!!.clear()


            }
        })
    }


}



 //Getting the instance of AutoCompleteTextView AutoCompleteTextView actv= (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1); actv.setThreshold(1);//will start working from first character actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView actv.setTextColor(Color.RED); } @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the menu; this adds items to the action bar if it is present. getMenuInflater().inflate(R.menu.activity_main, menu); return true; } }