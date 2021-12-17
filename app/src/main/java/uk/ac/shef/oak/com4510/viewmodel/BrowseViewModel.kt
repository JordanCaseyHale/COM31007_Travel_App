package uk.ac.shef.oak.com4510.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uk.ac.shef.oak.com4510.data.ImageData
import uk.ac.shef.oak.com4510.data.Location
import uk.ac.shef.oak.com4510.model.BrowseRepository
import java.util.ArrayList

class BrowseViewModel(application: Application) : AndroidViewModel(application) {
    private var mRepository: BrowseRepository = BrowseRepository(application)
    private var imageData: List<ImageData>? = ArrayList<ImageData>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            imageData = mRepository.getImageData()
        }
    }

    /**
     * Gets all the image data from the MapHistoryRepository
     * @return List of ImageData
     */
    fun getImageData(): List<ImageData>? {
        return imageData
    }

    fun getNextImageId(): Int? {
        var max_id = mRepository.getMaxImageId()
        if (max_id == null) {
            return null
        }
        else {
            return (max_id?.plus(1))
        }
    }

    fun getNextLocationId(): Int? {
        var max_id = mRepository.getMaxLocationId()
        if (max_id == null) {
            return null
        }
        else {
            return (max_id?.plus(1))
        }
    }

    fun insertImageData(imageData: ImageData) {
        viewModelScope.launch(Dispatchers.IO) {
            mRepository.insertImageData(imageData)
        }
    }

    fun insertLocationData(location: Location) {
        viewModelScope.launch(Dispatchers.IO) {
            mRepository.insertLocationData(location)
        }
    }

    fun updateImageData(imageData: ImageData) {
        viewModelScope.launch(Dispatchers.IO) {
            mRepository.updateImageData(imageData)
        }
    }
}