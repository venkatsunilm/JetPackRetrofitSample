package com.venkat.digitalclinic.apiservice.api.contracts

interface IAppSettingsRepository {
    fun saveToken(token: String)
    fun getToken(): String?
    fun clearToken()
}