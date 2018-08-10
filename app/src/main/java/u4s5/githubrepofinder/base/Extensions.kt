package u4s5.githubrepofinder.base

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

private const val NO_ADDITIONAL_ACTIONS_FLAG = 0

fun View.visible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.isVisible() = visibility == View.VISIBLE

fun View.showKeyboard(visible: Boolean, flag: Int = NO_ADDITIONAL_ACTIONS_FLAG) {
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    if (visible) {
        requestFocus()
        isFocusableInTouchMode = true
        inputManager?.showSoftInput(this, flag)
    } else {
        isFocusable = false
        inputManager?.hideSoftInputFromWindow(windowToken, flag)
    }
}

fun ViewGroup.inflate(@LayoutRes layout: Int, attachToRoot: Boolean = false): View =
        LayoutInflater.from(this.context).inflate(layout, this, attachToRoot)

fun Context.string(@StringRes stingRes: Int): String = getString(stingRes)

fun Context.middleToast(@StringRes stringRes: Int, duration: Int = Toast.LENGTH_SHORT) =
        Toast.makeText(this, stringRes, duration).run {
            setGravity(Gravity.CENTER, 0, 0)
            show()
        }
