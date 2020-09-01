package com.ilatyphi95.gads2020leaderboard

import androidx.lifecycle.*
import kotlinx.coroutines.*
import java.lang.Exception
import java.lang.IllegalArgumentException

class LeaderViewModel(private val leaders: IRepository) : ViewModel(){

    private val uiScope = CoroutineScope(Job() + Dispatchers.Main)

    private var _resources = MutableLiveData<Resource<List<RecyclerItem>?>>()
    val resources : LiveData<Resource<List<RecyclerItem>?>>
        get() = _resources

    private val learningLeadersResource = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = leaders.getTopLearners().map { it.toLearningLeader() }))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    private val skillLeadersResource = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = leaders.getTopSkills().map { it.toSkillsLeader() }))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun changeToLearning() {
        _resources = learningLeadersResource as MutableLiveData<Resource<List<RecyclerItem>?>>
    }

    fun changeToSkill() {
        _resources = skillLeadersResource as MutableLiveData<Resource<List<RecyclerItem>?>>
    }

    override fun onCleared() {
        uiScope.cancel()
        super.onCleared()
    }
}

@Suppress("UNCHECKED_CAST")
class LeaderViewModelFactory(private val leaders: IRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LeaderViewModel::class.java)) {
            return LeaderViewModel(leaders) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}