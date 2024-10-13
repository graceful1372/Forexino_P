package ir.hmb72.forexuser.data.network

import ir.hmb72.forexuser.data.model.network.home.ResponseCoinGecko
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServicesCoinGecko {
    @GET("coins/markets")
    suspend fun getCoinList(@Query ("vs_currency") vsCurrency:String):Response<ResponseCoinGecko>
}