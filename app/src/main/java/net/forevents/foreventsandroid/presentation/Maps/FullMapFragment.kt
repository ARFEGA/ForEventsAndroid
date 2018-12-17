package net.forevents.foreventsandroid.presentation.Maps



import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import net.forevents.foreventsandroid.Data.Model.Events.AppEvents
import net.forevents.foreventsandroid.R
import net.forevents.foreventsandroid.presentation.MainActivities.NucleusActivity
import net.forevents.foreventsandroid.presentation.TabFragment.TabFragment


class FullMapFragment :Fragment(), OnMapReadyCallback,GoogleMap.OnMarkerClickListener,GoogleMap.OnInfoWindowClickListener
{

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        //private val ARG_SECTION_NUMBER = "section_number"
        private val EXTRA_EVENTS = "EXTRA_EVENTS_LIST"
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        fun newInstance(events:List<AppEvents>): FullMapFragment {
            val arrayListEvents = arrayListOf<AppEvents>()
            events.map {  arrayListEvents.add(it)}
            val fragment = FullMapFragment()
            val args = Bundle()
            args.putParcelableArrayList(EXTRA_EVENTS,arrayListEvents)
            fragment.arguments = args
            return fragment
        }
    }


    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var events:List<AppEvents>
    private lateinit var marker: Marker
    //Inicio lista de tuplas vacía
    private  var tuplaEvents = emptyList<Pair<Int,AppEvents>>().toMutableList()




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_big_map,container,false)
        val mapFragment  = getChildFragmentManager().findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        events =  (arguments?.getParcelableArrayList(EXTRA_EVENTS)!!)
        events.map { tuplaEvents.add(tuplaEvents.size  ,Pair(tuplaEvents.size ,it)) }

    }



    override fun onInfoWindowClick(marker: Marker?) {
        marker?.let {
            //Toast.makeText(activity, marker?.title, Toast.LENGTH_LONG).show()
            (activity as NucleusActivity).openDetailScreen(tuplaEvents.get(marker.zIndex.toInt()).second)
        }
    }

    override fun onMarkerClick(marker: Marker?):Boolean{
        // We return true/false indicating that we either have consumed or not consumed the event and that if value to return is false, we wish
        // for the default behavior to occur (which is for the camera to move such that the marker is centere
        marker?.let{
            marker.zIndex += .0f
            Log.d("MARKER:","POSITION:${it.snippet} zIndex:${it.zIndex}  >>>>>>>>>  ${tuplaEvents.get(it.zIndex.toInt()).second.name}")
            //Toast.makeText(context, "${marker.title} z-index set to ${marker.zIndex}",Toast.LENGTH_SHORT).show()
        }
        return false
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        //Enabled zoom
        map.uiSettings.isZoomControlsEnabled = true
        //The activity will be the listener when the user will do tap  on the map
        map.setOnMarkerClickListener(this)
        map.setOnInfoWindowClickListener(this)
        map.setContentDescription("Tu mapa de eventos")
        /*
        Zoom level 0 corresponds to the fully zoomed-out world view.
        Most areas support zoom levels up to 20, while more remote areas only support zoom levels up to 13.
        A zoom level of 12 is a nice in-between value that shows enough detail
        */
        //map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,12.0f))
        setUpMap()
    }
    fun setUpMap() {
        //Todo, learn about permission at run time
        if (ActivityCompat.checkSelfPermission(activity!!,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity!!, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
        //Assigning the last known location.
        // 1
        //This, show a blue dot on the map, and also adds a button that when taped, centers the map
        //on the user's location.
        map.isMyLocationEnabled = true
        map.mapType = GoogleMap.MAP_TYPE_TERRAIN
        //map.mapType = GoogleMap.MAP_TYPE_HYBRID
        //map.mapType = GoogleMap.MAP_TYPE_NORMAL
        //map.mapType = GoogleMap.MAP_TYPE_SATELLITE
        //map.mapType = GoogleMap.MAP_TYPE_NONE
        // 2

        //If there's  events, if tupleEvents is no empty
        if (!tuplaEvents.isEmpty()){
            //we fix the points on the map
            tuplaEvents.map {
                placeMarkerOnMap(it)
            }
            //And then we fix the view on the first coordinate
            fixCameraMap(LatLng(events[0].latitude, events[0].longitude))
        }else{//Take current location from device
            myCurrentLocation()?.let {
                fixCameraMap(LatLng(it.latitude, it.longitude))
            }
        }
    }

    private fun fixCameraMap(coordinate:LatLng){
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 12f))
    }
    //An fun that creates a marker

    private fun myCurrentLocation():Location?{
        var lastLocation: Location? = null
        fusedLocationClient.lastLocation.addOnSuccessListener(activity!!){location ->
            //Got last known location. In some rare situations this can be nul.
            // 3
            if(location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                //val icon = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(resources,R.mipmap.ic_user_location))
                //map.addMarker(MarkerOptions().position(currentLatLng).title("My home").icon(icon))
                //placeMarkerOnMap(currentLatLng)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
        }
        return lastLocation
    }


    private fun placeMarkerOnMap(event:Pair<Int,AppEvents>) {
        // 1
        val location = LatLng(event.second.latitude,event.second.longitude)
        val markerOptions = MarkerOptions().position(location)
            //.icon (BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(resources,R.mipmap.ic_user_location)))
        // 2 set an custom icon

        //val icon = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(resources,R.mipmap.ic_user_location))
        //markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(resources,R.mipmap.ic_user_location)))
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
        markerOptions.snippet("${event.second.name}")
        markerOptions.title("${event.second.city}")
        markerOptions.zIndex(event.first + 0f)
        markerOptions.flat(false)
        map.addMarker(markerOptions)

    }



}
