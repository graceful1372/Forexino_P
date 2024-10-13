package ir.hmb72.forexuser.data.model.network.home


import com.google.gson.annotations.SerializedName

class ResponseCoinGecko : ArrayList<ResponseCoinGecko.ResponseCoinGeckoItem>(){
    data class ResponseCoinGeckoItem(
        @SerializedName("ath")
        val ath: Double?, // 4878.26
        @SerializedName("ath_change_percentage")
        val athChangePercentage: Double?, // -15.0259
        @SerializedName("ath_date")
        val athDate: String?, // 2024-03-14T07:10:36.635Z
        @SerializedName("atl")
        val atl: Double?, // 67.81
        @SerializedName("atl_change_percentage")
        val atlChangePercentage: Double?, // 92303.8767
        @SerializedName("atl_date")
        val atlDate: String?, // 2013-07-06T00:00:00.000Z
        @SerializedName("circulating_supply")
        val circulatingSupply: Double?, // 19719062.0
        @SerializedName("current_price")
        val currentPrice: Double?, // 3476.22
        @SerializedName("fully_diluted_valuation")
        val fullyDilutedValuation: Long?, // 1318863081239
        @SerializedName("high_24h")
        val high24h: Double?, // 3514.17
        @SerializedName("id")
        val id: String?, // bitcoin
        @SerializedName("image")
        val image: String?, // https://coin-images.coingecko.com/coins/images/1/large/bitcoin.png?1696501400
        @SerializedName("last_updated")
        val lastUpdated: String?, // 2024-07-01T13:33:04.463Z
        @SerializedName("low_24h")
        val low24h: Double?, // 3385.5
        @SerializedName("market_cap")
        val marketCap: Long?, // 1238416327069
        @SerializedName("market_cap_change_24h")
        val marketCapChange24h: Double?, // -145226397.78910828
        @SerializedName("market_cap_change_percentage_24h")
        val marketCapChangePercentage24h: Double?, // 2.30381
        @SerializedName("market_cap_rank")
        val marketCapRank: Int?, // 1
        @SerializedName("max_supply")
        val maxSupply: Double?, // 21000000.0
        @SerializedName("name")
        val name: String?, // Bitcoin
        @SerializedName("price_change_24h")
        val priceChange24h: Double?, // 1412.88
        @SerializedName("price_change_percentage_24h")
        val priceChangePercentage24h: Double?, // 2.2963
        @SerializedName("roi")
        val roi: Roi?,
        @SerializedName("symbol")
        val symbol: String?, // btc
        @SerializedName("total_supply")
        val totalSupply: Double?, // 21000000.0
        @SerializedName("total_volume")
        val totalVolume: Double? // 1908.07
    ) {
        data class Roi(
            @SerializedName("currency")
            val currency: String?, // btc
            @SerializedName("percentage")
            val percentage: Double?, // 7289.995914273896
            @SerializedName("times")
            val times: Double? // 72.89995914273896
        )
    }
}