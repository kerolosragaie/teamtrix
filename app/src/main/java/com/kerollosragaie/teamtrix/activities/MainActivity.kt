package com.kerollosragaie.teamtrix.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.kerollosragaie.teamtrix.R
import com.kerollosragaie.teamtrix.databinding.ActivityMainBinding
import com.kerollosragaie.teamtrix.models.UserModel
import com.kerollosragaie.teamtrix.services.FirestoreServices

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}