package com.venkat.digitalclinic.apiservice.api.repository

import com.google.gson.JsonObject
import retrofit2.http.Body
import retrofit2.http.POST

interface IPatientService {
    @POST("v1/login")
    suspend fun login(@Body body: HashMap<String, String>): JsonObject
}