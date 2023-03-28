package fr.isen.ANNE.androidsmartdevice

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class InterfaceActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interface)

        val intent = intent

        var str: String? = intent.getStringExtra("device_address")

        progressBar = findViewById(R.id.progressBar3)
        textView = findViewById(R.id.scanprocessing)
        textView.text = "CONNEXION A $str"
        // Indiquer que la connexion BLE est en cours pendant 5 secondes
        connectBLE()

    }

    private fun connectBLE() {
        textView.visibility = View.VISIBLE
        progressBar.visibility = View.VISIBLE

        Handler().postDelayed({
            textView.visibility = View.GONE
            progressBar.visibility = View.GONE
        }, 500000)
    }
}

