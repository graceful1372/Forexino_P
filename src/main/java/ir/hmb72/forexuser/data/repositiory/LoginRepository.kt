package ir.hmb72.forexuser.data.repositiory


import ir.hmb72.forexuser.data.model.network.login.BodyLogin
import ir.hmb72.forexuser.data.model.network.login.BodyRegister
import ir.hmb72.forexuser.data.network.ApiServiceLocal
import javax.inject.Inject


class LoginRepository @Inject constructor(
    private val api: ApiServiceLocal,
) {


    //login and register
    suspend fun postRegister(body: BodyRegister) = api.postRegister(body)

    suspend fun login(body: BodyLogin) = api.login(body)


}