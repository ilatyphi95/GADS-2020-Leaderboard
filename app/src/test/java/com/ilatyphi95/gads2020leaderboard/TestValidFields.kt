package com.ilatyphi95.gads2020leaderboard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.atMost
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.mockito.Mockito.mock


@RunWith(Parameterized::class)
class TestValidFields(
    private val expected: Boolean,
    private val firstName: String,
    private val lastName: String,
    private val email: String,
    private val projectLink: String,
    private val scenerio: String
) {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    companion object{
        @JvmStatic
        @Parameterized.Parameters(name = "{5}")
        fun todos() = listOf(
            arrayOf(false, "", "Adewale", "user@domain.com","github", "invalid first name"),
            arrayOf(false, "Adedayo", "", "user@domain.com","github", "invalid last name"),
            arrayOf(false, "Adedayo", "Adewale", "@domain.com","github", "invalid email"),
            arrayOf(false, "Adedayo", "Adewale", "user@domain.com","", "invalid email"),
            arrayOf(false, "Adedayo", "Adewale", "user@domain.com","github", "valid fields")
        )
    }

    @Test
    fun test_checkFieldsValidity() {
        val mockSubmitViewModel = mock(PostService::class.java)
        val submitViewModel = SubmitViewModel(mockSubmitViewModel)

        submitViewModel.firstName.value = firstName
        submitViewModel.lastName.value = lastName
        submitViewModel.email.value = email
        submitViewModel.projectLink.value = projectLink
        if(expected)
            verify(mockSubmitViewModel, atMost(1)).postProject(firstName, lastName, email, projectLink)
        else
            verify(mockSubmitViewModel, never()).postProject(firstName, lastName, email, projectLink)
    }
}