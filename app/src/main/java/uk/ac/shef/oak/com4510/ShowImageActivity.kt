package uk.ac.shef.oak.com4510

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import uk.ac.shef.oak.com4510.data.ImageDataDao

class ShowImageActivity : AppCompatActivity() {
    lateinit var daoObj: ImageDataDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_image)
        val bundle: Bundle? = intent.extras
        var position = -1

        daoObj = (this@ShowImageActivity.application as ImageApplication)
            .databaseObj.imageDataDao()

        if (bundle != null) {
            // this is the image position in the itemList
            position = bundle.getInt("position")
            displayData(position)
        }
    }

    private fun displayData(position: Int){
        if (position != -1) {
            val imageView = findViewById<ImageView>(R.id.show_image)
            val titleToolbar = findViewById<Toolbar>(R.id.show_toolbar)
            val descriptionTextView = findViewById<TextView>(R.id.show_image_description)
            val imageData = MyAdapter.items[position]

            imageView.setImageBitmap(MyAdapter.items[position].thumbnail!!)
            descriptionTextView.text = MyAdapter.items[position].imageDescription

            val fabEdit: FloatingActionButton = findViewById(R.id.fab_edit)
            /*fabEdit.setOnClickListener(View.OnClickListener {
                startForResult.launch(
                    Intent( this, EditActivity::class.java).apply {
                        putExtra("position", position)
                    }
                )
            })*/
        }
    }
}