package com.kerollosragaie.teamtrix.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.kerollosragaie.teamtrix.databinding.ActivitySignInBinding
import com.kerollosragaie.teamtrix.models.UserModel
import com.kerollosragaie.teamtrix.services.FirestoreServices
import com.kerollosragaie.teamtrix.utils.Constants
import com.kerollosragaie.teamtrix.utils.Utils
import id.ionbit.ionalert.IonAlert

class SignInActivity : BaseActivity() {
    private lateinit var binding: ActivitySignInBinding

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Utils.makeActivityFullScreen(this@SignInActivity)
        Utils.setUpActionbar(binding.tbSignIn, this@SignInActivity)

        auth = FirebaseAuth.getInstance()

        setupBttns()

    }

    private fun setupBttns() {
        binding.btnSignIn.setOnClickListener {
            Utils.hideSoftKeyboard(this@SignInActivity, binding.root)
            signInRegisteredUser()
        }
    }

    /**
     * User successfully signed in
     * */
    fun signInSuccess(userModel: UserModel) {
        mProgressDialog.dismissDialog()
        startActivity(Intent(this@SignInActivity, MainActivity::class.java))
        finish()
    }

    /**
     *Signing registered user
     */
    private fun signInRegisteredUser() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if (validateSigninForm(email, password)) {
            mProgressDialog.showDialog()
            auth.signInWithEmailAndPassword(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            ).addOnCompleteListener(this@SignInActivity) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Log.d(Constants.SIGNIN_USER, "success signin ${user!!.email}")
                    FirestoreServices().signinUser(this@SignInActivity)
                } else {
                    mProgressDialog.dismissDialog()
                    Log.w(Constants.SIGNIN_USER_ERROR, "failed to signin")
                    IonAlert(this, IonAlert.ERROR_TYPE)
                        .setTitleText("Error".uppercase())
                        .setContentText("${task.exception!!.message}")
                        .show()
                }
            }
        }

    }

    /**
     * To validate the signin form
     * */
    private fun validateSigninForm(
        email: String,
        password: String,
    ): Boolean {
        return when {
            (TextUtils.isEmpty(email) || !email.contains("@")) -> {
                showErrorSnackBar("Please enter a validate email.")
                false
            }
            (TextUtils.isEmpty(password) || password.length < 8) -> {
                showErrorSnackBar("Please enter a validate password.")
                false
            }
            else -> true
        }
    }

}