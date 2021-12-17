package uk.ac.shef.oak.com4510

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.room.Room
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import uk.ac.shef.oak.com4510.data.*
//import uk.ac.shef.oak.com4510.databinding.ActivityMapsBinding
import uk.ac.shef.oak.com4510.view.MainActivity
import java.text.DateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var mLocationRequest: LocationRequest
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
   // private lateinit var binding: ActivityMapsBinding
    private lateinit var daoObj: ImageDataDao
    private lateinit var startLocation : uk.ac.shef.oak.com4510.data.Location
    private lateinit var endLocation : uk.ac.shef.oak.com4510.data.Location
    private lateinit var startTime: String
    private var startLocationID : Int? = null
    private var endLocationID : Int? = null
    private lateinit var startDate: String
    private lateinit var endTime : String
    private lateinit var journeyTitle : String
    private var myDataset: MutableList<Journey> = ArrayList<Journey>()
    private val mapView: MapView? = null
    private var mButtonStart: Button? = null
    private var mButtonEnd: Button? = null
    private var mButtonBack: Button? = null
    //private var initialStartButton : Button? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        daoObj = (this@MapsActivity.application as ImageApplication).databaseObj.imageDataDao()

        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        mButtonStart = findViewById<View>(R.id.button_start) as Button
        val initialStartButton = findViewById(R.id.JourneyStart) as Button
        println("The initial start button is: "+initialStartButton)
        journeyTitle = (findViewById(R.id.editVisitTitle) as? EditText).toString()

        // First page start button:
        initialStartButton!!.setOnClickListener{
            Log.i("Start3","The button has been clicked")
            Log.i("Start3","The title of the journey is: "+ journeyTitle)
            startLocationUpdates()
            if (mButtonEnd != null) mButtonEnd!!.isEnabled = true
            mButtonStart!!.isEnabled = false
        }

        // attach listener to start button initially
        mButtonStart!!.setOnClickListener{
            Log.i("Start3","The button has been clicked")
            Log.i("Start3","The title of the journey is: "+ journeyTitle)
            startLocationUpdates()
            if (mButtonEnd != null) mButtonEnd!!.isEnabled = true
            mButtonStart!!.isEnabled = false
        }
        // start but is then disabled
        mButtonStart!!.isEnabled = false
        mButtonEnd = findViewById<View>(R.id.button_end) as Button
        mButtonEnd!!.setOnClickListener {
            stopLocationUpdates()
            if (mButtonStart != null) mButtonStart!!.isEnabled = true
            mButtonEnd!!.isEnabled = false
        }
        mButtonEnd!!.isEnabled = true
        mButtonBack = findViewById<View>(R.id.JourneyBack) as Button
        mButtonBack!!.setOnClickListener {
            Log.i("Journey", "return to journey start")
            val intent = Intent (mapFragment.activity, MainActivity::class.java)
            mapFragment.requireActivity().startActivity(intent)
        }
    }
    private fun initJournies() {
        GlobalScope.launch {
            var journies = daoObj.getJournies()
            myDataset.addAll(journies)
        }
    }


    private fun insertData(location: uk.ac.shef.oak.com4510.data.Location): Int = runBlocking {

        var insertJob = async { daoObj.insert(location) }
        insertJob.await().toInt()
    }

    private fun insertData(journey: Journey): Int = runBlocking {

        var insertJob = async { daoObj.insert(journey) }
        insertJob.await().toInt()
    }

    private fun getInitLocationData() {
        val journies : MutableList<Journey> = ArrayList<Journey>()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    ACCESS_FINE_LOCATION
                )

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
            return
        }
        mFusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                startLocation = Location(
                    longitude = location.longitude,
                    latitude =  location.latitude
                )
                startTime = java.time.LocalTime.now().toString()
                startDate = java.time.LocalDate.now().toString()
                startLocationID = startLocation.locationId
                insertData(startLocation)
            }
        }
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest,
            mLocationCallback,
            null /* Looper */
        )
    }

    /**
     * it stops the location updates
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun stopLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        mFusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            println("The last location is : "+ location)
            if (location != null) {
                endLocation = Location(
                    longitude = location.longitude,
                    latitude =  location.latitude
                )
                endTime = java.time.LocalTime.now().toString()
                endLocationID = endLocation.locationId
                insertData(endLocation)
            }
        }
        mFusedLocationClient.removeLocationUpdates(mLocationCallback)

        // adds the completed journey to the database
       // println("The journey title is: "+journeyTitle)
        println(" This is the first part" + startLocationID)
        println( " This is the second aprt" + endLocationID)
        if (startLocationID!= null && endLocationID != null) {
            var journey = Journey(
                title=journeyTitle,
                time = startTime,
                endTime= endTime,
                date= startDate,
                startLocId = startLocationID,
                endLocId = endLocationID
            )

            insertData(journey)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        mLocationRequest = LocationRequest.create().apply {
            interval = 1000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        startLocationUpdates()
    }

    private var mCurrentLocation: Location? = null
    private var mLastUpdateTime: String? = null
    private var mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            mCurrentLocation = locationResult.getLastLocation()
            mLastUpdateTime = DateFormat.getTimeInstance().format(Date())
            Log.i("MAP", "new location " + mCurrentLocation.toString())
            if (mMap != null) mMap.addMarker(
                MarkerOptions().position(
                    LatLng(
                        mCurrentLocation!!.latitude,
                        mCurrentLocation!!.longitude
                    )
                ).title(mLastUpdateTime)
            )
            mMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        mCurrentLocation!!.latitude,
                        mCurrentLocation!!.longitude
                    ), 14.0f
                )
            )
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    mFusedLocationClient.requestLocationUpdates(
                        mLocationRequest,
                        mLocationCallback, null /* Looper */
                    )
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }
        }
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
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14.0f))
    }

    companion object {
        private const val ACCESS_FINE_LOCATION = 123
    }
}