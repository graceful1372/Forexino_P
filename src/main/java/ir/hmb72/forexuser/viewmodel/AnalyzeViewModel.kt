package ir.hmb72.forexuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.hmb72.forexuser.data.model.network.analyze.ResponseSignal
import ir.hmb72.forexuser.data.repositiory.AnalyzeRepository
import ir.hmb72.forexuser.utils.network.NetworkRequest
import ir.hmb72.forexuser.utils.network.NetworkResponse
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AnalyzeViewModel @Inject constructor(private val repository: AnalyzeRepository):ViewModel() {

    private var _observerAnalyze = MutableLiveData<NetworkRequest<ResponseSignal>> ()
    val observerAnalyze :LiveData<NetworkRequest<ResponseSignal>> = _observerAnalyze

    fun getSignal(action:String)=viewModelScope.launch {
        _observerAnalyze.value = NetworkRequest.Loading()
         val response = repository.getListSignal(action)
        _observerAnalyze.value = NetworkResponse(response).generateResponse()

    }
}