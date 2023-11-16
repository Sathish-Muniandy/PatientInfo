package com.example.brightbridge.RoomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patient_table")
class Patient (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val patientId:String,
    val name:String,
    val age:String,
    val gender:String,
    val location:String
)