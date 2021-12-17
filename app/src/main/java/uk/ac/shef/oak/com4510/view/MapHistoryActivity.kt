package uk.ac.shef.oak.com4510.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import uk.ac.shef.oak.com4510.R
import uk.ac.shef.oak.com4510.databinding.MapHistoryBinding
import pl.aprilapps.easyphotopicker.EasyImage
import uk.ac.shef.oak.com4510.viewmodel.MapHistoryViewModel
import android.graphics.Bitmap
import android.graphics.BitmapFactory

// Map History View
class MapHistoryActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: MapHistoryBinding
    private lateinit var easyImage: EasyImage
    private var mapHistoryViewModel: MapHistoryViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MapHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mapHistoryViewModel = ViewModelProvider(this)[MapHistoryViewModel::class.java]


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
     * When the map is ready, add the images as markers on the map
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Move the camera
        val sheffield = LatLng(53.377, -1.476)
        val zoomLevel = 16.0f //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sheffield, zoomLevel))
        var imageData = mapHistoryViewModel?.getImageData()
        if (imageData != null) {
            for (image in imageData) {
                image.location?.apply {
                    var lat: Double? = null
                    var long: Double? = null
                    var locationData = mapHistoryViewModel?.getLocationData()
                    if (locationData != null) {
                        for (location in locationData) {
                            if (location.locationId == image.location) {
                                lat = location.latitude
                                long = location.longitude
                            }
                        }
                    }
                    // If there is a location
                    lat?.apply {
                        long?.apply {
                            // Convert the image into an icon to display on the map
                            val height = 160
                            val width = 160
                            val bmOptions = BitmapFactory.Options()
                            val bm = BitmapFactory.decodeFile(image.imageUri, bmOptions)
                            val smallMarker = Bitmap.createScaledBitmap(bm, width, height, false)
                            val smallMarkerIcon = BitmapDescriptorFactory.fromBitmap(smallMarker)

                            // Add marker
                            mMap.addMarker(
                                MarkerOptions().position(LatLng(lat, long))
                                    .title(image.imageDescription).icon(smallMarkerIcon))
                        }
                    }
                }
            }
        }
    }


}