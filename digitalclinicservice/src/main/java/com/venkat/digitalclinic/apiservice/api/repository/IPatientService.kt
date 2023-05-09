/*
 * Copyright (c) 2021 F-secure Corporation.
 */

package com.venkat.digitalclinic.apiservice.api.repository

import com.google.gson.JsonObject
import retrofit2.http.Body
import retrofit2.http.POST

internal interface IPatientService {
    @POST("v1/login")
    suspend fun login(@Body body: HashMap<String, String>): JsonObject
}