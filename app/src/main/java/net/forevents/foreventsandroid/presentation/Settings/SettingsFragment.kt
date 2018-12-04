package net.forevents.foreventsandroid.presentation.Settings

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.ColorFilter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.applandeo.materialcalendarview.R.drawable.background_transparent
import kotlinx.android.synthetic.main.fragment_settings.*
import net.forevents.foreventsandroid.Data.CreateUser.User.AppCity
import net.forevents.foreventsandroid.Data.CreateUser.User.AppEventType
import net.forevents.foreventsandroid.R
import net.forevents.foreventsandroid.R.id.city_autoComplete_TextView
import org.w3c.dom.Text


class SettingsFragment: Fragment() {

companion object {
    val cities_ = mutableListOf("Madrid","Lugo")
    val paises = mutableListOf("España","Francia")
}


    private lateinit var viewModel : SettingsListVM
    private lateinit var cities:List<AppCity>
    private lateinit var eventType:List<AppEventType>
    private   var listCities=  emptyList<String>().toMutableList()
    private  var adapter : ArrayAdapter<String>? = null
    private   var listEventType=  emptyList<String>().toMutableList()
    private  var adapterSpinner : ArrayAdapter<String>? = null

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

        //AutoCompleteText
        adapter = ArrayAdapter(context, android.R.layout.simple_gallery_item, listCities)
        city_autoComplete_TextView.setAdapter(adapter)
        with(city_autoComplete_TextView) {
            threshold = 1
            hint = "Introduce el nombre de la ciudad"
        }
        //Spinner
        adapterSpinner = ArrayAdapter(context, android.R.layout.simple_list_item_multiple_choice ,listEventType)
        adapterSpinner!!.setDropDownViewResource(android.R.layout.simple_list_item_checked)
        spinner_favorite_event.adapter = adapterSpinner
        with(spinner_favorite_event){
//            selectedItem()
            prompt="Selecciona tus eventos favoritos."

            //Para que no se dispare el evento nada más cargarse la pantalla, por aparecer seleccionado el primer elemento
            setSelection(0,false)
            setPopupBackgroundResource(R.color.orange_700)

        }


        viewModel.loadEventTypeList()


       // city_autoComplete_TextView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
       //     val selectedItem = parent.getItemAtPosition(position).toString()

       //     Toast.makeText(activity!!, "Selected : ${cities.get(position).city}", Toast.LENGTH_LONG).show()

       // }

        city_autoComplete_TextView.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(activity!!, "Selected : ${cities.get(position).city}", Toast.LENGTH_LONG).show()
                city_autoComplete_TextView.dismissDropDown()
            }

        }
       // city_autoComplete_TextView.setOnItemClickListener { parent, view, position, id ->
       // Toast.makeText(activity!!, "Selected : ${cities.get(position).city}", Toast.LENGTH_LONG).show()
       // }

        city_autoComplete_TextView.setOnDismissListener {
            //Toast.makeText(activity!!, "Suggestion closed", Toast.LENGTH_LONG).show()
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
        viewModel = ViewModelProviders.of(this).get(SettingsListVM::class.java)
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
                it.map { listCities.add("${it.city}       (${it.province})") }
                adapter!!.addAll(listCities)



            }
        })

        viewModel.eventTypeListState.observe(this, Observer { eventTypeList ->
            eventTypeList?.let{
                eventType = it
                adapterSpinner!!.clear()
                listEventType.clear()

                it.map { listEventType.add("${it.name}") }
                adapterSpinner!!.addAll(listEventType)

                it.map {
                    with(radio_group_button_event_type){
                        //setBackgroundResource(R.drawable.back_concert)
                        orientation= RadioGroup.VERTICAL
                        // create a radio button
                        val rb = RadioButton(activity!!)
                        // set text for the radio button
                        rb.text = it.name
                        rb.setTextSize(20f)
                        rb.setTextColor(Color.WHITE)
                        // assign an automatically generated id to the radio button
                        rb.id = View.generateViewId()
                        // add radio button to the radio group
                        addView(rb)
                    }
                }


            }
        })
    }


}



