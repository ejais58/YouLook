package com.empresadeapp.youlook.data.remote.auth

import com.empresadeapp.youlook.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthDataSource {
    suspend fun signIn(email: String, password: String): FirebaseUser?{
        val authResult = FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).await()
        return authResult.user
    }

    suspend fun signUp(email: String, password: String, nombre: String, apellido: String): FirebaseUser? {
        val authResult = FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await()
        authResult.user?.uid?.let { uid ->
            FirebaseFirestore.getInstance().collection("Users").document(uid).set(User(email,nombre,apellido)).await()
        }
        authResult.user?.sendEmailVerification()
        return authResult.user

    }
}