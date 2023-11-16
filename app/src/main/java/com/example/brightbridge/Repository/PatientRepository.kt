package com.example.brightbridge.Repository

import androidx.lifecycle.LiveData
import com.example.brightbridge.RoomDB.DAO.PatientDAO
import com.example.brightbridge.RoomDB.Patient

class PatientRepository(private val patientDAO: PatientDAO) {

    var readPatients : LiveData<List<Patient>>?=null
        get() = patientDAO.readPatientData()

    fun getNotes() : LiveData<List<Patient>> {
        readPatients = patientDAO.readPatientData()
        return readPatients!!
    }

    suspend fun addPatient(patient: Patient) {
        patientDAO.addPatient(patient)
    }

}