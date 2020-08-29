package com.ilatyphi95.gads2020leaderboard

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private val counter = MutableLiveData<Int>()

    private val job = CoroutineScope(Job()  + Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvSkip = findViewById<TextView>(R.id.tv_skip)
        tvSkip.setOnClickListener{
            navigateToLeaderBoard()
        }

        counter.observe(this, { count ->
            tvSkip.text = getString(R.string.skip_in_second, count)
        })

        job.launch { startCountDown(counter) }
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    private fun navigateToLeaderBoard() {
        startActivity(Intent(this, LeaderBoardActivity::class.java))
    }

    private suspend fun startCountDown(counter: MutableLiveData<Int>) {
        for (count in 3 downTo 0) {
            counter.postValue(count)
            delay(1000)
        }

        navigateToLeaderBoard()
    }
}