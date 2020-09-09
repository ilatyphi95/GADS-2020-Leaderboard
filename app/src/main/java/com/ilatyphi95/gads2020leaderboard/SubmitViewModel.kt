package com.ilatyphi95.gads2020leaderboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        _submitEnabled.postValue(validFields())
    }

    fun submissionConfirmed() {

        uiScope.launch {
            loading(true)
            withContext(Dispatchers.IO) {

                postService.postProject(
                    firstName.value!!, lastName.value!!,
                    email.value!!, projectLink.value!!
                ).enqueue(object : Callback<Void> {

                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if(response.isSuccessful) {
                            _submit.postValue(Event(true))
                        } else {
                            _submit.postValue(Event(false))
                        }
                        Log.d("SubmitViewModel", response.message() ?: "Error Occurred")
                        loading(false)
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        _submit.postValue(Event(false))
                        Log.d("SubmitViewModel", t.message ?: "Error Occurred")
                        loading(false)
                    }
                })
            }
        }
    }

    private fun validFields(): Boolean {
        return isValidName(firstName.value) && isValidName(lastName.value) &&
                isValidEmail(email.value) && isValidGitHubLink(projectLink.value)
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