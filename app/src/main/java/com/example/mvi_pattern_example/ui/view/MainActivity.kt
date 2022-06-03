package com.example.mvi_pattern_example.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvi_pattern_example.data.api.ApiClient
import com.example.mvi_pattern_example.data.model.User
import com.example.mvi_pattern_example.databinding.ActivityMainBinding
import com.example.mvi_pattern_example.ui.adapter.MainAdapter
import com.example.mvi_pattern_example.ui.intent.MainIntent
import com.example.mvi_pattern_example.ui.viewmodel.MainViewModel
import com.example.mvi_pattern_example.ui.viewmodel.ViewModelFactory
import com.example.mvi_pattern_example.ui.viewstate.MainState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

/**
 * Bu foydalanuvchi bilan birga aloqa qiluvchi ativity hisoblanib,
 * MVI asosida viewModel da ko’rsatilgan state larni tekshiradi va ma’lum bir state ni viewga yuklaydi.
 */

@OptIn(ExperimentalCoroutinesApi::class)
class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private var mainAdapter = MainAdapter(arrayListOf())

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupViewModel()
        observeViewModel()
        setupClicks()
    }

    private fun setupClicks() {
        binding.buttonFetchUser.setOnClickListener {
            lifecycleScope.launch {
                mainViewModel.userIntent.send(MainIntent.FetchUser)
            }
        }
    }

    private fun setupUI() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.run {
            addItemDecoration(
                DividerItemDecoration(
                    binding.recyclerView.context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
        binding.recyclerView.adapter = mainAdapter
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(this, ViewModelFactory(ApiClient.apiService))[MainViewModel::class.java]
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            mainViewModel.state.collect {
                when (it) {
                    is MainState.Idle -> {

                    }
                    is MainState.Loading -> {
                        binding.buttonFetchUser.visibility = View.GONE
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is MainState.Users -> {
                        binding.buttonFetchUser.visibility = View.GONE
                        binding.progressBar.visibility = View.GONE
                        renderList(it.user)
                    }
                    is MainState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.buttonFetchUser.visibility = View.VISIBLE
                        Toast.makeText(this@MainActivity, it.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderList(users: List<User>) {
        binding.recyclerView.visibility = View.VISIBLE
        users.let { listOfUsers -> listOfUsers.let { mainAdapter.addData(it) }
        }
        mainAdapter.notifyDataSetChanged()
    }
}