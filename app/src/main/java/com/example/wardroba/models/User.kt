package com.example.wardroba.models
import com.google.firebase.firestore.DocumentId
data class UserData(@DocumentId var id:String?=null, val firstName:String?=null,val lastName:String?=null)
data class UserAuth(@DocumentId var id:String?=null,val email: String?=null,val password: String?=null)