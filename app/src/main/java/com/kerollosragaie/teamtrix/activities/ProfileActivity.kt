package com.kerollosragaie.teamtrix.activities

import android.os.Build
import android.os.Bundle
import com.bumptech.glide.Glide
import com.kerollosragaie.teamtrix.R
import com.kerollosragaie.teamtrix.core.Constants.USER_DATA
import com.kerollosragaie.teamtrix.core.functions.Utils
import com.kerollosragaie.teamtrix.databinding.ActivityProfileBinding
import com.kerollosragaie.teamtrix.models.UserModel

class ProfileActivity : BaseActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var currentUser: UserModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpActionbar()

        loadUserData()

        setupBttns()
    }


    private fun setUpActionbar() {
        setSupportActionBar(binding.topAppBar.toolbar)
        Utils.setUpActionbar(binding.topAppBar.toolbar,this@ProfileActivity,"My profile")
    }

    /**
    Load user data from previous activity
     */
    @Suppress("DEPRECATION")
    private fun loadUserData() {
        currentUser = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(USER_DATA,UserModel::class.java)!!
        } else {
            intent.getParcelableExtra(USER_DATA)!!
        }
        Glide
            .with(this)
            .load(currentUser.image)
            .centerCrop()
            .placeholder(R.drawable.ic_profile)
            .into(binding.ivUserImage)
        binding.etName.setText(currentUser.name)
        binding.etEmail.setText(currentUser.email)
        binding.etMobile.setText(currentUser.mobile.toString())
    }

    private fun setupBttns(){
        //TODO: validate name and mobile then update using firebase services
        binding.btnUpdate.setOnClickListener {

        }
    }

}