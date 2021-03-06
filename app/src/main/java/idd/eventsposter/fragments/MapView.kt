package idd.eventsposter.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import java.io.IOException
import java.util.ArrayList
import java.util.Locale

import idd.eventsposter.R
import ui.form.FormView

import androidx.core.content.ContextCompat.checkSelfPermission
import ui.map.MapPresenter
import ui.map.MapVP

class MapView : Fragment(), MapVP.View, OnMapReadyCallback {

    private var map: SupportMapFragment? = null
    private var mapPresenter: MapPresenter? = null


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setMVP()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        (activity as AppCompatActivity).supportActionBar!!.setTitle(R.string.map)
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        map = childFragmentManager.findFragmentById(R.id.googleMap) as SupportMapFragment?

        if (map != null) {
            map!!.getMapAsync(this)
        }
        return view
    }


    private fun convertLocationToAddress(point: LatLng) {
        val addressText: String
        var errorMessage = ""

        val geocoder = Geocoder(context, Locale.getDefault())

        var addresses: List<Address>? = null

        try {
            addresses = geocoder.getFromLocation(
                    point.latitude,
                    point.longitude,
                    1
            )
        } catch (ioException: IOException) {
            // Network or other I/O issues
            errorMessage = getString(R.string.network_service_error)
            Log.e(TAG, errorMessage, ioException)
        } catch (illegalArgumentException: IllegalArgumentException) {
            // Invalid long / lat
            errorMessage = getString(R.string.invalid_long_lat)
            Log.e(TAG, errorMessage + ". " +
                    "Latitude = " + point.latitude +
                    ", Longitude = " +
                    point.longitude, illegalArgumentException)
        }

        // No address was found
        if (addresses == null || addresses.isEmpty()) {
            if (errorMessage.isEmpty()) {
                errorMessage = getString(R.string.no_address_found)
                Log.e(TAG, errorMessage)
            }
            addressText = errorMessage

        } else {
            val address = addresses[0]
            val addressFragments = ArrayList<String>()

            // Fetch the address lines, join them, and return to thread
            for (i in 0..address.maxAddressLineIndex) {
                addressFragments.add(address.getAddressLine(i))
            }
            Log.i(TAG, getString(R.string.address_found))
            addressText = TextUtils.join(System.getProperty("line.separator")!!,
                    addressFragments)
        }
        FormView.address = addressText
    }

    override fun setMVP() {
        mapPresenter = MapPresenter(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        val bryansk = LatLng(53.280458, 34.2275525)
        googleMap?.addMarker(MarkerOptions().position(bryansk)
                  .title("Marker in Bryansk"))
        googleMap?.moveCamera(CameraUpdateFactory.newLatLng(bryansk))
        //mMap.animateCamera(CameraUpdate);

        if (googleMap != null && context?.let {
                                 checkSelfPermission(it,
                                 Manifest.permission.ACCESS_FINE_LOCATION)
                                 } == PackageManager.PERMISSION_GRANTED) {
            /*|| checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                                        PackageManager.PERMISSION_GRANTED)*/
            googleMap.isMyLocationEnabled = true
        }

        googleMap?.setMinZoomPreference(14f)


        googleMap?.setOnMapClickListener { point: LatLng ->
            googleMap.clear()
            googleMap.addMarker(MarkerOptions().position(point))

            FormView.koord = context?.getString(R.string.textCoordinates,
                                                point.latitude,
                                                point.longitude)

            convertLocationToAddress(point)
        }

        googleMap?.setOnMarkerClickListener {
            //Navigation.findNavController(view!!).navigate(R.id.action_mapFragment_to_formFragment)
            true
        }
    }

    companion object { private const val TAG = "MapActivity" }
}
