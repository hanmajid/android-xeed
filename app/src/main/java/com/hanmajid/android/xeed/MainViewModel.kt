package com.hanmajid.android.xeed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val refreshCount = MutableLiveData(0)

    fun refresh() {
        refreshCount.value?.let {
            refreshCount.postValue(it + 1)
        }
    }

    val loggedInUser = MutableLiveData<String?>(null)
    val isUserLoggedIn = Transformations.map(loggedInUser) {
        it != null
    }

    fun login(shipId: String) {
        loggedInUser.postValue(shipId)
    }

    fun logout() {
        loggedInUser.postValue(null)
    }
}