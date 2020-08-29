package com.ilatyphi95.gads2020leaderboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class LeaderBoardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment, CollectorFragment())
        ft.commit()
    }
}