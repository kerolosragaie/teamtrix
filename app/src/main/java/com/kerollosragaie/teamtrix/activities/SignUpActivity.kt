package com.kerollosragaie.teamtrix.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.kerollosragaie.teamtrix.R
import com.kerollosragaie.teamtrix.databinding.ActivitySignUpBinding
import com.kerollosragaie.teamtrix.utils.Utils

class SignUpActivity : BaseActivity() {
    private lateinit var binding:ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        Utils.makeActivityFullScreen(this@SignUpActivity)
        Utils.setUpActionbar(binding.tbSignUp,this@SignUpActivity)

        setupBttns()
    }

    //To setup buttons
    private fun setupBttns(){
        binding.btnCreateAccount.setOnClickListener {
            registerUser()
        }
    }

    //For signup new user
    private fun registerUser(){
        val name:String = binding.etName.text.toString()
        val email:String = binding.etEmail.text.toString().trim{it <= ' '}
        val password:String = binding.etPassword.text.toString()
        val confirmPas:String = binding.etConfirmPassword.text.toString()
        if(validateForm(name,email,password,confirmPas)){
            Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateForm(name:String,email:String,password:String,confirmPass:String):Boolean{
        return when{
            TextUtils.isEmpty(name) -> {
                showErrorSnackBar("Please enter a validate name.")
                false
            }
            (TextUtils.isEmpty(email) || !email.contains("@")) -> {
                showErrorSnackBar("Please enter a validate email.")
                false
            }
            (TextUtils.isEmpty(password) || password.length < 8) -> {
                showErrorSnackBar("Please enter a validate password.")
                false
            }
            (password != confirmPass) -> {
                showErrorSnackBar("Your password and confirmation password do not match.")
                false
            }
            else -> true
        }
    }

}