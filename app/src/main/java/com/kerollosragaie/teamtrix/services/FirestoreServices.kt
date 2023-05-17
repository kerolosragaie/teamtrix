package com.kerollosragaie.teamtrix.services


import android.app.Activity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.kerollosragaie.teamtrix.activities.MainActivity
import com.kerollosragaie.teamtrix.activities.SignInActivity
import com.kerollosragaie.teamtrix.activities.SignUpActivity
import com.kerollosragaie.teamtrix.models.UserModel
import com.kerollosragaie.teamtrix.utils.Constants

class FirestoreServices {
    private val mFirestore = FirebaseFirestore.getInstance()

    fun signinUser(activity:Activity){
        mFirestore.collection(Constants.USERS).document(getCurrentUserId())
            .get()
            .addOnSuccessListener {
                document ->
                val loggedInUser = document.toObject(UserModel::class.java)!!
                when(activity){
                    is SignInActivity -> {
                        activity.signInSuccess(loggedInUser)
                    }
                    is MainActivity -> {
                        activity.updateNavigationUserDetails(loggedInUser)
                    }
                }
            }.addOnFailureListener {
                    e->

                when(activity){
                    is SignInActivity -> {
                        activity.mProgressDialog.dismissDialog()
                    }
                    is MainActivity -> {
                        activity.mProgressDialog.dismissDialog()
                    }
                }
                Log.e(activity.javaClass.simpleName,"Error: ${e.message}")
            }
    }

    fun registerUser(activity:SignUpActivity,userModel: UserModel){
        mFirestore.collection(Constants.USERS).document(getCurrentUserId())
            .set(userModel, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegisteredSuccess()
            }.addOnFailureListener {
                e->
                Log.e(activity.javaClass.simpleName,"Error: ${e.message}")
            }
    }

    fun getCurrentUserId():String{
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserId = ""
        if(currentUser!=null){
            currentUserId = currentUser.uid
        }
        return currentUserId
    }

    fun signOut(){
        FirebaseAuth.getInstance().signOut()
    }

    fun getCurrentUserData():UserModel?{
        var loggedInUserData :UserModel?=null
        mFirestore.collection(Constants.USERS).document(getCurrentUserId())
            .get()
            .addOnSuccessListener {
                    document ->
                loggedInUserData = document.toObject(UserModel::class.java)!!

            }.addOnFailureListener {
                    e->
                Log.e(Constants.GET_USER_ERROR,"Error: ${e.message}")
            }
        return loggedInUserData
    }
}