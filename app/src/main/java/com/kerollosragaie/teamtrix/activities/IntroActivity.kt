package com.kerollosragaie.teamtrix.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kerollosragaie.teamtrix.databinding.ActivityIntroBinding
import com.kerollosragaie.teamtrix.utils.Utils

class IntroActivity : AppCompatActivity() {
    private lateinit var binding:ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Utils.makeActivityFullScreen(this@IntroActivity)
    }
}