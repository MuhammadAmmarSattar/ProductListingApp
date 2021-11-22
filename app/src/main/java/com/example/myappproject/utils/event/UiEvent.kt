package com.app.projectname.utils.event

import androidx.navigation.NavDirections
import com.example.myappproject.utils.Constants

sealed class UiEvent {
    class ShowLoader(val show: Boolean) : UiEvent()
    class ShowToast(val message: String) : UiEvent()
    class ShowSnackbar(val message: String, val action: (() -> Unit)? = null) : UiEvent()
    class ShowAlert(
        val title: String = Constants.Alert.DEFAULT_TITLE,
        val message: String,
        val textPositive: String = Constants.Alert.DEFAULT_TEXT_POSITIVE,
        val textNegative: String? = null,
        val actionPositive: (() -> Unit)? = null
    ) : UiEvent()
    class NavigateByDirections(val navDirections: NavDirections) : UiEvent()

}