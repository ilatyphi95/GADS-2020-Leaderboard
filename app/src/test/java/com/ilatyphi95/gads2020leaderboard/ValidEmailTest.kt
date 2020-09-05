package com.ilatyphi95.gads2020leaderboard

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class ValidEmailTest(
    private val expected: Boolean,
    private val email: String,
    private val scenerio: String
) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{1} is {2}")
        fun todos() = listOf(
            arrayOf(true, "user@domain.com", "a Valid email"),
            arrayOf(true, "user@domain.co.in", "a Valid email"),
            arrayOf(true, "user1@domain.com", "a Valid email"),
            arrayOf(true, "user.name@domain.com", "a Valid email"),
            arrayOf(true, "user_name@domain.co.in", "a Valid email"),
            arrayOf(true, "user-name@domain.co.in", "a Valid email"),
            arrayOf(true, "user@domaincom", "a Valid email"),
            arrayOf(false, "@domain.com", "not a Valid email: username missing"),
            arrayOf(false, "user@", "not a Valid email: domain missing")
        )
    }

    @Test
    fun test_checkEmailValidity() {
        val actual = isValidEmail(email)

        assertEquals(expected, actual )
    }
}