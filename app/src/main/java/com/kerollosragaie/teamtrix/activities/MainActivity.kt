package com.kerollosragaie.teamtrix.activities


import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.kerollosragaie.teamtrix.R
import com.kerollosragaie.teamtrix.databinding.ActivityMainBinding
import com.kerollosragaie.teamtrix.models.UserModel
import com.kerollosragaie.teamtrix.services.FirestoreServices
import id.ionbit.ionalert.IonAlert


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener{
    private lateinit var binding: ActivityMainBinding
    private lateinit var currentUserData:UserModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpActionbar()

        binding.navView.setNavigationItemSelectedListener(this)

        mProgressDialog.showDialog()
        FirestoreServices().signinUser(this@MainActivity)
    }
    fun updateNavigationUserDetails(user:UserModel){
        currentUserData = user
        val civProfile = findViewById<ImageView>(R.id.civ_profile)
        val nameProfile = findViewById<TextView>(R.id.tv_username)
        nameProfile.text = user.name
        Glide
            .with(this)
            .load(user.image)
            .centerCrop()
            .placeholder(R.drawable.ic_profile)
            .into(civProfile)
        mProgressDialog.dismissDialog()
    }



    /**
     * Setup top action bar
     * */
    private fun setUpActionbar() {
        setSupportActionBar(binding.topAppBar.toolbarMainActivity)
        binding.topAppBar.toolbarMainActivity.setNavigationIcon(R.drawable.ic_action_nav_menu)
        binding.topAppBar.toolbarMainActivity.setNavigationOnClickListener {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            doubleBackToExit()
        }
    }

    /**
     * To set on click listener for every item in the nav menu
     * */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_my_profile -> {
                val intent = Intent(this@MainActivity,ProfileActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_sign_out -> {
                IonAlert(this@MainActivity, IonAlert.WARNING_TYPE)
                    .setTitleText("Warning".uppercase())
                    .setContentText("Are you sure you want to logout?")
                    .setConfirmText("YES")
                    .setCancelText("NO")
                    .setConfirmClickListener {
                        it.dismiss()
                        FirestoreServices().signOut()
                        val intent = Intent(this@MainActivity, IntroActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }.show()

            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

}