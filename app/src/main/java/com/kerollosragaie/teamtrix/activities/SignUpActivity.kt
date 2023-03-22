package com.kerollosragaie.teamtrix.activities

import android.os.Bundle
import android.text.TextUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.kerollosragaie.teamtrix.databinding.ActivitySignUpBinding
import com.kerollosragaie.teamtrix.models.UserModel
import com.kerollosragaie.teamtrix.services.FirestoreServices
import com.kerollosragaie.teamtrix.utils.Utils
import id.ionbit.ionalert.IonAlert

class SignUpActivity : BaseActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        Utils.makeActivityFullScreen(this@SignUpActivity)
        Utils.setUpActionbar(binding.tbSignUp, this@SignUpActivity)

        setupBttns()
    }

    //To setup buttons
    private fun setupBttns() {
        binding.btnCreateAccount.setOnClickListener {
            Utils.hideSoftKeyboard(this@SignUpActivity, binding.root)
            registerUser()
        }
    }

    /**
     *
     * */
    fun userRegisteredSuccess() {
        mProgressDialog.dismissDialog()
        IonAlert(this@SignUpActivity, IonAlert.SUCCESS_TYPE)
            .setTitleText("Account created".uppercase())
            .setContentText("Your email has successfully created successfully.")
            .setConfirmClickListener {
                it.dismiss()
                FirebaseAuth.getInstance().signOut()
                finish()
            }
            .show()
    }

    //For signup new user
    private fun registerUser() {
        val name: String = binding.etName.text.toString()
        val email: String = binding.etEmail.text.toString().trim { it <= ' ' }
        val password: String = binding.etPassword.text.toString()
        val confirmPas: String = binding.etConfirmPassword.text.toString()
        if (validateSignupForm(name, email, password, confirmPas)) {
            mProgressDialog.showDialog("Please wait")
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        val userModel = UserModel(
                            firebaseUser.uid,
                            name,
                            email
                        )
                        FirestoreServices().registerUser(this@SignUpActivity, userModel)
                    } else {
                        mProgressDialog.dismissDialog()
                        IonAlert(this, IonAlert.ERROR_TYPE)
                            .setTitleText("Error".uppercase())
                            .setContentText("${task.exception!!.message}")
                            .show()
                    }
                }
        }
    }

    /**
     * To validate the form
     * */
    private fun validateSignupForm(
        name: String,
        email: String,
        password: String,
        confirmPass: String
    ): Boolean {
        return when {
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
                showErrorSnackBar(
                    "Your password and confirmation password do not match."
                )
                false
            }
            else -> true
        }
    }

}