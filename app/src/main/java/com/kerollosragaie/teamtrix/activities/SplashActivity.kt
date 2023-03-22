package com.kerollosragaie.teamtrix.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kerollosragaie.teamtrix.databinding.ActivitySplashBinding
import com.kerollosragaie.teamtrix.services.FirestoreServices
import com.kerollosragaie.teamtrix.utils.Utils
import java.util.*

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Utils.makeActivityFullScreen(this@SplashActivity)

        navigateToActivity()
    }

    /**
     * ?After n seconds change activity
     * */
    private fun navigateToActivity() {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                var currentUserId = FirestoreServices().getCurrentUserId()
                if (currentUserId.isNotEmpty()) {
                    //val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    val intent = Intent(this@SplashActivity, IntroActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(this@SplashActivity, IntroActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }, 3000)
    }
}