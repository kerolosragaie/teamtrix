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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.kerollosragaie.teamtrix.R
import com.kerollosragaie.teamtrix.core.Constants.USER_DATA
import com.kerollosragaie.teamtrix.core.functions.Utils
import com.kerollosragaie.teamtrix.databinding.ActivityProfileBinding
import com.kerollosragaie.teamtrix.models.UserModel

class ProfileActivity : BaseActivity() {
    private val binding by lazy { ActivityProfileBinding.inflate(layoutInflater) }
    private lateinit var currentUser: UserModel
    private val imagePickerLauncher by lazy {
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedImageUri: Uri? = result.data?.data
                Glide
                    .with(this)
                    .load(selectedImageUri)
                    .centerCrop()
                    .placeholder(R.drawable.ic_profile)
                    .into(binding.ivProfileUserImage)
            }
        }
    }
    private val openSettingsLauncher by lazy {
        registerForActivityResult(
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
    }

    companion object {
        private const val READ_STORAGE_PERMISSION_CODE = 1
        private const val PICK_IMAGE_REQUEST_CODE = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    READ_STORAGE_PERMISSION_CODE
                )
            }
        }

        //TODO: validate name and mobile then update using firebase services
        binding.btnUpdate.setOnClickListener {

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == READ_STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showImageChooser()
            } else {
                AlertDialog
                    .Builder(this)
                    .setMessage("You denied the permission for storage. Please go to the app settings and grant the permission.")
                    .setPositiveButton("Go to settings") { _, _ ->
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", packageName, null)
                        intent.data = uri
                        openSettingsLauncher.launch(intent)
                    }
                    .setNegativeButton("Cancel", null)
                    .create()
                    .show()
            }
        }
    }

    private fun showImageChooser() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        imagePickerLauncher.launch(intent)
    }
}