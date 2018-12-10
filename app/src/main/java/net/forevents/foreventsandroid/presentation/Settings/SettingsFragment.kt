package net.forevents.foreventsandroid.presentation.Settings

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.Selection.setSelection
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_calendar_event.view.*
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.nav_header_nucleus_activity.view.*
import kotlinx.android.synthetic.main.toast_bienvenida.view.*
import net.forevents.foreventsandroid.Data.CreateUser.User.AppCity
import net.forevents.foreventsandroid.Data.CreateUser.User.AppEventType
import net.forevents.foreventsandroid.Data.Model.DataCity
import net.forevents.foreventsandroid.Data.Model.PreferenceSearches
import net.forevents.foreventsandroid.R
import net.forevents.foreventsandroid.R.id.*
import net.forevents.foreventsandroid.Util.Constants.PMANAGER_FAVORITE_PARAMETERS_SEARCH
import net.forevents.foreventsandroid.Util.getFromPreferenceManagerTypeString
import net.forevents.foreventsandroid.Util.removeAtPreferenceManagerTypeString
import net.forevents.foreventsandroid.Util.setToPreferenceManagerTypeString
import net.forevents.foreventsandroid.Util.stringNullToString
import org.jetbrains.anko.forEachChild
import org.jetbrains.anko.internals.AnkoInternals.addView


class SettingsFragment: Fragment() {
    companion object {
        private  val gson = Gson()

    }
    private lateinit var viewModel : SettingsListVM
    private lateinit var cities:List<AppCity>
    private lateinit var eventType:List<AppEventType>
    private  var listCities=  emptyList<String>().toMutableList()
    private  var adapter : ArrayAdapter<String>? = null
    private var PreferenceSearchesToFromPManager : PreferenceSearches? = null
    private var jsonToFromPManager : String? = null

    //private   var listEventType=  emptyList<String>().toMutableList()
    //private  var adapterSpinner : ArrayAdapter<String>? = null
    private var listIdChk = emptyList<Int>().toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PreferenceSearchesToFromPManager = getPreferencesFromPManager()
        setUpViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_settings,container,false)
    }


    override fun onResume() {
        super.onResume()
        setupView()
    }

    private fun getPreferencesFromPManager():PreferenceSearches?{
        jsonToFromPManager = getFromPreferenceManagerTypeString(activity!!, PMANAGER_FAVORITE_PARAMETERS_SEARCH)
        return gson.fromJson(jsonToFromPManager,PreferenceSearches::class.java)
    }



    private fun setupView(){


    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        settings_save_button.setOnClickListener {
            removeAtPreferenceManagerTypeString(activity!!, PMANAGER_FAVORITE_PARAMETERS_SEARCH)
            savePreferenceSearches()
        }

        //Toggle radius



        //AutoCompleteText

        getPreferencesFromPManager()?.let{
            it.favoriteCity?.let {
                city_autoComplete_TextView.setTag(PreferenceSearchesToFromPManager?.favoriteCity?.id.toString())
                city_autoComplete_TextView.setText(PreferenceSearchesToFromPManager?.favoriteCity?.cityAndProvince.toString())
            }
            it.favoriteRadius?.let{
                toggle_radius_button.setToggled(it.toInt(),true)
            }
        }
        adapter = ArrayAdapter(context, android.R.layout.simple_gallery_item, listCities)
        city_autoComplete_TextView.setAdapter(adapter)
        with(city_autoComplete_TextView) {
            threshold = 1
            hint = "Introduce el nombre de la ciudad"
        }
        //Spinner
        //adapterSpinner = ArrayAdapter(context, android.R.layout.simple_list_item_multiple_choice ,listEventType)
        //adapterSpinner!!.setDropDownViewResource(android.R.layout.simple_list_item_checked)
        //spinner_favorite_event.adapter = adapterSpinner
        //with(spinner_favorite_event){
        //    prompt="Selecciona tus eventos favoritos."
            //Para que no se dispare el evento nada más cargarse la pantalla, por aparecer seleccionado el primer elemento
        //    setSelection(0,false)
        //    setPopupBackgroundResource(R.color.orange_700)
        //}



        viewModel.loadEventTypeList()

        city_autoComplete_TextView.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(activity!!, "Selected : ${cities.get(position).city}", Toast.LENGTH_LONG).show()
                city_autoComplete_TextView.dismissDropDown()
            }
        }

        city_autoComplete_TextView.setOnDismissListener {
            //Toast.makeText(activity!!, "Suggestion closed", Toast.LENGTH_LONG).show()
        }
        city_autoComplete_TextView.setOnItemClickListener { parent, view, position, id ->
            city_autoComplete_TextView.tag = cities[position].id
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

    private fun savePreferenceSearches() {

        PreferenceSearchesToFromPManager = PreferenceSearches(
            if (city_autoComplete_TextView.tag != null) DataCity(city_autoComplete_TextView.tag.toString(), city_autoComplete_TextView.text.toString()) else null,
            if (toggle_radius_button.selectedToggles().size != 0) toggle_radius_button.selectedToggles()[0].id.toString() else null,
            favoriteEventsSelected()
        )
        jsonToFromPManager = gson.toJson(PreferenceSearchesToFromPManager)
        setToPreferenceManagerTypeString(activity!!,PMANAGER_FAVORITE_PARAMETERS_SEARCH,jsonToFromPManager!!)
        Toast.makeText(activity!!,"Preferencias guardadas",Toast.LENGTH_LONG).show()
    }

    private fun favoriteEventsSelected():List<String>{
        val listEvents = emptyList<String>().toMutableList()
        radio_group_button_event_type.forEachChild {
            if((it as CheckBox).isChecked()) {
                listEvents.add(it.tag.toString())
            }
        }
        return listEvents
    }

    private  fun setUpViewModel(){
        viewModel = ViewModelProviders.of(this).get(SettingsListVM::class.java)
        bindEvents()
    }

    private fun bindEvents(){
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
                it.map {
                    with(radio_group_button_event_type){
                        orientation= RadioGroup.VERTICAL
                        // create a check
                        val chk = CheckBox(activity!!)
                        chk.text = it.name
                        chk.tag = it.id //Id event inn mongodb
                        chk.setTextSize(20f)
                        chk.setTextColor(Color.WHITE)
                        chk.isChecked = setCheckToEventBox(it.id)
                        // assign an automatically generated id to the radio button
                        chk.id = View.generateViewId()
                        listIdChk.add(chk.id)
                        // add radio button to the radio group
                        addView(chk)
                    }
                }

            }
        })
    }
    private fun setCheckToEventBox(idEvent:String):Boolean{
        PreferenceSearchesToFromPManager?.let {
            it.favoriteEvents?.let{events->
                events.map {
                    if(idEvent == it) return true
                }
            }
        }
        return false
    }


}



