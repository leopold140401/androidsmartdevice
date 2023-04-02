package fr.isen.ANNE.androidsmartdevice

import android.annotation.SuppressLint
import android.bluetooth.*
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible


class InterfaceActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var textView: TextView

    private lateinit var textLed: TextView

    private lateinit var ImageLed1: ImageView
    private lateinit var ImageLed2: ImageView
    private lateinit var ImageLed3: ImageView

    private lateinit var textAbonnement: TextView
    private lateinit var textNombre: TextView

    private lateinit var checkbox: CheckBox

    private var currentGatt: BluetoothGatt? = null


    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interface)

        val intent = intent

        val device = intent.getParcelableExtra<BluetoothDevice>("device_address")

        progressBar = findViewById(R.id.progressBar3)
        textView = findViewById(R.id.scanprocessing)



        textView.text = "CONNEXION A ${device?.name ?: "inconnu"}"
        connectBLE(device)

    }

    @SuppressLint("MissingPermission")
    private fun connectBLE(device: BluetoothDevice?) {

        textView.visibility = View.VISIBLE
        progressBar.visibility = View.VISIBLE

        currentGatt = device?.connectGatt(this, false, gattCallback)

    }

    private val gattCallback = object : BluetoothGattCallback() {
        @SuppressLint("MissingPermission")
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            super.onConnectionStateChange(gatt, status, newState)
            if (newState == BluetoothProfile.STATE_CONNECTED) {

                textView.visibility=View.INVISIBLE
                progressBar.visibility=View.INVISIBLE

                textLed=findViewById(R.id.textLed)
                textLed.visibility= View.VISIBLE

                ImageLed1=findViewById(R.id.ImageLed)
                ImageLed1.visibility= View.VISIBLE
                ImageLed2=findViewById(R.id.ImageLed2)
                ImageLed2.visibility= View.VISIBLE
                ImageLed3=findViewById(R.id.ImageLed3)
                ImageLed3.visibility= View.VISIBLE

                textAbonnement=findViewById(R.id.textAbonnement)
                textAbonnement.visibility= View.VISIBLE

                textNombre=findViewById(R.id.textNombre)
                textNombre.visibility= View.VISIBLE

                checkbox=findViewById(R.id.checkBox)
                checkbox.visibility= View.VISIBLE

                // Connection established
                runOnUiThread{
                    gatt?.discoverServices()
                }
            }

        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            super.onServicesDiscovered(gatt, status)
            val services = gatt?.services
        }

        override fun onCharacteristicRead(
            gatt: BluetoothGatt?,
            characteristic: BluetoothGattCharacteristic?,
            status: Int
        ) {
            super.onCharacteristicRead(gatt, characteristic, status)
            // TODO: add implementation for characteristic read
            if (status == BluetoothGatt.GATT_SUCCESS && characteristic != null) {
                val data = characteristic.value
                // process data read from the characteristic
            } else {
                // handle failure to read characteristic
            }
        }
    }
}