package com.ilatyphi95.gads2020leaderboard

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class ValidNameTest(
    private val expected: Boolean,
    private val name: String,
    private val scenerio: String
) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{1} is {2}")
        fun todos() = listOf(
            arrayOf(true, "Ade", "a Valid name"),
            arrayOf(true, "Ade-Ola", "a Valid name"),
            arrayOf(true, "Ade Ola", "not a valid name: Name cannot contain spaces"),
            arrayOf(false, "abcdefghijklmnopqrstuvwxyz", "not a valid name: Length greater than 25"),
            arrayOf(false, "", "not a valid name: Name cannot be empty"),
            arrayOf(false, "Ade-", "not a valid name: Name cannot be end by non-alphabet character"),
            arrayOf(false, "-Ade", "not a valid name: Name cannot be start by non-alphabet character"),
            arrayOf(false, "Ade55", "not a valid name: Name cannot contain non-alphabet character")
        )
    }

    @Test
    fun test_checkNameValidity() {
        val actual = isValidName(name)

        assertEquals(expected, actual )
    }
}