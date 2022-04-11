package com.example.wardroba.models
import com.google.firebase.firestore.DocumentId
data class User(val firstName:String?=null, val lastName:String?=null)
data class UserAuth(@DocumentId var id:String?=null,val email: String?=null,val password: String?=null)