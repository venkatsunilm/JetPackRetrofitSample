package com.venkat.digitalclinic.apiservice.data.repository

import com.venkat.digitalclinic.apiservice.data.remote.IPatientService
import com.venkat.digitalclinic.apiservice.utils.ResponseError
import javax.inject.Inject

class PatientRepository @Inject constructor(
    private val userService: IPatientService
) {
    suspend fun login(
        username: String,
        password: String
    ): String {
        try {
            val userCredentials = hashMapOf(USERNAME_KEY to username, PASSWORD_KEY to password)
            val jsonResponsePbj = userService.login(userCredentials)
            return jsonResponsePbj.get(TOKEN_KEY).asString
        } catch (error: Throwable) {
            throw ResponseError("login error", error)
        }
    }

    companion object {
        const val USERNAME_KEY = "username"
        const val PASSWORD_KEY = "password"
        const val TOKEN_KEY = "token"
    }

}