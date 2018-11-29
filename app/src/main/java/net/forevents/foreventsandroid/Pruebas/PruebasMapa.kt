package net.forevents.foreventsandroid.Pruebas



import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import net.forevents.foreventsandroid.R


class PruebasMapa : AppCompatActivity(), OnMapReadyCallback,GoogleMap.OnMarkerClickListener {

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_big_map)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


    }



    override fun onMarkerClick(p0: Marker?)=false

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

        // Add a marker in Sydney and move the camera
        //val sydney = LatLng(-34.0, 151.0)
        //map.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        //map.moveCamera(CameraUpdateFactory.newLatLng(sydney))



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
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
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

        /*

         mMap.clear(); //clear old markers


        CameraPosition googlePlex = CameraPosition.builder()
            .target(new LatLng(37.4219999,-122.0862462))
        .zoom(10)
            .bearing(0)
            .tilt(45)
            .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 10000, null);

        mMap.addMarker(new MarkerOptions()
            .position(new LatLng(37.4219999, -122.0862462))
            .title("Spider Man")
            .icon(bitmapDescriptorFromVector(getActivity(),R.drawable.spider)));

        //Pasa archivo de imagen a vector
        private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
            Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
            vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
            Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.draw(canvas);
            return BitmapDescriptorFactory.fromBitmap(bitmap);
        }
         */
        // 2
        fusedLocationClient.lastLocation.addOnSuccessListener(this){location ->
            //Got last known location. In some rare situations this can be nul.
            // 3
            if(location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                //val icon = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(resources,R.mipmap.ic_user_location))
                //map.addMarker(MarkerOptions().position(currentLatLng).title("My home").icon(icon))
                placeMarkerOnMap(currentLatLng)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
        }
    }

    //An fun that creates a marker

    private fun placeMarkerOnMap(location: LatLng) {
        // 1
        val markerOptions = MarkerOptions().position(location)
            //.icon (BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(resources,R.mipmap.ic_user_location)))
        // 2 set an custom icon

        //val icon = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(resources,R.mipmap.ic_user_location))
        //markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(resources,R.mipmap.ic_user_location)))
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
        markerOptions.snippet("Hola")
        markerOptions.title("Velilla")


            //fromBitmap(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)))
        // 3
        map.addMarker(markerOptions)
    }



}
