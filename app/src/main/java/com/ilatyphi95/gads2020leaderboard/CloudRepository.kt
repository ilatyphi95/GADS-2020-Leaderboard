package com.ilatyphi95.gads2020leaderboard

class CloudRepository(private val service: LeaderService) : IRepository {
    override suspend fun getTopLearners(): List<Leader> {
        return service.getLearningLeader().map { it.toLeader()}
    }

    override suspend fun getTopSkills(): List<Leader> {
        return service.getSkillLeader().map { it.toLeader() }
    }
}

