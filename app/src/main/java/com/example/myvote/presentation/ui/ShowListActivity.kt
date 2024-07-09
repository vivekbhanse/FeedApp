package com.example.myvote.presentation.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myvote.data.dto.PostDetails
import com.example.myvote.data.room.AppDatabase
import com.example.myvote.databinding.ActivityShowListBinding
import com.example.myvote.presentation.adapters.CustomAdapter
import com.example.myvote.presentation.viewmodels.ShowActivityModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@AndroidEntryPoint
class ShowListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowListBinding
    var listPrimary = mutableListOf<PostDetails>()
    private var sharedNameValue: String = ""
    private var profileName: String = ""

    @Inject
    lateinit var database: AppDatabase

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    val viewModel: ShowActivityModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedNameValue = sharedPreferences.getString("usename", "defaultname").toString()
        profileName = intent.getStringExtra("fromButton").toString()

        lifecycleScope.launch(Dispatchers.Main) {
            listPrimary = withContext(Dispatchers.Default) {
                fetchData(sharedNameValue)
            }
        }


    }

    private suspend fun fetchData(usename: String): MutableList<PostDetails> {
        var result: List<PostDetails> = emptyList()
        val waitFor = lifecycleScope.async {
            delay(1000)
            if (profileName.equals("Vivek") || profileName.equals("Admin")) {
                listPrimary = viewModel.checkPostAll(database).toMutableList()
            } else {
                listPrimary = viewModel.checkPost(database, usename).toMutableList()
            }
        }
        waitFor.await()

        runOnUiThread(Runnable {
            if (listPrimary.size > 0) {
                binding.recyclerview.layoutManager = LinearLayoutManager(this)
                val adapter = CustomAdapter(listPrimary, sharedNameValue, object : ShowOperation {
                    override fun delateNote(postId: String) {
                        lifecycleScope.launch {
                            viewModel.deletePost(database, postId)
                        }

                    }

                    override fun updateNote(postDetails: PostDetails) {

                        val intent = Intent(this@ShowListActivity, FeedPage::class.java)
                        intent.putExtra("postParce", postDetails)
                        startActivity(intent)

                    }


                })
                binding.recyclerview.adapter = adapter
                adapter.notifyDataSetChanged()

            } else {
                Toast.makeText(this, "No Data", Toast.LENGTH_LONG).show()
            }
        })
        return result.toMutableList()
    }


}

interface ShowOperation {
    fun delateNote(post: String)
    fun updateNote(postDetails: PostDetails)
}