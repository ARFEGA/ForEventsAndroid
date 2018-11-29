package net.forevents.foreventsandroid.Util.maps

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat





fun ____setUpMap(activity:Activity,context:Context,LOCATION_PERMISSION_REQUEST_CODE :Int) {
    if (ActivityCompat.checkSelfPermission(context,
            android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(activity,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        return
    }
}