package com.empresadeapp.youlook.domain.auth

import com.google.firebase.auth.FirebaseUser

interface AuthRepo {
    suspend fun signIn(email: String, password: String): FirebaseUser?
    suspend fun signUp(email: String, password: String, nombre: String, apellido: String): FirebaseUser?
}