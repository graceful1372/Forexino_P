package ir.hmb72.forexuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hmb72.forexuser.data.model.network.home.ResponseCoinGecko
import ir.hmb72.forexuser.data.model.network.home.ResponseNews
import ir.hmb72.forexuser.data.repositiory.HomeRepository
import ir.hmb72.forexuser.utils.network.NetworkRequest
import ir.hmb72.forexuser.utils.network.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    private var _newsList = MutableLiveData<NetworkRequest<ResponseNews>>()
    val newsList: LiveData<NetworkRequest<ResponseNews>> = _newsList

    init {
        getNews()

    }

    private fun getNews() = viewModelScope.launch(Dispatchers.IO) {
        //Why I get Error this here ?!
//        _newsList.value = NetworkRequest.Loading()
//        val response = repository.getNews()
//        _newsList.value = NetworkResponse(response).generateResponse()

         _newsList.postValue(NetworkRequest.Loading())
        val response = repository.getNews()
        _newsList.postValue(NetworkResponse(response).generateResponse())


    }

    //Coin
    private var _observerCoin = MutableLiveData<NetworkRequest<ResponseCoinGecko>>()
    val observerCoin: MutableLiveData<NetworkRequest<ResponseCoinGecko>> = _observerCoin

    fun getListCoin(action:String) = viewModelScope.launch(Dispatchers.IO) {

        _observerCoin.postValue(NetworkRequest.Loading())
        val response = repository.getListCoin(action)
        _observerCoin.postValue(NetworkResponse(response).generateResponse())

    }

}