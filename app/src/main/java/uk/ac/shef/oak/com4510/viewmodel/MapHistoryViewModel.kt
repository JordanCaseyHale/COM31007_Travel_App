package uk.ac.shef.oak.com4510.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uk.ac.shef.oak.com4510.data.ImageData
import uk.ac.shef.oak.com4510.data.Location
import uk.ac.shef.oak.com4510.model.MapHistoryRepository
import java.util.ArrayList

class MapHistoryViewModel(application: Application) : AndroidViewModel(application) {
    private var mRepository: MapHistoryRepository = MapHistoryRepository(application)
    private var imageData: List<ImageData>? = ArrayList<ImageData>()
    private var locationData: List<Location>? = ArrayList<Location>()

    /**
     * Initialises the view model by getting the location and image
     * data from the repository on the dispatchers coroutine
     */
    init {
        viewModelScope.launch(Dispatchers.IO) {
            imageData = mRepository.getImageData()
            locationData = mRepository.getLocationData()
        }
    }

    /**
     * Gets all the image data from the MapHistoryRepository
     * @return List of ImageData
     */
    fun getImageData(): List<ImageData>? {
        return imageData
    }

    /**
     * Gets all the location data from the MapHistoryRepository
     * @return List of Location
     */
    fun getLocationData(): List<Location>? {
        return locationData
    }
}