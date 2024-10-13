package ir.hmb72.forexuser.data.repositiory

import ir.hmb72.forexuser.data.network.ApiServiceLocal
import javax.inject.Inject

class AnalyzeRepository @Inject constructor(private val api:ApiServiceLocal) {
    suspend fun getListSignal(action:String) = api.getListSignal(action)

}