package ir.hmb72.forexuser.data.model.network.login


import com.google.gson.annotations.SerializedName

data class BodyLogin(
    @SerializedName("phone")
    var phone: String? = null // 09370000000
)