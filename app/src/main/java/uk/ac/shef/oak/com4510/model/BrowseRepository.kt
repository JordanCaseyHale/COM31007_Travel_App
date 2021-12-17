
package uk.ac.shef.oak.com4510.model

import android.app.Application
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import uk.ac.shef.oak.com4510.data.ImageData
import uk.ac.shef.oak.com4510.data.ImageDataDao
import uk.ac.shef.oak.com4510.data.ImageRoomDatabase
import uk.ac.shef.oak.com4510.data.Location
/**
 * Browse Repository class
 */
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

    /**
     * @return the most recent image ID
     * as an integer.
     */
    fun getMaxImageId(): Int? {
        return daoObj?.getMaxImageId()
    }

    /**
     * @return the most recent location ID
     * as an integer.
     */
    fun getMaxLocationId(): Int? {
        return daoObj?.getMaxLocationId()
    }

    /**
     * Inserts an image into the database.
     * @param imageData - The image to be inserted.
     */
    fun insertImageData(imageData: ImageData) {
        daoObj?.insert(imageData)
    }

    /**
     * Inserts a location into the database.
     * @param location - the location to be inserted.
     */
    fun insertLocationData(location: Location) {
        daoObj?.insert(location)
    }

    /**
     * Updates an image in the database.
     * @param imageData - the image to be updated.
     */
    fun updateImageData(imageData: ImageData) {
        daoObj?.update(imageData)
    }
}