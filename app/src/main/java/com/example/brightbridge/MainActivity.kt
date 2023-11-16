package com.example.brightbridge

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.brightbridge.RoomDB.Patient
import com.example.brightbridge.ViewModel.PatientViewModel
import com.example.brightbridge.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    val MyPREFERENCES = "myprefs"
    lateinit var sharedPreference : SharedPreferences
    lateinit var editor : SharedPreferences.Editor
    var gender = "Male"
    var name = ""
    var location = ""
    var age = ""
    var patientId = ""
    lateinit var  patientViewModel: PatientViewModel

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreference = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE)
        editor = sharedPreference.edit()

        patientViewModel = ViewModelProvider(this@MainActivity).get(PatientViewModel::class.java)

        binding.radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId -> // on below line we are getting radio button from our group.
            val radioButton = findViewById<RadioButton>(checkedId)
           gender = radioButton.text.toString()
        })

        binding.addBtn.setOnClickListener(View.OnClickListener {
            name = binding.nameEt.text.toString()
            age = binding.ageEt.text.toString()
            patientId = binding.patientId.text.toString()
            location = binding.locationEt.text.toString()

            if(isOnline(this@MainActivity)) {
                if(name.isNullOrEmpty() || age.isNullOrEmpty() || location.isNullOrEmpty()) {
                    Toast.makeText(this@MainActivity, "Please Fill All Details", Toast.LENGTH_SHORT).show()
                }else {
                    editor.putBoolean("AddPatient",true)
                    editor.apply()
                    var id = sharedPreference.getInt("Id",0)
                    editor.putInt("Id",id+1)
                    editor.apply()
                    patientViewModel.addPatient(Patient(0,patientId,name,age,gender,location))
                    binding.showBtn.visibility = View.VISIBLE
                    Toast.makeText(this@MainActivity, "Patient Added Successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@MainActivity,MapActivity::class.java)
                    intent.putExtra("location",location)
                    startActivity(intent)
                }
            }else {
                Toast.makeText(this@MainActivity, "Please Turn on Internet", Toast.LENGTH_SHORT).show()
            }

        })

        binding.showBtn.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainActivity,PatientDetails::class.java)
            startActivity(intent)
        })

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    override fun onResume() {
        super.onResume()
        var addPatient = sharedPreference.getBoolean("AddPatient",false)
        if(!addPatient) {
            editor.putInt("Id",1)
            editor.apply()
        }
        binding.patientId.setText("Patient Id:HRS000"+sharedPreference.getInt("Id",0).toString())
    }
}