package com.kerollosragaie.teamtrix.activities

import android.os.Bundle
import android.view.MenuItem
import com.kerollosragaie.teamtrix.databinding.ActivityProfileBinding

class ProfileActivity : BaseActivity() {
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpActionbar()
    }


    private fun setUpActionbar() {
        setSupportActionBar(binding.topAppBar.toolbar)
        supportActionBar?.title = "My profile"
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressedDispatcher.onBackPressed()
            else -> return true
        }
        return super.onOptionsItemSelected(item)
    }
}