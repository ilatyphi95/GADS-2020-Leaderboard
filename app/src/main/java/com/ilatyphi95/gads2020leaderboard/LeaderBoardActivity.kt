package com.ilatyphi95.gads2020leaderboard

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class LeaderBoardActivity : AppCompatActivity() {

    private val viewModel : LeaderViewModel by viewModels {
        LeaderViewModelFactory(Repository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)

        viewModel.loadList()
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment, CollectorFragment())
        ft.commit()
    }
}