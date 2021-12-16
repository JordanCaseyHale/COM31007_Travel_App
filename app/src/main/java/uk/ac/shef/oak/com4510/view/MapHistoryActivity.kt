package uk.ac.shef.oak.com4510.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import uk.ac.shef.oak.com4510.ImageApplication
import uk.ac.shef.oak.com4510.R
import uk.ac.shef.oak.com4510.data.ImageData
import uk.ac.shef.oak.com4510.data.ImageDataDao
import uk.ac.shef.oak.com4510.databinding.MapHistoryBinding
import java.util.ArrayList
import android.media.ExifInterface
import uk.ac.shef.oak.com4510.data.Location


class MapHistoryActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var binding: MapHistoryBinding
    private var myDataset: MutableList<ImageData> = ArrayList<ImageData>()
    private lateinit var daoObj: ImageDataDao
    private var locationData: Location = Location()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MapHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        daoObj = (this@MapHistoryActivity.application as ImageApplication).databaseObj.imageDataDao()

        initData()

        binding.MapHistoryBack.setOnClickListener {
            setContentView(R.layout.activity_main)
            val intent = Intent (this, MainActivity::class.java)
            startActivity(intent)
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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

        // Add a marker in Sydney and move the camera
        val sheffield = LatLng(53.377, -1.476)
        mMap.addMarker(MarkerOptions().position(sheffield).title("Marker in Sheffield"))
        val zoomLevel = 16.0f //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sheffield, zoomLevel))
        Log.i("LocationData", "Start output of location data")
        for (image in myDataset) {
            Log.i("LocationData", image.imageDescription.toString())
            Log.i("LocationData", image.imageUri)
            //val exifInterface = ExifInterface(image.imageUri)
            //val latLong = FloatArray(2)
            //if (exifInterface.getLatLong(latLong)) {
                // Do stuff with lat / long...
                //Log.i("LocationData", latLong[0].toString())
            //}
            image.location?.apply {
                getLocationData(image.location!!)
                mMap.addMarker(MarkerOptions().position(LatLng(locationData.latitude!!, locationData.longitude!!)).title(image.imageDescription))
                Log.i("LocationData", locationData.latitude.toString())
            }
        }
    }

    /**
     * Init data by loading from the database
     */
    private fun initData() {
        GlobalScope.launch {
            daoObj = (this@MapHistoryActivity.application as ImageApplication).databaseObj.imageDataDao()
            var data = daoObj.getItems()
            myDataset.addAll(data)
        }
    }

    private fun getLocationData(location_id: Int) {
        GlobalScope.launch {
            daoObj = (this@MapHistoryActivity.application as ImageApplication).databaseObj.imageDataDao()
            locationData = daoObj.getLocation(location_id)
        }
    }
}