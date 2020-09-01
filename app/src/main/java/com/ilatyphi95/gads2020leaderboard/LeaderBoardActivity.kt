package com.ilatyphi95.gads2020leaderboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class LeaderBoardActivity : AppCompatActivity() {

    private lateinit var viewModel : LeaderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)

        val leaderViewModelFactory = LeaderViewModelFactory(CloudRepository(RetrofitBuilder.service))

        viewModel = ViewModelProvider(this, leaderViewModelFactory)
            .get(LeaderViewModel::class.java)

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment, CollectorFragment())
        ft.commit()
    }
}