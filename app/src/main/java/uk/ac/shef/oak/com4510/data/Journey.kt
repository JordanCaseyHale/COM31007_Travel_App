/**
 * Class corresponding to the Journey Table and represents the Journey object.
 */
package uk.ac.shef.oak.com4510.data

import androidx.room.*

/**
 * Entity data class represents a single row in the database.
 */
@Entity(tableName = "journey", indices = [Index(value = ["journeyId"])],
    foreignKeys = [
        ForeignKey(
            entity = Location::class,
            parentColumns = arrayOf("locationId"),
            childColumns = arrayOf("start_location_id"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Location::class,
            parentColumns = arrayOf("locationId"),
            childColumns = arrayOf("end_location_id"),
            onDelete = ForeignKey.CASCADE
        )])
data class Journey(
    @PrimaryKey(autoGenerate = true)var journeyId: Int = 0,
    @ColumnInfo(name="title") val title: String? = null,
    @ColumnInfo(name="start_time") var time: String? = null, //time: Time? = null,
    @ColumnInfo(name="end_time") var endTime: String? = null, //var endTime: Time? = null,
    @ColumnInfo(name="date") var date: String? = null,
    @ColumnInfo(name="start_location_id") var startLocId: Int? = null,
    @ColumnInfo(name="end_location_id") var endLocId: Int? = null,)
