package com.ilatyphi95.gads2020leaderboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class SubmitViewModel(private val postService: PostService) : ViewModel() {
    val firstName = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val projectLink = MutableLiveData<String>()

    private val _submitEnabled = MutableLiveData(false)
    val submitEnabled : LiveData<Boolean>
        get() = _submitEnabled

    private val _eventMessage = MutableLiveData<Event<Int>>()
    val eventMessage : LiveData<Event<Int>>
        get() = _eventMessage

    fun submit() {
        if(validFields()) {
            _eventMessage.value = Event(R.string.project_submitted)
        } else {
            _eventMessage.value = Event(R.string.fill_all_fields)
        }
    }

    fun dataEdited() {
        _submitEnabled.value = validFields()
    }

    private fun validFields() = isValidName(firstName.value) && isValidName(lastName.value)
            && isValidEmail(email.value) && isValidGitHubLink(projectLink.value)
}

@Suppress("UNCHECKED_CAST")
class SubmitViewModelFactory(private val postService: PostService) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SubmitViewModel::class.java)) {
            return SubmitViewModel(postService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}