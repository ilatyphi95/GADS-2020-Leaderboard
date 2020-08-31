package com.ilatyphi95.gads2020leaderboard

data class Leader(val name: String, val desc: String) : RecyclerItemComparator {
    override fun isSameItem(other: Any): Boolean {
        if(this == other) return true
        if(javaClass != other.javaClass) return false

        other as Leader
        return this == other
    }

    override fun isSameContent(other: Any): Boolean {
        other as Leader
        return this == other
    }

}

fun Leader.toSkillsLeader() = RecyclerItem(
    data = this,
    variableId = BR.leader,
    layoutId = R.layout.top_skill_iq_item
)

fun Leader.toLearningLeader() = RecyclerItem(
    data = this,
    variableId = BR.leader,
    layoutId = R.layout.top_learner_item
)

fun generateLeaders() : List<Leader> {
    return listOf(
        Leader("Ahmed Hani", "Score 300, from Egypt"),
        Leader("Sulaiman Holo", "Score 299, from Algeria"),
        Leader("John Doe", "Score 299, from Nigeria")
    )
}