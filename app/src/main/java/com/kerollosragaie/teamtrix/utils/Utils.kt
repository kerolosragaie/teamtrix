package com.kerollosragaie.teamtrix.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat.getSystemService
import com.kerollosragaie.teamtrix.R


object Utils {

    /**
     * To set the activity to full screen
     * */
    @Suppress("DEPRECATION")
    fun makeActivityFullScreen(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            activity.window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    /**
     * Setup top action bar
     * */
    fun setUpActionbar(view: View, context: Context) {
        val appCompatActivity = context as AppCompatActivity
        val componentActivity = context as ComponentActivity
        appCompatActivity.setSupportActionBar(view as Toolbar)
        val actionBar = appCompatActivity.supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }
        view.setNavigationOnClickListener {

            componentActivity.onBackPressedDispatcher.onBackPressed()
        }
    }

    /**
     * To hide keyboard if shown
     * @view needs this.currentFocus
     * @context needs context too
     * */
    fun hideSoftKeyboard(view: View,context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * To show keyboard if shown
     * @view needs this.currentFocus
     * @context needs context too
     * */

    fun showSoftKeyboard(view: View,context: Context) {
        if (view.requestFocus()) {
            val imm: InputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

}