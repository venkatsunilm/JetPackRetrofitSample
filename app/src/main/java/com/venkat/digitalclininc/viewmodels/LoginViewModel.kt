package com.venkat.digitalclininc.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.venkat.digitalclinic.apiservice.api.RepositoryServiceManager
import com.venkat.digitalclinic.apiservice.api.repository.AppSettingsRepository
import com.venkat.digitalclinic.apiservice.helper.ResponseError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject internal constructor(
    private val repositoryServiceManager: RepositoryServiceManager,
    private val appSettingsRepository: AppSettingsRepository
) : ViewModel() {

    private val _username = MutableLiveData<String>("venkatsunilm@gmail.com")
    val userName: LiveData<String> = _username

    private val _password = MutableLiveData<String>("IDon'tKnow")
    val password: LiveData<String> = _password

    private val _spinner = MutableLiveData<Boolean>(false)
    val spinner: LiveData<Boolean> = _spinner

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> = _loginResult

    fun login(): LiveData<String> {
        val responseObject = MutableLiveData<String>()
        viewModelScope.launch {
            _spinner.value = true
            try {
                responseObject.value = repositoryServiceManager.login(
                    _username.value!!,
                    _password.value!!
                )

                _loginResult.value = !responseObject.value.isNullOrEmpty()

                responseObject.value?.let { token ->
                    onUserLoggedIn(token)
                }
            } catch (error: ResponseError) {
                // TODO: Update the UI with the error message
                // TODO: For now the end-points are down, sending mock token back
                responseObject.value = "token akjsdjsnjsnldjcnlsjncdjskjvbksjbvjdnfvkjdnf"
                _loginResult.value = !responseObject.value.isNullOrEmpty()

                onUserLoggedIn(responseObject.value!!)

            } finally {
                _spinner.value = false
            }
        }
        return responseObject
    }

    private fun onUserLoggedIn(token: String) {
        appSettingsRepository.saveToken(token)
    }
}