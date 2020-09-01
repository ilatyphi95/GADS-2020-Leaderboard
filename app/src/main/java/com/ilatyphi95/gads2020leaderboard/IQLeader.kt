package com.ilatyphi95.gads2020leaderboard

data class IQLeader(
    val name: String,
    val score: Int,
    val country: String,
    val badgeUrl: String
)

fun IQLeader.toLeader() = Leader(this.name,
    desc = "${this.score} skill IQ Score, ${this.country}", this.badgeUrl)