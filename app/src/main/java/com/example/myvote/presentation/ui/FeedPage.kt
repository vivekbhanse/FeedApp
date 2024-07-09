package com.example.myvote.presentation.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.myvote.common.utils
import com.example.myvote.data.PostDetails
import com.example.myvote.databinding.ActivityFeedPageBinding
import com.example.myvote.domain.room.AppDatabase
import com.example.myvote.presentation.viewmodels.FeedModel
import java.text.SimpleDateFormat
import java.util.Date

class FeedPage : AppCompatActivity() {
    private lateinit var binding: ActivityFeedPageBinding
    private lateinit var viewModel: FeedModel
    private var spinnerValue: String = ""
    private lateinit var database: AppDatabase
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = AppDatabase.getDatabase(this)
        viewModel = ViewModelProviders.of(this).get(FeedModel::class.java)
        binding.btnCreatepost.setOnClickListener {
            val sdf = SimpleDateFormat("dd-MM-yyyy")
            val currentDateAndTime = sdf.format(Date())


            sharedPreferences = this.getSharedPreferences(
                utils.sharedPrefFile,
                Context.MODE_PRIVATE
            )
            val post = binding.post.text.toString()
            val sharedNameValue = sharedPreferences.getString("usename", "defaultname")
            if (binding.post.text.length > 0) {

                viewModel.insertFeed(
                    database,
                    PostDetails(0, sharedNameValue, post.toString(), currentDateAndTime)
                )
                startActivity(Intent(this, ShowListActivity::class.java))

            }



        }
        binding.btnViewpost.setOnClickListener{
            val intent = Intent(this, ShowListActivity::class.java)
            intent.putExtra("fromButton", "csds")
            startActivity(intent)
        }


        binding.btnViewothePost.setOnClickListener{
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