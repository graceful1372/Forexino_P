package ir.hmb72.forexuser.data.network

import ir.hmb72.forexuser.data.model.network.home.ResponseNews
import ir.hmb72.forexuser.data.model.network.login.BodyRegister
import ir.hmb72.forexuser.data.model.network.login.ResponseRegister
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiServiceNews {

    @GET("ff_calendar_thisweek.json") //baseUrl -> "https://nfs.faireconomy.media/ff_calendar_thisweek.json"
    suspend fun getNews(): Response<ResponseNews>
//    suspend fun getNews(): Flow<Response<ResponseNews>>



}