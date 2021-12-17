/**
 * Class contains methods to manipulate the database
 */
package uk.ac.shef.oak.com4510.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

/**
 * Database access object to access the Inventory database
 */
@Dao
interface ImageDataDao {
    // Select all images from the image table in ascending order from
    // when they were inputted into the database.
    @Query("SELECT * from image ORDER by imageId ASC")
    fun getItems(): List<ImageData>

    // Select the image with the corresponding ID
    @Query("SELECT * from image WHERE imageId = :id")
    fun getItem(id: Int): ImageData

    // Select all locations in ascending order from when they were inputted
    // into the table.
    @Query("SELECT * from location ORDER BY locationId ASC")
    fun getLocations(): List<Location>

    // Select the most recent image.
    @Query("SELECT MAX(imageId) FROM image")
    fun getMaxImageId(): Int

    // Select the most recent ID
    @Query("SELECT MAX(locationId) FROM location")
    fun getMaxLocationId(): Int

    // Specify the conflict strategy as REPLACE,
    // when the trying to add an existing Item
    // into the database.
    // Insert an image into the image table replacing any duplicates.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(singleImageData: ImageData): Long

    // Insert a location into the location table replacing any
    // duplicates.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(singleLocation: Location): Long

    // Update the image table.
    @Update
    fun update(imageData: ImageData)

    // Delete an image from the image table.
    @Delete
    fun delete(imageData: ImageData)


}