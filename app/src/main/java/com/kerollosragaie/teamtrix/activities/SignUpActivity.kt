package com.kerollosragaie.teamtrix.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kerollosragaie.teamtrix.R
import com.kerollosragaie.teamtrix.databinding.ActivitySignUpBinding
import com.kerollosragaie.teamtrix.utils.Utils

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        Utils.makeActivityFullScreen(this@SignUpActivity)
        Utils.setUpActionbar(binding.tbSignUp,this@SignUpActivity)
    }
}