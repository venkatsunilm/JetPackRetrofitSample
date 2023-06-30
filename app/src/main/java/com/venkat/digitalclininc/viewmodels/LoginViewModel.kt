package com.venkat.digitalclininc.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.venkat.digitalclinic.apiservice.data.RepositoryServiceManager
import com.venkat.digitalclinic.apiservice.data.repository.AppSettingsRepository
import com.venkat.digitalclinic.apiservice.utils.ResponseError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    fun loginMainDispatcher(): LiveData<String> {
        val responseObject = MutableLiveData<String>()

        println("Main thread: ${Thread.currentThread().name}")
        Log.d("Login venkat", "Main thread: ${Thread.currentThread().name}")

        viewModelScope.launch() {

            println("Coroutine context: $coroutineContext")
            println("Coroutine thread: ${Thread.currentThread().name}")

            Log.d("Login venkat", "Coroutine context: $coroutineContext")
            Log.d("Login venkat", "Coroutine thread: ${Thread.currentThread().name}")

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

    fun login(): LiveData<String> {
        val responseObject = MutableLiveData<String>()

        println("Main thread: ${Thread.currentThread().name}")
        Log.d("Login venkat", "Main thread: ${Thread.currentThread().name}")

        viewModelScope.launch(Dispatchers.IO) {

            println("Coroutine context: $coroutineContext")
            println("Coroutine thread: ${Thread.currentThread().name}")

            Log.d("Login venkat", "Coroutine context: $coroutineContext")
            Log.d("Login venkat", "Coroutine thread: ${Thread.currentThread().name}")

            _spinner.postValue(true)
            try {
                responseObject.postValue(
                    repositoryServiceManager.login(
                        _username.value!!,
                        _password.value!!
                    )
                )

                _loginResult.postValue(!responseObject.value.isNullOrEmpty())

                withContext(Dispatchers.Main) {
                    responseObject.value?.let { token ->
                        onUserLoggedIn(token)
                    }
                }

            } catch (error: ResponseError) {
                // TODO: Update the UI with the error message
                // TODO: For now the end-points are down, sending mock token back
                withContext(Dispatchers.Main) {
                    responseObject.value = "token akjsdjsnjsnldjcnlsjncdjskjvbksjbvjdnfvkjdnf"
                    _loginResult.value = !responseObject.value.isNullOrEmpty()

                    onUserLoggedIn(responseObject.value!!)
                }
            } finally {
                _spinner.postValue(false)
            }
        }
        return responseObject
    }

    private fun onUserLoggedIn(token: String) {
        appSettingsRepository.saveToken(token)
    }
}