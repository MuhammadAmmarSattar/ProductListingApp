package com.example.myappproject.ui.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import com.app.neomads.ui.components.customDialog.CustomDialog
import com.app.projectname.utils.event.EventUtilFunctions
import com.app.projectname.utils.event.UiEvent
import com.example.myappproject.R
import com.example.myappproject.infrastructure.ApplicationEntry
import com.example.myappproject.utils.Constants
import com.example.myappproject.utils.ProgressDialog
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

abstract class  BaseFragment<DB : ViewDataBinding> : Fragment(){
    lateinit var applicationEntry: ApplicationEntry
    protected lateinit var activity: Activity
    protected var customProgressDialog: ProgressDialog? = null
    protected lateinit var groupAdapter: GroupAdapter<GroupieViewHolder>

    private lateinit var dataBinding: DB
    protected val binding get() = dataBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = (context as Activity)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applicationEntry = requireActivity().application as ApplicationEntry
        customProgressDialog = ProgressDialog(activity)
    }
    protected abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): DB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = getFragmentBinding(inflater, container)
        return dataBinding.root
    }

    protected  fun subscribeUiEvents(baseViewModel: BaseViewModel) {
        baseViewModel.uiEvents.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()
                ?.let { event ->
                    when (event) {
                        is UiEvent.ShowAlert -> {
                            showAlert(
                                message = event.message,
                                title = event.title,
                                textPositive = event.textPositive,
                                textNegative = event.textNegative,
                                actionPositive = event.actionPositive
                            )
                        }
                        is UiEvent.ShowToast -> {
                            EventUtilFunctions.showToast(event.message, activity)
                        }
                        is UiEvent.ShowLoader -> {
                            showLoader(event.show)
                        }
                        is UiEvent.ShowSnackbar -> {
                            EventUtilFunctions.showSnackBar(
                                requireActivity(). findViewById(android.R.id.content),
                                event.message,
                                event.action
                            )
                        }
                        is UiEvent.NavigateByDirections -> {
                            navigateByDirections(event.navDirections)
                        }
                    }
                }
        })
        applicationEntry.getNetworkObservable().observe(this, {
            it.getContentIfNotHandled()?.let {
                EventUtilFunctions.showSnackBar(requireActivity().findViewById(android.R.id.content), it.message)
            }
        })
    }
    protected  fun showAlert(
        message: String,
        title: String = Constants.Alert.DEFAULT_TITLE,
        textPositive: String = Constants.Alert.DEFAULT_TEXT_POSITIVE,
        textNegative: String? = null,
        actionNegative: (() -> Unit)? = null,
        actionPositive: (() -> Unit)? = null,
    ) {
        EventUtilFunctions.showAlert(
            message = message,
            context = activity,
            title = title,
            textPositive = textPositive,
            textNegative = textNegative,
            actionPositive = actionPositive
        )
    }
    protected  fun navigateByDirections(navDirections: NavDirections) {
        EventUtilFunctions.navigateByDirections(this, navDirections)
    }
    protected   fun showLoader(show: Boolean) {
        EventUtilFunctions.showLoader(show, customProgressDialog)
    }
     fun back() {
        activity.onBackPressed()
    }
}