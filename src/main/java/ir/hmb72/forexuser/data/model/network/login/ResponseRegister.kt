package ir.hmb72.forexuser.data.model.network.login


import com.google.gson.annotations.SerializedName

data class ResponseRegister(
    @SerializedName("name")
    val name: String?, // hamed
    @SerializedName("response")
    val response: String?, // Success
    @SerializedName("user_id")
    val userId: String? // 2
)