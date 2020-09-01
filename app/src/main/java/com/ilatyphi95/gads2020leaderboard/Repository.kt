package com.ilatyphi95.gads2020leaderboard

import kotlinx.coroutines.delay

class Repository : IRepository {
    override suspend fun getTopLearners(): List<Leader> {
        // simulate fetching data
        delay(2000)

        val toMutableList = generateLeaders().toMutableList()
        toMutableList.removeAt(1)

        return toMutableList
    }

    override suspend fun getTopSkills(): List<Leader> {
        // simulate fetching data
        delay(500)
        return generateLeaders()
    }
}