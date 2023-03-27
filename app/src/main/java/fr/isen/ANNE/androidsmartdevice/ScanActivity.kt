package fr.isen.ANNE.androidsmartdevice

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ScanActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var modifiedImage: ImageView
    private var isImageModified = false
    private lateinit var recyclerView: RecyclerView


    private lateinit var devices: ArrayList<BluetoothDevice>
    private lateinit var adapter: MyAdapter

    private lateinit var bluetoothLeScanner: BluetoothLeScanner
    private var scanCallback: ScanCallback? = null
    private val handler = Handler()

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.all { it.value }){
                scanBLEDevices()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        var scanTitle = findViewById<TextView>(R.id.ScanTitle)
        scanTitle.text = "LANCER LE SCAN"

        imageView = findViewById(R.id.play)
        var progressBar = findViewById<ProgressBar>(R.id.progressBar2)
        progressBar.isIndeterminate = false

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        devices = arrayListOf()
        adapter = MyAdapter(devices)
        recyclerView.adapter = adapter

        val bluetoothManager: BluetoothManager = getSystemService(BluetoothManager::class.java)
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter

        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
        }

        if (bluetoothAdapter?.isEnabled == true) {
            bluetoothLeScanner = bluetoothAdapter.bluetoothLeScanner
            Toast.makeText(this, "bluetooth activé", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "bluetooth désactivé", Toast.LENGTH_LONG).show()
        }

        imageView.setOnClickListener {
            if (!isImageModified) {
                scanTitle.text = "SCAN EN COURS"
                imageView.setImageResource(R.drawable.pause)
                progressBar.isIndeterminate = true

                scandeviceWithPermission()

                isImageModified = true

            } else {
                stopScan()
                scanTitle.text = "LANCER LE SCAN"
                imageView.setImageResource(R.drawable.play)
                progressBar.isIndeterminate = false

                isImageModified = false
            }
        }
    }

    private fun scandeviceWithPermission() {
        if (allPermissionGranted()) {
            scanBLEDevices()
        } else {
            requestPermissionLauncher.launch(getAllPermissions())
        }
    }

    @SuppressLint("MissingPermission")
    private fun scanBLEDevices() {
        scanCallback = object : ScanCallback() {
            override fun onScanResult(callbackType: Int, result: ScanResult) {
                val device = result.device
                if (!devices.contains(device)) {
                    devices.add(device)
                    adapter.updateDevice(device)
                    adapter.notifyDataSetChanged()
                }
            }
        }
        bluetoothLeScanner.startScan(scanCallback)
        val scanDuration=4000L
        Handler().postDelayed({
            bluetoothLeScanner.stopScan(scanCallback)
        }, scanDuration)
    }

    @SuppressLint("MissingPermission")
    private fun stopScan() {
        scanCallback?.let {
            bluetoothLeScanner.stopScan(it)
            scanCallback = null
            devices.clear()
            adapter.notifyDataSetChanged()
        }
    }

    private fun allPermissionGranted(): Boolean {
        val allPermissions = getAllPermissions()
        return allPermissions.all {
            ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }




    private fun getAllPermissions(): Array<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            arrayOf(
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.ACCESS_FINE_LOCATION ,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        } else {
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION ,
                Manifest.permission.ACCESS_COARSE_LOCATION)

        }
    }

}

