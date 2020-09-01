package com.ilatyphi95.gads2020leaderboard

import kotlinx.coroutines.delay

class Repository : IRepository {
    override suspend fun getTopLearners(): List<Leader> {
        // simulate fetching data
        delay(2000)

        val imgUrl =
            "https://res.cloudinary.com/mikeattara/image/upload/v1596700848/Top-learner.png"

        return listOf(
            Leader("Ahmed Hani", "Score 300, from Egypt", imgUrl),
            Leader("John Doe", "Score 299, from Nigeria", imgUrl)
        )
    }

    override suspend fun getTopSkills(): List<Leader> {
        // simulate fetching data
        delay(500)

        val imgUrl =
            "https://res.cloudinary.com/mikeattara/image/upload/v1596700835/skill-IQ-trimmed.png"
        return listOf(
            Leader("Ahmed Hani", "Score 300, from Egypt", imgUrl),
            Leader("Sulaiman Holo", "Score 299, from Algeria", imgUrl),
            Leader("John Doe", "Score 299, from Nigeria", imgUrl)
        )
    }
}