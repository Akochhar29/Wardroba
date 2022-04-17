package com.example.wardroba.vms

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wardroba.models.Accessory
import com.example.wardroba.models.UserAuth
import com.example.wardroba.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject
@HiltViewModel
class UserViewModel @Inject constructor():ViewModel() {
    @Inject
    lateinit var db: FirebaseFirestore
    var auth: FirebaseAuth = Firebase.auth
        private set
    var loginSuccessful = MutableLiveData(false)
    fun addUser(userData: User, userAuth: UserAuth) {
        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(
                    userAuth.email.toString(),
                    userAuth.password.toString()
                ).await()
                db.collection("users").document(auth.uid.toString()).set(userData).await()
            } catch (e: Exception) {
                Log.d("ABC", "Error occurred: ${e.message}")
            }
        }
    }

    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    loginSuccessful.value = true
                    Log.d("ABC", "createUserWithEmail:success")
                } else {
                    loginSuccessful.value = false
                    // If sign in fails, display a message to the user.
                    Log.w("ABC", "createUserWithEmail:failure", task.exception)
                }
            }
    }
    fun storeAccessory(accessory:Accessory) {

        viewModelScope.launch {
            try {
                db.collection("accessories").document().set(accessory).await()
            } catch (e: Exception) {
                Log.d("ABC", "Error occurred: ${e.message}")
            }
        }
    }
    fun getAccessories():List<Accessory>{
        val accessories = mutableListOf<Accessory>()
        viewModelScope.launch {
         try{
             val data = db.collection("accessories").get().await()
             val converted = data.toObjects<Accessory>()
             converted.forEach { entry ->
                 if (auth.currentUser?.uid == entry.id){
                     accessories.add(entry)
                 }
             }
         }catch (e:Exception){
             Log.d("ABC", "Error occurred: ${e.message}")
         }
        }
        return accessories
    }
}