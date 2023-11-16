package com.example.brightbridge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brightbridge.Adapter.PatientAdapter
import com.example.brightbridge.RoomDB.Patient
import com.example.brightbridge.ViewModel.PatientViewModel
import com.example.brightbridge.databinding.ActivityPatientDetailsBinding

class PatientDetails : AppCompatActivity() {

    lateinit var binding: ActivityPatientDetailsBinding
    lateinit var viewModel: PatientViewModel
    var list = ArrayList<Patient>()
    lateinit var patientAdapter: PatientAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        patientAdapter = PatientAdapter(list)
        binding.patientRv.adapter = patientAdapter
        binding.patientRv.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProvider(this).get(PatientViewModel::class.java)

        viewModel.Read().observe(this, Observer {
            var arrayList : ArrayList<Patient> = it as ArrayList<Patient>
            patientAdapter.addPatients(arrayList)
        })

    }
}