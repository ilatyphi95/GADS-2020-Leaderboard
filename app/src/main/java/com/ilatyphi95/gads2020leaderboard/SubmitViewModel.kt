package com.ilatyphi95.gads2020leaderboard

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.*

class SubmitViewModel(private val postService: PostService) : ViewModel() {

    private val uiScope = CoroutineScope(Job() + Dispatchers.Main)
    val firstName = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val projectLink = MutableLiveData<String>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean>
        get() = _isLoading

    private val _submit = MutableLiveData<Event<Boolean>>()
    val submit : LiveData<Event<Boolean>>
        get() = _submit

    private val _submitEnabled = MutableLiveData(false)
    val submitEnabled : LiveData<Boolean>
        get() = _submitEnabled

    private val _eventConfirmSubmission = MutableLiveData(Event(false))
    val eventConfirmSubmission : LiveData<Event<Boolean>>
        get() = _eventConfirmSubmission

    private val _eventMessage = MutableLiveData<Event<Int>>()
    val eventMessage : LiveData<Event<Int>>
        get() = _eventMessage

    fun submit() {

        if(validFields()) {
            _eventConfirmSubmission.value = Event(true)

        } else {
            _eventMessage.value = Event(R.string.fill_all_fields)
        }
    }

    private fun loading(isLoading: Boolean) {
        _isLoading.postValue(isLoading)
        _submitEnabled.postValue(!isLoading)
    }

    fun dataEdited() {
        _submitEnabled.value = validFields()
    }

    fun submissionConfirmed() {

        uiScope.launch {
            loading(true)
            withContext(Dispatchers.IO) {
                try {
                    testString()

//                        postService.postProject(firstName.value!!, lastName.value!!,
//                            email.value!!, projectLink.value!!)

                    _submit.postValue(Event(true))
                } catch (exception: Exception) {
                    _submit.postValue(Event(false))
                    Log.d("SubmitViewModel", exception.message ?: "Error Occurred")
                }
            }
            loading(false)
        }
    }

    private fun validFields(): Boolean {
        return isValidName(firstName.value) && isValidName(lastName.value) &&
                isValidEmail(email.value) && isValidGitHubLink(projectLink.value)
    }

    private fun showMessage(@StringRes message: Int) {
        _eventMessage.postValue(Event(message))
    }

    private suspend fun testString() : Int {
        delay(5000)
        return R.string.project_submitted
    }
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