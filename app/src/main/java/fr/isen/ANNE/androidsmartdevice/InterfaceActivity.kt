package fr.isen.ANNE.androidsmartdevice

import android.annotation.SuppressLint
import android.bluetooth.*
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class InterfaceActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var textView: TextView
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
        // Indiquer que la connexion BLE est en cours pendant 5 secondes
        connectBLE(device)

    }

    @SuppressLint("MissingPermission")
    private fun connectBLE(device: BluetoothDevice?) {


        textView.visibility = View.VISIBLE
        progressBar.visibility = View.VISIBLE

        //Handler().postDelayed({
           // textView.visibility = View.GONE
           // progressBar.visibility = View.GONE
       // }, 5000)

        //Ui()


        currentGatt = device?.connectGatt(this, false, gattCallback)


    }

    //private fun Ui(){

   // }
    private val gattCallback = object : BluetoothGattCallback() {
        @SuppressLint("MissingPermission")
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            super.onConnectionStateChange(gatt, status, newState)
            Log.d("coucou","c est moi")
            if (newState == BluetoothProfile.STATE_CONNECTED) {
            Log.d("coucou","c est moi encore")
                // Connection established, now discover services
                runOnUiThread{
                    gatt?.discoverServices()
                }

                //Ui()


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
        // add implementation for other callback methods as needed
    }
}