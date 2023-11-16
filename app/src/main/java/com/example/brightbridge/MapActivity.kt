package com.example.brightbridge

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException


class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var map : GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    fun getLocationFromAddress(context: Context?, strAddress: String?): LatLng? {
        val coder = Geocoder(context)
        val address: List<Address>?
        var p1: LatLng? = null
        try {
            address = coder.getFromLocationName(strAddress, 5)
            if (address == null) {
                return null
            }
            val location: Address = address[0]
            p1 = LatLng(location.getLatitude(), location.getLongitude())
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return p1
    }

    override fun onMapReady(gMap: GoogleMap?) {
        try{
            map = gMap!!
            val intent = intent
            var location = ""
            if(intent.hasExtra("location")) {
                location = intent.getStringExtra("location").toString()
            }
            val latlng = getLocationFromAddress(this@MapActivity,location)
            map!!.addMarker(MarkerOptions().position(latlng!!).title("Patient Location"))
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng,10F))
        }catch (e:Exception) {
            Log.i("Error:",e.message.toString())
        }
    }

    override fun onResume() {
        super.onResume()
    }


}