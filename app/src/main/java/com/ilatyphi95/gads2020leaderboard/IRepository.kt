package com.ilatyphi95.gads2020leaderboard

interface IRepository {
    suspend fun getTopLearners() : List<Leader>

    suspend fun getTopSkills() : List<Leader>
}