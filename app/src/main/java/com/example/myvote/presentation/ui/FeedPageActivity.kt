package com.example.myvote.presentation.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myvote.data.dto.PostDetails
import com.example.myvote.data.room.AppDatabase
import com.example.myvote.databinding.ActivityFeedPageBinding
import com.example.myvote.presentation.viewmodels.FeedModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class FeedPageActivity : AppCompatActivity() {
    @Inject
    lateinit var database: AppDatabase

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    val viewModel: FeedModel by viewModels()
    private lateinit var binding: ActivityFeedPageBinding
    private var spinnerValue: String = ""
    private var curruntProfile: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        curruntProfile = sharedPreferences.getString("usename", "defaultname").toString()
        val tasks = intent.getParcelableExtra<PostDetails>("postParce")
        with(binding) {
            post.setText(tasks?.post)
            btnViewpost.visibility = if (curruntProfile == "Admin") View.GONE else View.VISIBLE
        }
        binding.btnCreatepost.setOnClickListener {
            val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val currentDateAndTime = sdf.format(Date())
            val post = binding.post.text.toString()
            val sharedNameValue = sharedPreferences.getString("usename", "defaultname")
            val postLength = binding.post.text.length

            if (postLength > 0) {
                tasks?.let {
                    viewModel.updatePost(it.id.toString(), post, currentDateAndTime)
                } ?: run {
                    viewModel.insertFeed(PostDetails(0, sharedNameValue, post, currentDateAndTime))
                }
                if (!curruntProfile.equals("Admin")) {
                    startActivity(Intent(this, ShowListActivity::class.java))
                } else {
                    binding.post.setText("")
                    var toast=Toast.makeText(this, "Feed Added..", Toast.LENGTH_SHORT)
                    toast.setGravity(
                        Gravity.CENTER,0,0)
                    toast.show()

                }
            }
        }
        binding.btnViewpost.setOnClickListener {
            val intent = Intent(this, ShowListActivity::class.java)
            intent.putExtra("fromButton", "NO")
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