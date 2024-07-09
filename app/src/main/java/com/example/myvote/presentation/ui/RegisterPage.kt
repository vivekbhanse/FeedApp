package com.example.myvote.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.myvote.R
import com.example.myvote.data.PrimaryDetails
import com.example.myvote.databinding.ActivityRegisterPageBinding
import com.example.myvote.domain.room.AppDatabase
import com.example.myvote.presentation.viewmodels.RegisterViewModel

class RegisterPage : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterPageBinding
    private lateinit var viewModel: RegisterViewModel
    private var spinnerValue: String = ""
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        database = AppDatabase.getDatabase(this)
        val rolesValues = resources.getStringArray(R.array.Roles)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, rolesValues
        )
        binding.spinnerRole.adapter = adapter


        binding.btnRegister.setOnClickListener {
            if (CheckAllFields()) {
                val username = binding.usename.text.toString()
                val password = binding.password.text.toString()
                val email = binding.email.text.toString()
                val phone = binding.phone.text.toString()
                viewModel.insertItem(database,PrimaryDetails(0,username,password,email,phone,spinnerValue))
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

//        viewModel.addedforDB.observe(this, Observer {
//            if (it != null) {
//                if (it>0) {
//                    startActivity(Intent(this, FeedPage::class.java))
//                }
//            }
//        })
    }

    private fun CheckAllFields(): Boolean {
        if (binding.usename.length() == 0) {
            binding.usename.error = "This field is required"
            return false
        }

        if (binding.password.length() == 0) {
            binding.password.error = "This field is required"
            return false
        }

        if (binding.email.length() < 6) {
            binding.email.error = "Email is required"
            return false
        }

        if (binding.phone.length() < 9) {
            binding.phone.error = "Phone Should Be Valid"
            return false
        }


        if (binding.spinnerRole.getSelectedItem() != "Select Role") {
            spinnerValue = binding.spinnerRole.getSelectedItem().toString()
        }else{

            return false
        }


        // after all validation return true.
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}