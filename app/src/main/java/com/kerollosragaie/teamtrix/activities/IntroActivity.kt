package com.kerollosragaie.teamtrix.activities

import android.content.Intent
import android.os.Bundle
import com.kerollosragaie.teamtrix.databinding.ActivityIntroBinding
import com.kerollosragaie.teamtrix.core.functions.Utils

class IntroActivity : BaseActivity() {
    private lateinit var binding:ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Utils.makeActivityFullScreen(this@IntroActivity)

        //Setup buttons
        setupButtons()
    }

    private fun setupButtons(){
        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }
        binding.btnSignIn.setOnClickListener {
            startActivity(Intent(this,SignInActivity::class.java))
        }
    }

}