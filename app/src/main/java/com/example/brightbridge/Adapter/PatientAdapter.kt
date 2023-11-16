package com.example.brightbridge.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.brightbridge.R
import com.example.brightbridge.RoomDB.Patient


class PatientAdapter(list:ArrayList<Patient>) : RecyclerView.Adapter<PatientAdapter.MyViewHolder>() {

    var list:ArrayList<Patient> = ArrayList()
    fun addPatients(list: ArrayList<Patient>) {
        this.list = list
        notifyDataSetChanged()
    }

    class MyViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var idTv = holder.itemView.findViewById<TextView>(R.id.pId)
        var nameTv = holder.itemView.findViewById<TextView>(R.id.pName)
        var ageTv = holder.itemView.findViewById<TextView>(R.id.pAge)
        var locationTv = holder.itemView.findViewById<TextView>(R.id.pLocation)
        idTv.setText(list.get(position).patientId)
        nameTv.setText("Patient Name: "+list.get(position).name)
        ageTv.setText("Age: "+list.get(position).age+" Gender:"+list.get(position).gender)
        locationTv.setText("Location: "+list.get(position).location)

    }

    override fun getItemCount(): Int {
        return list.size
    }

}