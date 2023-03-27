package fr.isen.ANNE.androidsmartdevice

import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ScanActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var modifiedImage: ImageView
    private var isImageModified = false

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        var scanTitle = findViewById<TextView>(R.id.ScanTitle)
        scanTitle.text = "LANCER LE SCAN";

        imageView = findViewById(R.id.play)
        var progressBar= findViewById<ProgressBar>(R.id.progressBar2)
        progressBar.isIndeterminate=false


        imageView.setOnClickListener {
            if (!isImageModified) {
                scanTitle.text = "SCAN EN COURS";
                imageView.setImageResource(R.drawable.pause)
                progressBar.isIndeterminate=true
                isImageModified = true


            } else {
                scanTitle.text = "LANCER LE SCAN";
                imageView.setImageResource(R.drawable.play)
                progressBar.isIndeterminate=false
                isImageModified = false
            }
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val myDataList = listOf(MyData("Title 1", "Description 1"), MyData("Title 2", "Description 2"))
        val adapter = MyAdapter(myDataList)
        recyclerView.adapter = adapter

    }
}
