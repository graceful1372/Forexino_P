package ir.hmb72.forexuser.data.model.network.login


import com.google.gson.annotations.SerializedName

data class BodyRegister(
    @SerializedName("family")
    var family: String? = null, // mb
    @SerializedName("name")
    var name: String?= null, // hamed
    @SerializedName("phone")
    var phone: String?= null // 09370000000
)