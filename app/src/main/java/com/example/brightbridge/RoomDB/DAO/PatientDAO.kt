package com.example.brightbridge.RoomDB.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.brightbridge.RoomDB.Patient

@Dao
interface PatientDAO {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addPatient(patient: Patient)

    @Query("SELECT * FROM patient_table ORDER BY id ASC")
    fun readPatientData() : LiveData<List<Patient>>

}