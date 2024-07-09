package com.example.myvote.presentation.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.myvote.R
import com.example.myvote.data.dto.PrimaryDetails
import com.example.myvote.data.room.AppDatabase
import com.example.myvote.databinding.ActivityRegisterPageBinding
import com.example.myvote.presentation.viewmodels.FeedModel
import com.example.myvote.presentation.viewmodels.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class RegisterPage : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterPageBinding

    private var spinnerValue: String = ""
    @Inject
    lateinit var database: AppDatabase

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val rolesValues = resources.getStringArray(R.array.Roles)
        val adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_item, rolesValues
        )
        binding.spinnerRole.adapter = adapter


        binding.btnRegister.setOnClickListener {
            if (CheckAllFields()) {
                val username = binding.usename.text.toString()
                val password = binding.password.text.toString()
                val email = binding.email.text.toString()
                val phone = binding.phone.text.toString()
                viewModel.insertItem(
                    database, PrimaryDetails(0, username, password, email, phone, spinnerValue)
                )
                startActivity(Intent(this, MainActivity::class.java))
            }else{
                Toast.makeText(this,"Fill Details",Toast.LENGTH_LONG).show()
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
        binding.run {
            if (usename.length() == 0) {
                usename.error = "This field is required"
                return false
            }
            if (password.length() == 0) {
                password.error = "This field is required"
                return false
            }

            if (email.text.isNotEmpty()) {
                val isValidEmail = isValidEmail(email.text.toString())
                if (!isValidEmail) {
                    email.error = "Email should be valid"
                }
                return isValidEmail
            }


            if (phone.length() < 9) {
                phone.error = "Phone Should Be Valid"
                return false
            }


            if (spinnerRole.getSelectedItem() != "Select Role") {
                spinnerValue = spinnerRole.getSelectedItem().toString()
            } else {

                return false
            }
        }


        // after all validation return true.
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}