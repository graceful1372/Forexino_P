package ir.hmb72.forexuser.data.repositiory

import ir.hmb72.forexuser.data.network.ApiServiceNews
import ir.hmb72.forexuser.data.network.ApiServicesCoinGecko
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val api:ApiServiceNews ,
    private  val apiCoin:ApiServicesCoinGecko ) {

    suspend fun getNews() = api.getNews()
    suspend fun getListCoin(action: String) = apiCoin.getCoinList(action)
}