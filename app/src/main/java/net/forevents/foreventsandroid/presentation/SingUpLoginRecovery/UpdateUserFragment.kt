package net.forevents.foreventsandroid.presentation.SingUpLoginRecovery


import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_update_profile.*

import net.forevents.foreventsandroid.Data.Model.City.AppCity
import net.forevents.foreventsandroid.Data.Model.UserById.AppUserById
import net.forevents.foreventsandroid.R
import net.forevents.foreventsandroid.Util.Constants.PMANAGER_ID_USER
import net.forevents.foreventsandroid.Util.Constants.PMANAGER_TOKEN_USER
import net.forevents.foreventsandroid.Util.getFromPreferenceManagerTypeString
import net.forevents.foreventsandroid.Util.logOut
import net.forevents.foreventsandroid.Util.showDialog
import net.forevents.foreventsandroid.presentation.MainActivities.NucleusActivity
import net.forevents.foreventsandroid.presentation.Navigator.Navigator

class UpdateUserFragment : Fragment() ,LifecycleOwner{

        private lateinit var mLifecycleRegistry: LifecycleRegistry

        private lateinit var cities:List<AppCity>
        private   var listCities=  emptyList<String>().toMutableList()
        private  var adapter : ArrayAdapter<String>? = null
        private lateinit var apiDataUser:AppUserById

        override fun onStart() {
            super.onStart()
            mLifecycleRegistry.markState(Lifecycle.State.STARTED)
        }

        override fun getLifecycle(): Lifecycle {
            return mLifecycleRegistry
        }

        lateinit var viewModel : UserVM


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_update_profile,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        update_repeat_pass_text.visibility =View.GONE
        update_pass_text.visibility = View.GONE

            mLifecycleRegistry = LifecycleRegistry(this)
            mLifecycleRegistry.markState(Lifecycle.State.CREATED)

            update_profile_btn.setOnClickListener {

                //todo Asegurar la información necesaria
                //todo registrar user en api
                //todo notificar el resultado del alta y pasar a pantalla de login si el alta ha sido satisfactoria
                if(true){
                    viewModel.updateProfile(
                        "a${getFromPreferenceManagerTypeString(activity!!, PMANAGER_ID_USER)!!}",
                        getFromPreferenceManagerTypeString(activity!!, PMANAGER_TOKEN_USER)!!,
                        update_email_text.text.toString(),
                        update_name_text.text.toString(),
                        update_last_name_text.text.toString(),
                        update_address_text.text.toString(),
                        update_city_text.tag.toString(),
                        update_zip_code_text.text.toString(),
                        update_province_text.text.toString(),
                        update_country_text.text.toString(),
                        update_alias_text.text.toString())
                    //todo pasar parametro alias a la pantalla login par que el user no tenga que introducirlo y situarle en pws
                    //Navigator.OpenLogin(this,alias_text.text.toString())
                }/*else{
                    setResult(Activity.RESULT_CANCELED)
                    finish()
                }*/

            }
            setupViewModel()
            autoCompleteTextCity()
            viewModel.getUserById(getFromPreferenceManagerTypeString(activity!!,PMANAGER_ID_USER)!!,
            getFromPreferenceManagerTypeString(activity!!, PMANAGER_TOKEN_USER)!!)

        }


        private fun autoCompleteTextCity(){


            //AutoCompleteText
            adapter = ArrayAdapter(activity!!, android.R.layout.simple_gallery_item, listCities)
            update_city_text.setAdapter(adapter)
            with(update_city_text) {
                threshold = 1
            }

            update_city_text.setOnItemClickListener { parent, view, position, id ->
                update_province_text.setText(cities.get(position).province)
                update_zip_code_text.setText(cities.get(position).zip_code)
                update_city_text.setText(cities.get(position).city)
                update_city_text.setTag(cities.get(position).id)
                update_country_text.setText(cities.get(position).country)
            }

            update_city_text.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    update_city_text.showDropDown()
                }
            }
            update_city_text.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {}
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    viewModel.loadCitiestList(s.toString(),"50")
                }
            })
            update_city_text.setOnDismissListener {
                update_city_text.hideKeyboard()
            }

            root_layout.setOnClickListener {
                update_city_text.hideKeyboard()
            }

        }

    fun View.hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }


        private fun setupViewModel(){
            viewModel = ViewModelProviders.of(this).get(UserVM::class.java)
            BindEvents()
        }
        private fun BindEvents(){
            viewModel.createUserState.observe(this, Observer {userCreated->
                userCreated?.let {
                    //showDialog(this,"'CREATE' Datos desde API ","Message: ${it.message} El id: ${it.id}")
                    Navigator.OpenLogin(activity!!,update_alias_text.text.toString())
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

            viewModel.userByIdState.observe(this, Observer { userById ->
                userById?.let{
                    if(it.ok) {
                        updateForm(it)
                        apiDataUser=it
                        Toast.makeText(activity!!,"Perfil actualizado",Toast.LENGTH_LONG).show()
                    }
                    else
                        showDialog(activity!!,"ERROR","No se ha podido obtener la información, intentelo nuevamente.")
                }
            })

            viewModel.updateUserState.observe(this, Observer { resultUpdate->
                resultUpdate?.let {
                    var auxMsg=""
                    if(it.ok) {
                        auxMsg = "Perfil actualizado correctamente. \n"
                        if (apiDataUser.user.email != update_email_text.text.toString()) {
                            //Ha cambiado el mail por lo tanto, borro de preference userId y token y hago logout
                            auxMsg += "El cambio de mail, ocasionará un logout."
                            showDialog(activity!!, "Actualización de perfil.",auxMsg)
                            (activity as NucleusActivity).logOut()
                        }
                    }
                    else{
                        auxMsg = it.error[0].toString()
                    }
                    showDialog(activity!!, "Actualización de perfil.",auxMsg)
                }
            })

        }

        private fun updateForm(user:AppUserById){
            update_email_text.setText(user.user.email)
            update_pass_text.setText(user.user.password)
            update_name_text.setText(user.user.first_name)
            update_last_name_text.setText(user.user.last_name)
            update_address_text.setText(user.user.address)
            update_city_text.tag = user.user.city._id
            update_city_text.setText(user.user.city.city)
            update_zip_code_text.setText(user.user.city.zip_code)
            update_province_text.setText(user.user.city.province)
            update_country_text.setText(user.user.city.province)
            update_alias_text.setText(user.user.alias)
        }






    }




