package com.ilatyphi95.gads2020leaderboard

import androidx.lifecycle.*
import kotlinx.coroutines.*
import java.lang.IllegalArgumentException

class LeaderViewModel(private val leaders: IRepository) : ViewModel(){

    private val uiScope = CoroutineScope(Job() + Dispatchers.Main)

    private val _learningList = MutableLiveData<List<RecyclerItem>>()
    val learningList : LiveData<List<RecyclerItem>>
        get() = _learningList

    private val _iqList = MutableLiveData<List<RecyclerItem>>()
    val iqList : LiveData<List<RecyclerItem>>
        get() = _iqList

    fun loadList() {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                _learningList.postValue(leaders.getTopLearners().map { it.toLearningLeader()})
                _iqList.postValue(leaders.getTopSkills().map { it.toSkillsLeader()})
            }
        }
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