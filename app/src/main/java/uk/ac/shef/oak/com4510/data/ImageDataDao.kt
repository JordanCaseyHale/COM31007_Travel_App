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
    @Query("SELECT * from image ORDER by imageId ASC")
    suspend fun getItems(): List<ImageData>

    @Query("SELECT * from image WHERE imageId = :id")
    fun getItem(id: Int): ImageData

    @Query("SELECT * from location ORDER BY locationId ASC")
    fun getLocations(): List<Location>

    // Specify the conflict strategy as REPLACE,
    // when the trying to add an existing Item
    // into the database.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(singleImageData: ImageData): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(singleLocation: Location): Long

    @Update
    suspend fun update(imageData: ImageData)

    @Delete
    suspend fun delete(imageData: ImageData)


}