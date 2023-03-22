package com.kerollosragaie.teamtrix.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.kerollosragaie.teamtrix.databinding.ActivityBaseBinding
import com.kerollosragaie.teamtrix.utils.ProgressDialog
import java.util.*

open class BaseActivity : AppCompatActivity() {
    private var doubleBackToExitPressedOnce = false

    lateinit var mProgressDialog: ProgressDialog

    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mProgressDialog = ProgressDialog(this@BaseActivity)

        binding.btnShow.setOnClickListener {
        }
    }

    /**
     * Press double twice to exit app
     * */
    private fun doubleBackToExit() {
        if (doubleBackToExitPressedOnce) {
            super.getOnBackPressedDispatcher().onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click back again to exit.", Toast.LENGTH_SHORT).show()

        Timer().schedule(object : TimerTask() {
            override fun run() {
                doubleBackToExitPressedOnce = false
            }
        }, 2000)
    }

    /**
     * To show snackbar
     * @message the shown message to the user
     * @context current activity context
     * */
    fun showErrorSnackBar(message: String) {
        val snackBar =
            Snackbar.make(
                findViewById(android.R.id.content),
                message,
                Snackbar.LENGTH_LONG
            )
        val snackbarView = snackBar.view
        snackbarView.setBackgroundColor(
            ContextCompat.getColor(
                this@BaseActivity,
                id.ionbit.ionalert.R.color.color_red
            )
        )
        snackBar.show()
    }
}