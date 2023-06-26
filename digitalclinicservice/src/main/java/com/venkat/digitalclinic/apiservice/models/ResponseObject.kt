package com.venkat.digitalclinic.apiservice.models

// builder pattern
data class ResponseObject<T>(val data: T? = null, val apiException: String? = null, val statusCode: Int = 0)