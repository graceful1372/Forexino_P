package ir.hmb72.forexuser.data.network

import ir.hmb72.forexuser.data.model.network.analyze.ResponseSignal
import ir.hmb72.forexuser.data.model.network.home.ResponseNews
import ir.hmb72.forexuser.data.model.network.login.BodyLogin
import ir.hmb72.forexuser.data.model.network.login.BodyRegister
import ir.hmb72.forexuser.data.model.network.login.ResponseRegister
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiServiceLocal {



    @POST("user/register.php") //baseUrl ->"http://192.168.130.2/forexino/register.php"
    suspend fun postRegister( @Body body: BodyRegister): Response<ResponseRegister>

    @POST("user/login.php") //baseUrl ->"http://192.168.130.2/forexino/register.php"
    suspend fun login( @Body body: BodyLogin): Response<ResponseRegister>

    @GET("user/get_list_signal.php")
    suspend fun getListSignal(@Query ("action") action: String): Response<ResponseSignal>

}