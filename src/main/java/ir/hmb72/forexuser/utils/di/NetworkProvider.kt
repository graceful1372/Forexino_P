package ir.hmb72.forexuser.utils.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.hmb72.forexuser.data.network.ApiServiceLocal
import ir.hmb72.forexuser.data.network.ApiServiceNews
import ir.hmb72.forexuser.data.network.ApiServicesCoinGecko
import ir.hmb72.forexuser.utils.BASE_URL_COIN
import ir.hmb72.forexuser.utils.BASE_URL_LOCAL
import ir.hmb72.forexuser.utils.BASE_URL_NEWS
import ir.hmb72.forexuser.utils.CONNECTION_TIME
import ir.hmb72.forexuser.utils.URL_COIN
import ir.hmb72.forexuser.utils.URL_LOCAL
import ir.hmb72.forexuser.utils.URL_NEWS
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkProvider {
    @Provides
    @Singleton
    @Named(URL_LOCAL)
    fun providerBaseUrlLocal() = BASE_URL_LOCAL

    @Provides
    @Singleton
    @Named(URL_NEWS)
    fun providerBaseUrlNews() = BASE_URL_NEWS
    @Provides
    @Singleton
    @Named(URL_COIN)
    fun providerBaseUrlCoins() = BASE_URL_COIN

    @Provides
    @Singleton
    fun provideNetworkTime() = CONNECTION_TIME

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setLenient().create()


    @Provides
    @Singleton
    fun provideInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideClient(
        time: Long, interceptor: HttpLoggingInterceptor,
    ) = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .writeTimeout(time, TimeUnit.SECONDS)
        .readTimeout(time, TimeUnit.SECONDS)
        .connectTimeout(time, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideRetrofitLocal(@Named(URL_LOCAL) baseUrl: String, gson: Gson, client: OkHttpClient): ApiServiceLocal =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(ApiServiceLocal::class.java)

    @Provides
    @Singleton
    fun provideRetrofitNews(@Named(URL_NEWS) baseUrl: String, gson: Gson, client: OkHttpClient): ApiServiceNews =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(ApiServiceNews::class.java)

    @Provides
    @Singleton
    fun provideRetrofitCoins(@Named(URL_COIN) baseUrl: String, gson: Gson, client: OkHttpClient): ApiServicesCoinGecko =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(ApiServicesCoinGecko::class.java)


}