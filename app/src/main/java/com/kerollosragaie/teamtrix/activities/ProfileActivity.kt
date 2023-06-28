package com.kerollosragaie.teamtrix.activities

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.kerollosragaie.teamtrix.R
import com.kerollosragaie.teamtrix.core.Constants.USER_DATA
import com.kerollosragaie.teamtrix.core.functions.Utils
import com.kerollosragaie.teamtrix.databinding.ActivityProfileBinding
import com.kerollosragaie.teamtrix.models.UserModel
import java.io.IOException

class ProfileActivity : BaseActivity() {
    private val binding by lazy { ActivityProfileBinding.inflate(layoutInflater) }
    private lateinit var currentUser: UserModel
    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>
    private lateinit var openSettingsLauncher: ActivityResultLauncher<Intent>
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpActionbar()

        loadUserData()

        setupBttns()

        setupLaunchers()
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
            .into(binding.ivProfileUserImage)
        binding.etName.setText(currentUser.name)
        binding.etEmail.setText(currentUser.email)
        binding.etMobile.setText(currentUser.mobile.toString())
    }

    private fun setupBttns() {
        binding.ivProfileUserImage.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                showImageChooser()
            } else {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }

        //TODO: validate name and mobile then update using firebase services
        binding.btnUpdate.setOnClickListener {

        }
    }
    private fun showImageChooser() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        imagePickerLauncher.launch(intent)
    }
    private fun setupLaunchers(){
        imagePickerLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedImageUri: Uri? = result.data?.data
                try {
                    Glide
                        .with(this)
                        .load(selectedImageUri)
                        .centerCrop()
                        .placeholder(R.drawable.ic_profile)
                        .into(binding.ivProfileUserImage)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        openSettingsLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                showImageChooser()
            }
        }
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                showImageChooser()
            } else {
                AlertDialog.Builder(this)
                    .setMessage("You denied the permission for storage. Please go to the app settings and grant the permission.")
                    .setPositiveButton("Go to settings") { _, _ ->
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", packageName, null)
                        intent.data = uri
                        startActivity(intent)
                    }
                    .setNegativeButton("Cancel", null)
                    .create()
                    .show()
            }
        }

    }
}