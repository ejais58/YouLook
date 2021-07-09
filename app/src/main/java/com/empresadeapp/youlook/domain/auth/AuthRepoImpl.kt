package com.empresadeapp.youlook.domain.auth

import com.empresadeapp.youlook.data.remote.auth.AuthDataSource
import com.google.firebase.auth.FirebaseUser

class AuthRepoImpl(private val dataSource: AuthDataSource): AuthRepo {
    override suspend fun signIn(email: String, password: String): FirebaseUser? = dataSource.signIn(email, password)
    override suspend fun signUp(email: String, password: String, nombre: String, apellido: String): FirebaseUser? = dataSource.signUp(email,password,nombre,apellido)
}