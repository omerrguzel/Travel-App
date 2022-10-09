package com.oguzel.travel_app.presentation.register

import androidx.lifecycle.ViewModel
import com.oguzel.travel_app.data.local.entity.User
import com.oguzel.travel_app.data.remote.repository.FireStoreRepository

class RegisterViewModel : ViewModel() {

    var firebaseRepository = FireStoreRepository()

    fun saveUserToFirebase(user: User) {
        firebaseRepository.sendUserInformationToFirestore(user)
    }
}