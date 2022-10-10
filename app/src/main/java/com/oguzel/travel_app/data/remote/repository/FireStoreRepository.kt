package com.oguzel.travel_app.data.remote.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.oguzel.travel_app.data.local.entity.User

class FireStoreRepository {

    private var firestoreDB = FirebaseFirestore.getInstance()

    fun sendUserInformationToFirestore(user: User) {
        firestoreDB.collection("Users").document(user.id).set(user, SetOptions.merge())
            .addOnSuccessListener {
                println("successful")
            }
            .addOnFailureListener {
                println("not successful")
                println(it)
            }
    }
}