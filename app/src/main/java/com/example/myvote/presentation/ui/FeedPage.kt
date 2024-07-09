package com.example.myvote.presentation.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myvote.data.dto.PostDetails
import com.example.myvote.data.room.AppDatabase
import com.example.myvote.databinding.ActivityFeedPageBinding
import com.example.myvote.presentation.viewmodels.FeedModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@AndroidEntryPoint
class FeedPage : AppCompatActivity() {
    @Inject
    lateinit var database: AppDatabase

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    val viewModel: FeedModel by viewModels()
    private lateinit var binding: ActivityFeedPageBinding
    private var spinnerValue: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val task = intent.getParcelableExtra<PostDetails>("postParce")
        if (task != null) {
            binding.run {
               // binding.post.text = task.
            }
        }
        binding.btnCreatepost.setOnClickListener {
            val sdf = SimpleDateFormat("dd-MM-yyyy")
            val currentDateAndTime = sdf.format(Date())

            val post = binding.post.text.toString()
            val sharedNameValue = sharedPreferences.getString("usename", "defaultname")
            if (binding.post.text.length > 0) {

                viewModel.insertFeed(
                    database, PostDetails(0, sharedNameValue, post.toString(), currentDateAndTime)
                )
                startActivity(Intent(this, ShowListActivity::class.java))

            }


        }
        binding.btnViewpost.setOnClickListener {
            val intent = Intent(this, ShowListActivity::class.java)
            intent.putExtra("fromButton", "csds")
            startActivity(intent)
        }


        binding.btnViewothePost.setOnClickListener {
            val intent = Intent(this, ShowListActivity::class.java)
            intent.putExtra("fromButton", "Vivek")
            startActivity(intent)
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}