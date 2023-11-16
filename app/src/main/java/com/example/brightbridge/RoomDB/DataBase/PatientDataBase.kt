package com.example.brightbridge.RoomDB.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.brightbridge.RoomDB.DAO.PatientDAO
import com.example.brightbridge.RoomDB.Patient

@Database(entities = [Patient::class], version = 1, exportSchema = false)
abstract class PatientDataBase : RoomDatabase() {

    abstract fun patientDAO() : PatientDAO

    companion object {
        @Volatile
        private var INSTANCE : PatientDataBase? = null

        fun getDataBase(context: Context) : PatientDataBase {
            val tempInstacew = INSTANCE
            if(tempInstacew!=null) {
                return tempInstacew
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PatientDataBase::class.java,
                    "patient_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}