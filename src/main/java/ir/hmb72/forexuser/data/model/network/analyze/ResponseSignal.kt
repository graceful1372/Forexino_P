package ir.hmb72.forexuser.data.model.network.analyze


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


data class ResponseSignal(
    @SerializedName("listSignal")
    val listSignal: List<Signal>?
) {
    @Parcelize
    data class Signal(
        @SerializedName("full_name")
        val fullName: String?, // Australian Dollar / Canadian Dollar
        @SerializedName("image_path")
        val imagePath: String?, // http://192.168.130.2/forexino/img_signal/66810f1847b2a.jpg
        @SerializedName("price")
        val price: String?, // 2
        @SerializedName("profit")
        val profit: String?,
        @SerializedName("spot_trade")
        val spotTrade: String?, // 2
        @SerializedName("stop_loss")
        val stopLoss: String?, // 2
        @SerializedName("symbol")
        val symbol: String?, // AUDCAD
        @SerializedName("symbol_image_path")
        val symbolImagePath: String?, // http://192.168.130.2/forexino/img_symbol/img_audcad.png
        @SerializedName("tp1")
        val tp1: String?, // 2
        @SerializedName("tp2")
        val tp2: String? // 2
    ):Parcelable
}