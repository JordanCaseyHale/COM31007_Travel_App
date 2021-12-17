package uk.ac.shef.oak.com4510.model

import android.app.Application
import uk.ac.shef.oak.com4510.data.ImageData
import uk.ac.shef.oak.com4510.data.ImageDataDao
import uk.ac.shef.oak.com4510.data.ImageRoomDatabase
import uk.ac.shef.oak.com4510.data.Location

/**
 * Model class for Map History.
 */
class MapHistoryRepository(application: Application) {
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

    /**
     * Gets all the location data from the location table
     * @return List of Location
     */
    fun getLocationData(): List<Location>? {
        return daoObj?.getLocations()
    }
}