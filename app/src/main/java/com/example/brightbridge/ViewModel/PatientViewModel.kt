package com.example.brightbridge.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.brightbridge.Repository.PatientRepository
import com.example.brightbridge.RoomDB.DataBase.PatientDataBase
import com.example.brightbridge.RoomDB.Patient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PatientViewModel(application: Application) : AndroidViewModel(application) {

    var readPatients : LiveData<List<Patient>>? = null
    private val repository:PatientRepository

    init {
        val patientDAO = PatientDataBase.getDataBase(application).patientDAO()
        repository = PatientRepository(patientDAO)
        readPatients = repository.readPatients
    }

    fun addPatient(patient:Patient) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPatient(patient)
        }
    }

    fun Read() : LiveData<List<Patient>> {
        return readPatients!!
    }

    fun ReadNote() {
        readPatients = repository.getNotes()
    }

}