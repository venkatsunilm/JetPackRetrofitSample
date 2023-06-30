package com.venkat.digitalclinic.apiservice.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.venkat.digitalclinic.apiservice.data.repository.contracts.IAppSettingsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppSettingsRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : IAppSettingsRepository {

    companion object {
        private const val SHARED_PREFS_NAME = "auth_digital_clinic"
        private const val KEY_TOKEN = "token"
        private const val KEY_DATA_ID = "data_id"
    }

    private val sharedPreferences: SharedPreferences by lazy {
        val masterKeyAlias = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        EncryptedSharedPreferences.create(
            context,
            SHARED_PREFS_NAME,
            masterKeyAlias,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override fun saveToken(token: String) {
        sharedPreferences.edit().putString(KEY_TOKEN, token).apply()
    }

    override fun getToken(): String? {
        return sharedPreferences.getString(KEY_TOKEN, null)
    }

    override fun clearToken() {
        sharedPreferences.edit().clear().apply()
    }
}
