package fr.isen.ANNE.androidsmartdevice

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val dataList: ArrayList<BluetoothDevice>, var onDeviceClickListener: (BluetoothDevice) -> Unit ) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.item_title)
        val description: TextView = itemView.findViewById(R.id.item_description)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.my_item_layout, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("MissingPermission")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.description.text = currentItem.address
        holder.itemView.setOnClickListener {
            onDeviceClickListener(dataList[position])
        }
    }

    override fun getItemCount() = dataList.size

    fun updateDevice(newDevice: BluetoothDevice){
        var should = true
        dataList.forEachIndexed {
            index, element ->
            if (element.address == newDevice.address){
                dataList[index] = newDevice
                should=false
            }
        }
        if(should){
            dataList.add(newDevice)
        }

    }

}
