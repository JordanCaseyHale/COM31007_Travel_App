/**
 * This class represents the table in the database for Image Elements.
 *
 */

package uk.ac.shef.oak.com4510.data

import android.graphics.Bitmap
import androidx.room.*

/**
 * Entity data class represents a single row in the database.
 */
@Entity(tableName = "image", indices = [Index(value = ["imageId"])],
    foreignKeys = [(ForeignKey(entity = Location::class,
        parentColumns = arrayOf("locationId"),
        childColumns = arrayOf("location_id"),
        onDelete = ForeignKey.CASCADE))])
data class ImageData(
    @PrimaryKey(autoGenerate = true)var imageId: Int = 0,
    @ColumnInfo(name="uri") val imageUri: String,
    @ColumnInfo(name="location_id") var location: Int? = null,
    @ColumnInfo(name="description") var imageDescription: String? = null,
    @ColumnInfo(name="thumbnailUri") var thumbnailUri: String? = null,)
{
    @Ignore
    var thumbnail: Bitmap? = null
}