package ir.hmb72.forexuser.utils.network


import retrofit2.Response

open class NetworkResponse <T> (private val response:Response<T>){
    open fun generateResponse():NetworkRequest<T>{
        return when{
                response.message().contains("timeout") -> NetworkRequest.Error("Timeout")
                response.code() == 401 -> NetworkRequest.Error("ثبت نام نکرده اید")
                response.code() == 402 -> NetworkRequest.Error("Your free plan finished")
                response.code() == 402 -> NetworkRequest.Error("قبلا ثبت نام کرده اید")
                response.code() == 422 -> NetworkRequest.Error("Api key not found!")
                response.code() == 500 -> NetworkRequest.Error("Try again")
                response.isSuccessful -> NetworkRequest.Success(response.body()!!)
                else -> NetworkRequest.Error(response.message())
            }
        }
    }
