package com.example.finalproject_6_1.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import kotlin.math.roundToInt

object UtilWindow {

    /**-----------------------------------------------------------------------------------------
     * --------------------------------------GET DPI----------------------------------------
     * -----------------------------------------------------------------------------------------*/
    fun getDpi(context: Context): Int {
        val displayMetrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.densityDpi
    }

    /**-----------------------------------------------------------------------------------------
     * --------------------------------------GET IMEI----------------------------------------
     * -----------------------------------------------------------------------------------------*/
    @SuppressLint("HardwareIds")
    fun getDeviceId(context: Context, sub10char: Boolean): String? {
        return try {
            val id = Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            )
            if (sub10char){
                return if(id.length > 10) id.substring(0, 10) else id
            }
            return  id
        } catch (e: Exception) {
            ""
        }
    }

    /**-----------------------------------------------------------------------------------------
     * --------------------------------------CHECK CLICK----------------------------------------
     * -----------------------------------------------------------------------------------------*/
    const val TIME_CLICK_DELAY: Long = 1000
    private var mOldClickTime: Long = 0
    fun isClickEvent(timeDelay: Long): Boolean {
        val time = System.currentTimeMillis()
        if (time - mOldClickTime < timeDelay) return false
        mOldClickTime = time
        return true
    }

    /**-----------------------------------------------------------------------------------------
     * -------------------------------------HIDE KEYBOARD---------------------------------------
     * -----------------------------------------------------------------------------------------*/
    fun hideKeyboard(activity: Activity?) {
        if (activity != null) {
            val imm: InputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
        }
    }

    /**-----------------------------------------------------------------------------------------
     * -------------------------------GET WIDTH HEIGHT DISPLAY----------------------------------
     * -----------------------------------------------------------------------------------------*/
    fun getWidth(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        return displayMetrics.widthPixels
    }

    fun getHeight(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        return displayMetrics.heightPixels
    }

    /**-----------------------------------------------------------------------------------------
     * --------------------------------------Convert dimen----------------------------------------
     * -----------------------------------------------------------------------------------------*/
    fun dpToPx(dp: Int, context: Context?): Int {
        val displayMetrics: DisplayMetrics = context?.resources!!.displayMetrics
        return (dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
    }

    fun pxToDp(px: Int, context: Context?): Int {
        val displayMetrics: DisplayMetrics = context?.resources!!.displayMetrics
        return (px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
    }

    fun spToPx(sp: Float, context: Context): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.resources.displayMetrics)
    }
}