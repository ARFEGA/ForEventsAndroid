package net.forevents.foreventsandroid.presentation.EventDetail

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
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
import net.forevents.foreventsandroid.Util.showDialog
import net.forevents.foreventsandroid.presentation.MainActivities.NucleusActivity


class EventDetailFragment : Fragment() ,OnMapReadyCallback,  GoogleMap.OnMarkerClickListener{


    companion object {
        private const val MY_LOCATION_REQUEST_CODE = 329
        private const val NEW_REMINDER_REQUEST_CODE = 330
        val EXTRA_EVENT = "EXTRA_EVENT"

        fun newInstance(event: AppEvents): EventDetailFragment {
                val arguments = Bundle()
                arguments.putParcelable(EXTRA_EVENT, event)
                val fragment = EventDetailFragment()
                fragment.arguments = arguments
                return fragment
            }
        }

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
        val view = inflater.inflate(R.layout.fragment_event_detail,container,false)
        go_full_screen_map.setOnClickListener {
            (activity as NucleusActivity).openFullScreenMap(listOf<AppEvents>(event))
        }
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        event = arguments?.getParcelable(EXTRA_EVENT) as AppEvents
        drawEvent(event)
        initMap()

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


    //private fun init(){
        //2º Inicializamos la variable que apunta al ViewModel de esta activity
        //eventDetailVM = ViewModelProviders.of(this).get(EventDetailVM::class.java)
        //3º Hacemos el bindEvents del ViewModel
        //bindEvents()
        //4º
        //drawEvent()
    //}

    /*private fun bindEvents(){
        eventDetailVM.userState.observe(this, Observer {userEntity ->
            userEntity?.let{
                onUserLoaded(it)
            }

        })
    }*/
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