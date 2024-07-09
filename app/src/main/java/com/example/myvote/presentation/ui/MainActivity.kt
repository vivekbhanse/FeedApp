package com.example.myvote.presentation.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myvote.data.dto.PrimaryDetails
import com.example.myvote.data.room.AppDatabase
import com.example.myvote.databinding.ActivityMainBinding
import com.example.myvote.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor(
) : AppCompatActivity() {
    @Inject
    lateinit var database: AppDatabase

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    var listPrimary = emptyList<PrimaryDetails>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.RegisterButton.setOnClickListener {
            startActivity(Intent(this, RegisterPage::class.java))
        }
        binding.loginButton.setOnClickListener {
            val usename = binding.username.text.toString()
            val password = binding.password.text.toString()
            if (usename.length > 0 && password.length > 0) {


                lifecycleScope.launch(Dispatchers.Main) {
                    listPrimary = withContext(Dispatchers.Default) {
                        fetchData(usename, password)
                    }
                }

            } else {
                Toast.makeText(this, "Enter Details", Toast.LENGTH_LONG).show()
            }

        }
        binding.adminButton.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("usename", "Admin")
            editor.apply()
            editor.commit()
            val intent = Intent(this, ShowListActivity::class.java)
            intent.putExtra("fromButton", "Admin")
            startActivity(intent)

        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    private suspend fun fetchData(usename: String, password: String): List<PrimaryDetails> {
        var result: List<PrimaryDetails> = emptyList()
        val waitFor = CoroutineScope(Dispatchers.IO).async {
            listPrimary = viewModel.checkUser(database, usename, password)
            return@async result
        }
        waitFor.await()
        if (listPrimary.isNotEmpty()) {
            val name: String = binding.username.text.toString()
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("usename", name)
            editor.apply()
            editor.commit()

            startActivity(Intent(this, FeedPage::class.java))
        } else {
            runOnUiThread(Runnable {
                Toast.makeText(this, "Invalid Details", Toast.LENGTH_LONG).show()

            })
        }
        return result
    }
}