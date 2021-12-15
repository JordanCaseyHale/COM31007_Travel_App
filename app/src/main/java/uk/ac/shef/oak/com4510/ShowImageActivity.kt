package uk.ac.shef.oak.com4510

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ShowImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_image)
        val b: Bundle? = intent.extras
        var position = -1
    }
}