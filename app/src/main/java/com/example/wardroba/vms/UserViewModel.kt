package com.example.wardroba.vms

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.wardroba.models.UserAuth
import com.example.wardroba.models.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject
@HiltViewModel
class UserViewModel @Inject constructor():ViewModel(){
    @Inject
    lateinit var db: FirebaseFirestore
    @Inject
    lateinit var auth:FirebaseAuth
    fun addUser(userData:UserData,userAuth:UserAuth){
        viewModelScope.launch{
            try{
                auth.createUserWithEmailAndPassword(userAuth.email.toString(),userAuth.password.toString()).await()
                db.collection("users").add(UserData( userAuth.id,firstName = userData.firstName, lastName = userData.lastName)).await()

            }
            catch (e:Exception){
                Log.d("ABC","Error occurred: ${e.message}")
            }
        }
    }
}