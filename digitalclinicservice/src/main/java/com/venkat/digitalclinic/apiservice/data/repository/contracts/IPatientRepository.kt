package com.venkat.digitalclinic.apiservice.data.repository.contracts

import com.venkat.digitalclinic.apiservice.models.ResponseObject

interface IPatientRepository {
    suspend fun login(username: String, password: String): String
}