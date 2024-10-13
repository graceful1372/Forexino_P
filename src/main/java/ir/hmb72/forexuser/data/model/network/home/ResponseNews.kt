package ir.hmb72.forexuser.data.model.network.home


import com.google.gson.annotations.SerializedName

class ResponseNews : ArrayList<ResponseNews.ResponseNewsItem>() {
    data class ResponseNewsItem(
        @SerializedName("country")
        val country: String? , // JPY
        @SerializedName("date")
        val date: String? , // 2024-05-26T20:05:00-04:00
        @SerializedName("forecast")
        val forecast: String? ,
        @SerializedName("impact")
        val impact: String?, // High
        @SerializedName("previous")
        val previous: String? ,
        @SerializedName("title")
        val title: String? , // BOJ Gov Ueda Speaks
        @SerializedName("url")
        val url: String? , // https://www.forexfactory.com/calendar/892-jpy-boj-gov-ueda-speaks
    )
}