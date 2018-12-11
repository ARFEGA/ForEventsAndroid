package net.forevents.foreventsandroid.presentation.EventDetail

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.LocationManager
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_event_detail.*
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import net.forevents.foreventsandroid.Data.CreateUser.User.AppEvents
import net.forevents.foreventsandroid.R
import net.forevents.foreventsandroid.Util.Constants
import net.forevents.foreventsandroid.Util.getFromPreferenceManagerTypeString
import net.forevents.foreventsandroid.Util.showDialog
import net.forevents.foreventsandroid.presentation.MainActivities.NucleusActivity
import net.forevents.foreventsandroid.presentation.MainActivities.NucleusActivityVM


class EventDetailFragment : Fragment() ,OnMapReadyCallback,  GoogleMap.OnMarkerClickListener{


    companion object {
        private const val MY_LOCATION_REQUEST_CODE = 329
        private const val NEW_REMINDER_REQUEST_CODE = 330
        val EXTRA_EVENT = "EXTRA_EVENT"
        val ASISTIRE = "Asistiré"
        val ANULAR_RESERVA = "Anular Reserva"

        fun newInstance(event: AppEvents): EventDetailFragment {
                val arguments = Bundle()
                arguments.putParcelable(EXTRA_EVENT, event)
                val fragment = EventDetailFragment()
                fragment.arguments = arguments
                return fragment
            }


        }
    private lateinit var viewModel : NucleusActivityVM

    private  var map: GoogleMap? = null

    private lateinit var locationEvent : LatLng

    private lateinit var locationManager: LocationManager

    private lateinit var event: AppEvents

    //1º Preparamos la variable que apunta al ViewModel
    //lateinit var eventDetailVM: EventDetailVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        setUpViewModel()
        return inflater.inflate(R.layout.fragment_event_detail,container,false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        description_event.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD)

        event = arguments?.getParcelable(EXTRA_EVENT) as AppEvents

            super.onResume()
            btn_book.text= if (event.idTrans.isNullOrBlank() || event.idTrans.isNullOrEmpty()) ASISTIRE else ANULAR_RESERVA

            btn_book.setOnClickListener {
                when(btn_book.text){
                    ASISTIRE->{
                        viewModel.saveTransaction( getFromPreferenceManagerTypeString(activity!!, Constants.PMANAGER_TOKEN_USER)!!,event.id)
                        //onBookButtonClickedListenerDetailFragment?.onBookBtnClickedFragmentDetail("save",event.id)
                    }
                    ANULAR_RESERVA->{
                        viewModel.delTransaction( getFromPreferenceManagerTypeString(activity!!, Constants.PMANAGER_TOKEN_USER)!!,event.idTrans.toString())
                        //onBookButtonClickedListenerDetailFragment?.onBookBtnClickedFragmentDetail("del",event.idTrans.toString())
                    }
                }
            }


        go_full_screen_map.setOnClickListener {
            (activity as NucleusActivity).openFullScreenMap(listOf<AppEvents>(event))
        }


