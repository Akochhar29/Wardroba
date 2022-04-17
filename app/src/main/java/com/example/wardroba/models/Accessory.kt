package com.example.wardroba.models

import com.google.firebase.firestore.DocumentId

data class Accessory(@DocumentId var id:String? = null, val colour:Int, val type:String, val seasons:List<String>, val foreignKey:String) {
}