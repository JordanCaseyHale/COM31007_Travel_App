package uk.ac.shef.oak.com4510.data

import android.graphics.Bitmap
import androidx.room.*

/**
 * Entity data class represents a single row in the database.
 */
@Entity(tableName = "location", indices = [Index(value = ["locationId"])])
data class Location(
    @PrimaryKey(autoGenerate = true)var locationId: Int = 0,
    @ColumnInfo(name="longitude") val longitude: Double? = null,
    @ColumnInfo(name="latitude") var latitude: Double? = null,
    @ColumnInfo(name="name") var locationName: String? = null,)
{

}