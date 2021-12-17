package uk.ac.shef.oak.com4510.model

import android.app.Application
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import uk.ac.shef.oak.com4510.data.ImageData
import uk.ac.shef.oak.com4510.data.ImageDataDao
import uk.ac.shef.oak.com4510.data.ImageRoomDatabase
import uk.ac.shef.oak.com4510.data.Location

class BrowseRepository(application: Application) {
    private var daoObj: ImageDataDao? = null

    /**
     * Initialises the repository by creating the database object
     */
    init {
        val db: ImageRoomDatabase? = ImageRoomDatabase.getDatabase(application)
        if (db != null) { daoObj = db.imageDataDao() }
    }

    /**
     * Gets all the image data from the image table
     * @return List of ImageData
     */
    fun getImageData(): List<ImageData>? {
        return daoObj?.getItems()
    }

    fun getMaxImageId(): Int? {
        return daoObj?.getMaxImageId()
    }

    fun getMaxLocationId(): Int? {
        return daoObj?.getMaxLocationId()
    }

    fun insertImageData(imageData: ImageData) {
        daoObj?.insert(imageData)
    }

    fun insertLocationData(location: Location) {
        daoObj?.insert(location)
    }

    fun updateImageData(imageData: ImageData) {
        daoObj?.update(imageData)
    }
}