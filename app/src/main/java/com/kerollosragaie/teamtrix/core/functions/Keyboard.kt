package com.kerollosragaie.teamtrix.core.functions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object Keyboard {
    /**
     * To hide keyboard if shown
     * @view needs binding.root
     * @activity needs current activity
     * */
    fun hideSoftKeyboard(activity: Activity, view: View) {
        val imm = activity.applicationContext
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if(imm.isActive){
            imm.hideSoftInputFromWindow(view.windowToken,0)
        }
    }

    /**
     * To show keyboard if shown
     * @view needs this.currentFocus
     * @context needs context too
     * */

    fun showSoftKeyboard(view: View, context: Context) {
        if (view.requestFocus()) {
            val imm: InputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}