package com.example.myvote.presentation.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myvote.adapters.CustomAdapter
import com.example.myvote.common.utils
import com.example.myvote.data.PostDetails
import com.example.myvote.databinding.ActivityShowListBinding
import com.example.myvote.domain.room.AppDatabase
import com.example.myvote.presentation.viewmodels.ShowActivityModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShowListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowListBinding
    var listPrimary = emptyList<PostDetails>()
    private lateinit var viewModel: ShowActivityModel
    private lateinit var database: AppDatabase
    private var sharedNameValue: String = ""
    private var profileName: String = ""
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = AppDatabase.getDatabase(this)
        viewModel = ViewModelProviders.of(this).get(ShowActivityModel::class.java)
        sharedPreferences = this.getSharedPreferences(
            utils.sharedPrefFile,
            Context.MODE_PRIVATE
        )
        sharedNameValue = sharedPreferences.getString("usename", "defaultname").toString()
        profileName = intent.getStringExtra("fromButton").toString()

        lifecycleScope.launch(Dispatchers.Main) {
            listPrimary = withContext(Dispatchers.Default) {
                fetchData(sharedNameValue)
            }
        }



    }

    private suspend fun fetchData(usename: String): List<PostDetails> {
        var result: List<PostDetails> = emptyList()
        val waitFor = CoroutineScope(Dispatchers.IO).async {
            if (profileName.equals("Vivek") ||profileName.equals("Admin")) {
                listPrimary = viewModel.checkPostAll(database)
            } else {
                listPrimary = viewModel.checkPost(database, usename)
            }
            return@async result
        }
        waitFor.await()

        runOnUiThread(Runnable {
            if (listPrimary.size > 0) {

                binding.recyclerview.layoutManager = LinearLayoutManager(this)
                val adapter = CustomAdapter(listPrimary, sharedNameValue, object : ShowOperation {
                    override fun delateNote(noteId: String) {
                        lifecycleScope.launch {
                            viewModel.deletePost(database,noteId)
                        }

                    }
                })
                binding.recyclerview.adapter = adapter
                adapter.notifyDataSetChanged()

            } else {
                Toast.makeText(this, "No Data", Toast.LENGTH_LONG).show()
            }
        })
        return result
    }


}
interface ShowOperation{
    fun delateNote(post:String)
}