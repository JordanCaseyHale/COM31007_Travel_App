/**
 * This interface contains the methods for manipulating the
 * database for this project called ImageRoomDatabase
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
    // Returns a list of images ordering by most recent in the database
    @Query("SELECT * from image ORDER by imageId ASC")
    suspend fun getItems(): List<ImageData>

    // Selects the image of the corresponding imageID
    @Query("SELECT * from image WHERE imageId = :id")
    fun getItem(id: Int): ImageData

    @Query("SELECT * from location ORDER BY locationId ASC")
    fun getLocations(): List<Location>

    // Specify the conflict strategy as REPLACE,
    // when the trying to add an existing Item
    // into the database.
    // Insert an image into the image table.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(singleImageData: ImageData): Long

    // Insert a location into the lcoation table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(singleLocation: Location): Long

    // Update an image in the image table
    @Update
    suspend fun update(imageData: ImageData)

    // Delete an image from the image table.
    @Delete
    suspend fun delete(imageData: ImageData)


}