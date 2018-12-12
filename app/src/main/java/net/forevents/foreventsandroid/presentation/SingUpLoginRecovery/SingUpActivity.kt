package net.forevents.foreventsandroid.presentation.SingUpLoginRecovery



import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.*
import net.forevents.foreventsandroid.Data.CreateUser.User.AppCity
import net.forevents.foreventsandroid.R
import kotlinx.android.synthetic.main.activity_sing_up.*
import net.forevents.foreventsandroid.presentation.Navigator.Navigator



class SingUpActivity : AppCompatActivity() , LifecycleOwner {

    private lateinit var mLifecycleRegistry: LifecycleRegistry

    private lateinit var cities:List<AppCity>
    private   var listCities=  emptyList<String>().toMutableList()
    private  var adapter : ArrayAdapter<String>? = null

    override fun onStart() {
        super.onStart()
        mLifecycleRegistry.markState(Lifecycle.State.STARTED)
    }

    override fun getLifecycle(): Lifecycle {
        return mLifecycleRegistry
    }

    lateinit var viewModel : UserVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)

        mLifecycleRegistry = LifecycleRegistry(this)
        mLifecycleRegistry.markState(Lifecycle.State.CREATED)

        create_btn_sing_up.setOnClickListener {
            //todo Asegurar la información necesaria
            //todo registrar user en api
            //todo notificar el resultado del alta y pasar a pantalla de login si el alta ha sido satisfactoria
            if(true){
                viewModel.createUser(
                    create_email_text.text.toString(),
                    create_pass_text.text.toString(),
                    create_name_text.text.toString(),
                    create_last_name_text.text.toString(),
                    create_address_text.text.toString(),
                    create_city_text.tag.toString(),
                    create_zip_code_text.text.toString(),
                    create_province_text.text.toString(),
                    create_country_text.text.toString(),
                    create_alias_text.text.toString())
                //todo pasar parametro alias a la pantalla login par que el user no tenga que introducirlo y situarle en pws
                //Navigator.OpenLogin(this,alias_text.text.toString())
            }else{
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
        }
        setupViewModel()
        autoCompleteTextCity()
    }


    private fun autoCompleteTextCity(){
        //AutoCompleteText
        adapter = ArrayAdapter(this, android.R.layout.simple_gallery_item, listCities)
        create_city_text.setAdapter(adapter)
        with(create_city_text) {
            threshold = 1
        }

        create_city_text.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                //create_province_text.setText(cities.get(position).province)
                //create_zip_code_text.setText(cities.get(position).zip_code)
                //create_city_text.setText(cities.get(position).city)
                //create_city_text.setTag(cities.get(position).id)
                //create_country_text.setText(cities.get(position).country)
            }
        }
        create_city_text.setOnItemClickListener { parent, view, position, id ->
            create_province_text.setText(cities.get(position).province)
            create_zip_code_text.setText(cities.get(position).zip_code)
            create_city_text.setText(cities.get(position).city)
            create_city_text.setTag(cities.get(position).id)
            create_country_text.setText(cities.get(position).country)
        }
        create_city_text.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                //create_city_text.showDropDown()
            }
        }
        create_city_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                viewModel.loadCitiestList(s.toString(),"50")
            }
        })
        create_city_text.setOnDismissListener {
            cerrarTeclado()
        }

        root_layout.setOnClickListener {
            cerrarTeclado()
        }
    }
    private fun cerrarTeclado(){
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }

    private fun setupViewModel(){
        viewModel = ViewModelProviders.of(this).get(UserVM::class.java)
        BindEvents()
    }
    private fun BindEvents(){
        viewModel.createUserState.observe(this, Observer {userCreated->
            userCreated?.let {
                Navigator.OpenLogin(this,create_alias_text.text.toString())
            }
        })

        viewModel.citiesListState.observe(this, Observer { citiesList ->
            citiesList?.let{
                cities = it
                listCities.clear()
                adapter!!.clear()
                it.map { listCities.add("${it.city}       (${it.province})") }
                adapter!!.addAll(listCities)
            }
        })
    }
}
