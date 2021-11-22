package com.app.neomads.utils.extensions

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.BackgroundColorSpan
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myappproject.utils.Constants
import okhttp3.RequestBody
import okio.Buffer
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale.getDefault


fun Exception.triggerException() {
    when (this) {
        is HttpException -> {
            when (code()) {
                200 -> {
                }
                300 -> {
                }
                400 -> {
                }
            }
        }
        else -> {
            Timber.e(message)
        }
    }
}

fun requestBodyToString(request: RequestBody?): String? {
    return try {
        val buffer = Buffer()
        if (request != null) request.writeTo(buffer) else return ""
        buffer.readUtf8()
    } catch (e: IOException) {
        "IO Exception"
    }

}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}


fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun <A : Activity> Activity.killSessionAndStartNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
        finish()
    }
}

fun <A : Activity> Fragment.killSessionAndStartNewActivity(activity: Class<A>) {
    Intent(getActivity(), activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
        getActivity()?.finish()

    }
}


fun String.toDate(format: String): Date? {
    val dateFormatter = SimpleDateFormat(format, Locale.US)
    return try {
        dateFormatter.parse(format)
    } catch (e: ParseException) {
        null
    }
}

fun Date.toString(format: String): String {
    val dateFormatter = SimpleDateFormat(format, getDefault())
    return dateFormatter.format(this)
}


fun Group.addOnClickListener(listener: (view: View) -> Unit) {
    referencedIds.forEach { id ->
        rootView.findViewById<View>(id).setOnClickListener(listener)
    }
}

// when 2 navigations are performed at same time, eg register and login
// nav controller assumes to have already navigated to login frag and when it fires register navigation
// it crashes because there is no action defined which navigates from login to register fragment
fun Fragment.safeNavigateFromNavController(directions: NavDirections) {
    val navController = findNavController()
    val destination = navController.currentDestination as FragmentNavigator.Destination
    //current visible fragment == fragment that is firing navigation
    if (javaClass.name == destination.className) {
        navController.navigate(directions)
    } else {
        Timber.e("Invalid navigation detected")
    }
}

fun CheckBox.addClickableLink(fullText: String, linkText: SpannableString, callback: () -> Unit) {
    val clickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) {
            widget.cancelPendingInputEvents() // Prevent CheckBox state from being toggled when link is clicked
            callback.invoke()
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = true // Show links with underlines
        }
    }
    linkText.setSpan(clickableSpan, 0, linkText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    linkText.setSpan(
        ForegroundColorSpan(Color.parseColor(Constants.Colors.LINK)),
        0,
        linkText.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    linkText.setSpan(
        BackgroundColorSpan(Color.WHITE),
        0,
        linkText.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    val fullTextWithTemplate = fullText.replace(linkText.toString(), "^1", false)
    val cs = TextUtils.expandTemplate(fullTextWithTemplate, linkText)

    text = cs
    movementMethod = LinkMovementMethod.getInstance() // Make link clickable
}


fun TextView.addClickableLink(
    underLineLink: Boolean = true,
    fullText: String,
    linkText: SpannableString,
    callback: () -> Unit
) {
    val clickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) {
            widget.cancelPendingInputEvents() // Prevent CheckBox state from being toggled when link is clicked
            callback.invoke()
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = underLineLink // Show links with underlines
        }
    }
    linkText.setSpan(clickableSpan, 0, linkText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    linkText.setSpan(
        ForegroundColorSpan(Color.parseColor(Constants.Colors.LINK)),
        0,
        linkText.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    linkText.setSpan(
        BackgroundColorSpan(Color.WHITE),
        0,
        linkText.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    val fullTextWithTemplate = fullText.replace(linkText.toString(), "^1", false)
    val cs = TextUtils.expandTemplate(fullTextWithTemplate, linkText)

    text = cs
    movementMethod = LinkMovementMethod.getInstance() // Make link clickable
}

fun ImageView.setTint(@ColorRes colorRes: Int) {
    ImageViewCompat.setImageTintList(
        this,
        ColorStateList.valueOf(ContextCompat.getColor(context, colorRes))
    )
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.show(show: Boolean) {
    this.visibility = if (show) View.VISIBLE else View.GONE
}

fun View.hide() {
    this.visibility = View.GONE
}

//https://stackoverflow.com/questions/20482849/how-to-disable-searchview/42074814#42074814
fun View.setEnabledAllChild(enabled: Boolean) {
    this.isEnabled = enabled
    if (this is ViewGroup) {
        val viewGroup = this as ViewGroup
        for (i in 0 until viewGroup.childCount) {
            val child: View = viewGroup.getChildAt(i)
            child.setEnabledAllChild(enabled)
        }
    }
}


//invokes callback if there is error occurred in pagingSource or pagination ends
//usage example: callback then checks if adapter's item list is empty or not to show/hide no list item placeholder
//https://stackoverflow.com/questions/63284793/check-if-the-list-is-empty-on-the-first-request-in-paging-3-0
//https://developer.android.com/topic/libraries/architecture/paging/load-state
//https://stackoverflow.com/a/65324885/7500631
fun <T : Any, V : RecyclerView.ViewHolder> PagingDataAdapter<T, V>.withLoadStateAdapters(
    header: LoadStateAdapter<*>,
    footer: LoadStateAdapter<*>,
    callback: (showPlaceHolder: Boolean) -> Unit
): ConcatAdapter {
    addLoadStateListener { loadStates ->
        header.loadState = loadStates.refresh
        footer.loadState = loadStates.append
        if (loadStates.source.refresh is LoadState.NotLoading &&
            loadStates.append.endOfPaginationReached &&
            itemCount < 1
        ) {
            callback(true)
        } else if (loadStates.source.refresh is LoadState.Error &&
            itemCount < 1
        ) {
            callback(true)
        } else {
            callback(false)
        }
    }

    return ConcatAdapter(header, this, footer)
}


fun getViewsByTag(root: ViewGroup, tag: String): ArrayList<View> {
    val views = ArrayList<View>()
    val childCount = root.childCount
    for (i in 0 until childCount) {
        val child = root.getChildAt(i)
        if (child is ViewGroup) {
            views.addAll(getViewsByTag(child, tag))
        }
        val tagObj = child.tag
        if (tagObj != null && tagObj == tag) {
            views.add(child)
        }
    }
    return views
}

//https://stackoverflow.com/a/66763460/7500631
fun <T : Any, V : RecyclerView.ViewHolder> PagingDataAdapter<T, V>.withLoadStateAdapters(
    header: LoadStateAdapter<*>,
    footer: LoadStateAdapter<*>
): ConcatAdapter {
    addLoadStateListener { loadStates ->
        header.loadState = loadStates.refresh
        footer.loadState = loadStates.append
    }

    return ConcatAdapter(header, this, footer)
}


