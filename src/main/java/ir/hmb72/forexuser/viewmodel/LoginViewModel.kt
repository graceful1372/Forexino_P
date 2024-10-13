package ir.hmb72.forexuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hmb72.forexuser.data.model.network.login.BodyLogin
import ir.hmb72.forexuser.data.model.network.login.BodyRegister
import ir.hmb72.forexuser.data.model.network.login.ResponseRegister
import ir.hmb72.forexuser.data.repositiory.LoginRepository
import ir.hmb72.forexuser.utils.network.NetworkRequest
import ir.hmb72.forexuser.utils.network.NetworkResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val rep:LoginRepository):ViewModel() {
    private var _loginData =MutableLiveData<NetworkRequest<ResponseRegister>>()
    val loginData : LiveData<NetworkRequest<ResponseRegister>> = _loginData

    fun register(body:BodyRegister) = viewModelScope.launch {
        _loginData.value = NetworkRequest.Loading()
        val response = rep.postRegister(body)
        _loginData.value = NetworkResponse(response).generateResponse()

    }

    fun login(body:BodyLogin) = viewModelScope.launch {
        _loginData.value = NetworkRequest.Loading()
        val response = rep.login(body)
        _loginData.value = NetworkResponse(response).generateResponse()

    }
//    fun saveUserData(name:String)=viewModelScope.launch {
//        rep.saveRegisterData(name)
//    }
//    val readData = rep.readRegisterData
//    val readWelcomeData = rep.readWelcome
}