        drawEvent(event)
        initMap()
    }
    private  fun setUpViewModel(){
        viewModel = ViewModelProviders.of(this).get(NucleusActivityVM::class.java)
        bindEvents()
    }
    private fun bindEvents(){
        viewModel.saveTransactionState.observe(this, Observer { saveState->
            saveState?.let {
                Toast.makeText(activity!!,"RESERVA REALIZADA", Toast.LENGTH_LONG).show()
               btn_book.text = ANULAR_RESERVA
            }
        })

        viewModel.deleteTransactionState.observe(this, Observer { deleteState ->
            deleteState?.let {
                Toast.makeText(activity!!,"RESERVA ANULADA", Toast.LENGTH_LONG).show()
                btn_book.text = ASISTIRE
            }
        })
    }


    private fun drawEvent(event: AppEvents?) {
        //Asignamos first_name a la caja de texto
        event?.let {
            locationEvent = LatLng(event.latitude,event.longitude)
            name_event.text = event.name
            description_event.text = event.description
            address_event.text = "${event.address} (${event.city})"
            begin_date.text = event.begin_date
            //Mediante Glide, asignamos la imagen al objeto image
            Glide.with(activity!!)
                .load(event.imgUrl)
                .into(image_user)
        }
    }

    //Pertenece a OnMapReadyCallback
    override fun onMapReady(gooleMap: GoogleMap?) {
        map = gooleMap
        map?.mapType = GoogleMap.MAP_TYPE_TERRAIN
        map?.run{
            //uiSettings.isMyLocationButtonEnabled = true
            uiSettings.isMapToolbarEnabled  = true
            uiSettings.isRotateGesturesEnabled = false
            uiSettings.isZoomGesturesEnabled = false
            //setOnMarkerClickListener(this@EventDetailFragment)
        }
        onMapAndPermissionReady()
    }


    //Pertenece a GoogleMap.OnMarkerClickListener
    override fun onMarkerClick(p0: Marker?): Boolean {
        showDialog(activity!!,"Desde el mapa","Desde el mapa")
        return true
    }

    private fun initMap(){
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)

        locationManager = activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity!!, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), MY_LOCATION_REQUEST_CODE)
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == MY_LOCATION_REQUEST_CODE){
            onMapAndPermissionReady()
        }
    }

    private fun onMapAndPermissionReady(){
        if (map != null && ContextCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED){
            //map?.isMyLocationEnabled = true
            val bestProvider = locationManager.getBestProvider(Criteria(), false)
            //val location = locationManager.getLastKnownLocation(bestProvider)
            //if (location != null) {
            //    val latLng = LatLng(location.latitude, location.longitude)
            //    map?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
            //}

            placeMarkerOnMap(locationEvent)
            map?.animateCamera(CameraUpdateFactory.newLatLngZoom(locationEvent, 12f))
            //centerCamera(LatLng(40.370038,-3.468868))
        }
    }
    private fun placeMarkerOnMap(location: LatLng) {
        // 1
        val markerOptions = MarkerOptions().position(location)
        //.icon (BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(resources,R.mipmap.ic_user_location)))
        // 2 set an custom icon

        //val icon = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(resources,R.mipmap.ic_user_location))
        //markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(resources,R.mipmap.ic_user_location)))
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
        markerOptions.title(event.name)
        markerOptions.snippet("${event.address} (${event.city})")


        //fromBitmap(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)))
        // 3
        map?.addMarker(markerOptions)
    }
    private fun centerCamera(latLon:LatLng){
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLon,15f))
    }

    //########## Code that's associated interface  ###############
    //Var tipo interfaz
    private var onBookButtonClickedListenerDetailFragment: OnBookButtonClickedListenerDetailFragment? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        commonAttach(context as? Activity)
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        commonAttach(activity)
    }

    fun commonAttach(activity: Activity?){
        if(activity is EventDetailFragment.OnBookButtonClickedListenerDetailFragment)
            onBookButtonClickedListenerDetailFragment = activity
        else onBookButtonClickedListenerDetailFragment = null
    }

    override fun onDetach() {
        super.onDetach()
        onBookButtonClickedListenerDetailFragment = null
    }
//#################   interface for connect fragment with qctivity  ##############

    interface OnBookButtonClickedListenerDetailFragment{
        fun onBookBtnClickedFragmentDetail(typeAction:String,eventOrTransactionID: String)
    }
    //private fun init(){
        //2º Inicializamos la variable que apunta al ViewModel de esta activity
        //eventDetailVM = ViewModelProviders.of(this).get(EventDetailVM::class.java)
        //3º Hacemos el bindEvents del ViewModel
        //bindEvents()
        //4º
        //drawEvent()
    //}


    /*private fun drawEvent(){
        //Comprobamos si tenemos v alor de usuario
        if(event == null){
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
        //Llamamos a la función de lViewModel
        //eventDetailVM.loadUserById(user_id)
        onUserLoaded(userEntity!!)
    }*/




}