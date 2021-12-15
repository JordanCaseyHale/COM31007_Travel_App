package uk.ac.shef.oak.com4510

import android.app.Application
import uk.ac.shef.oak.com4510.data.ImageRoomDatabase

// test push
class ImageApplication: Application() {
    val databaseObj: ImageRoomDatabase by lazy { ImageRoomDatabase.getDatabase(this) }
}
