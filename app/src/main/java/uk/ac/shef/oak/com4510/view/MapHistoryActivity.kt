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
import android.view.View
import com.google.android.material.floatingactionbutton.FloatingActionButton
import pl.aprilapps.easyphotopicker.EasyImage
import uk.ac.shef.oak.com4510.data.Location


class MapHistoryActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var binding: MapHistoryBinding
    private var myDataset: MutableList<ImageData> = ArrayList<ImageData>()
    private lateinit var daoObj: ImageDataDao
    private var locationData: MutableList<Location> = ArrayList<Location>()
    private lateinit var easyImage: EasyImage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MapHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        daoObj = (this@MapHistoryActivity.application as ImageApplication).databaseObj.imageDataDao()

        initData()

        initLocationData()

        //initEasyImage()

        // the floating button that will allow us to get the images from the Gallery
        //val fabGallery: FloatingActionButton = findViewById(R.id.fab_gallery)
        //fabGallery.setOnClickListener(View.OnClickListener {
            //easyImage.openChooser(this@MapHistoryActivity)
        //})

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

        // Move the camera
        val sheffield = LatLng(53.377, -1.476)
        val zoomLevel = 16.0f //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sheffield, zoomLevel))
        for (image in myDataset) {
            image.location?.apply {
                var lat: Double? = null
                var long: Double? = null
                for (location in locationData) {
                    if (location.locationId == image.location) {
                        lat = location.latitude
                        long = location.longitude
                    }
                }
                lat?.apply{
                    long?.apply{
                        mMap.addMarker(MarkerOptions().position(LatLng(lat, long)).title(image.imageDescription))
                    }
                }
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

    private fun initLocationData() {
        GlobalScope.launch {
            daoObj = (this@MapHistoryActivity.application as ImageApplication).databaseObj.imageDataDao()
            var data = daoObj.getLocations()
            locationData.addAll(data)
        }
    }
}