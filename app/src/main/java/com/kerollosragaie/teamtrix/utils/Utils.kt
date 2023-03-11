package com.kerollosragaie.teamtrix.utils

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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
    fun setUpActionbar(view: View,appCompatActivity : AppCompatActivity,componentActivity : ComponentActivity){
        appCompatActivity.setSupportActionBar(view as Toolbar)
        val actionBar = appCompatActivity.supportActionBar
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }
        view.setNavigationOnClickListener {
            componentActivity.onBackPressedDispatcher.onBackPressed()
        }
    }

}