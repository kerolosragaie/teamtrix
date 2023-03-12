package com.kerollosragaie.teamtrix.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kerollosragaie.teamtrix.R
import com.kerollosragaie.teamtrix.databinding.ActivitySignInBinding
import com.kerollosragaie.teamtrix.utils.Utils

class SignInActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Utils.makeActivityFullScreen(this@SignInActivity)
        Utils.setUpActionbar(binding.tbSignIn,this@SignInActivity)
    }
}