package com.example.myappproject.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.projectname.utils.event.Event
import com.app.projectname.utils.event.UiEvent

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    private val _uiEventsLiveData = MutableLiveData<Event<UiEvent>>()
    val uiEvents: LiveData<Event<UiEvent>> = _uiEventsLiveData

    fun showLoader(show: Boolean) {
        _uiEventsLiveData.value = (Event(UiEvent.ShowLoader(show)))
    }

    fun showAlert(title: String = "Alert", message: String) {
        _uiEventsLiveData.postValue(Event(UiEvent.ShowAlert(title = title, message = message)))
    }
    fun showToast(message: String) {
//        _uiEventsLiveData.value = Event(UiEvent.ShowToast(message))
        _uiEventsLiveData.postValue(Event(UiEvent.ShowToast(message)))
    }

    fun showSnackbar(message: String, action: (() -> Unit)? = null) {
        _uiEventsLiveData.postValue(Event(UiEvent.ShowSnackbar(message, action)))

    }
}