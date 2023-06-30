package com.venkat.digitalclinic.apiservice.data.repository.contracts

interface IAppSettingsRepository {
    fun saveToken(token: String)
    fun getToken(): String?
    fun clearToken()
}