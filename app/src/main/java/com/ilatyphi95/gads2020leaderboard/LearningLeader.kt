package com.ilatyphi95.gads2020leaderboard

data class LearningLeader(
    val name: String,
    val hours: Int,
    val country: String,
    val badgeUrl: String
)

fun LearningLeader.toLeader() = Leader( this.name,
    desc = "${this.hours} learning hours, ${this.country}")