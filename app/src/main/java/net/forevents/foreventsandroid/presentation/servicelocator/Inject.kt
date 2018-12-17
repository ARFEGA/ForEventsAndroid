package net.forevents.foreventsandroid.presentation.servicelocator


import net.forevents.foreventsandroid.Data.CreateUser.RandomUser.OutUserEntityMapper
import net.forevents.foreventsandroid.Data.Net.UserService
import net.forevents.foreventsandroid.Data.Repository.Repository
import net.forevents.foreventsandroid.Util.SettingsManager
import net.forevents.foreventsandroid.BuildConfig
import net.forevents.foreventsandroid.Data.CreateUser.User.OutAppUserByIdMapper
import net.forevents.foreventsandroid.Data.Model.City.OutAppCityMapper
import net.forevents.foreventsandroid.Data.Model.CreateUser.OutAppCreateUserMapper
import net.forevents.foreventsandroid.Data.Model.EventType.OutAppEventTypeMapper
import net.forevents.foreventsandroid.Data.Model.Events.OutAppEventsMapper
import net.forevents.foreventsandroid.Data.Model.LoginUser.OutAppUserMapper
import net.forevents.foreventsandroid.Data.Model.Transactions.OutAppTransactionsMapper
import net.forevents.foreventsandroid.Data.Repository.DataSource.ApiDataSource
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object Inject {

    lateinit var settingsManager : SettingsManager

    //Interceptor
    fun provideOkHttpClient(): OkHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()

    //Conexión api
    val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.URL_API)
        .client(provideOkHttpClient())
        //.baseUrl("https://randomuser.me/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
    val userService = retrofit.create(UserService::class.java)
    //Fin conexión api
    val outUserEntityMapper = OutUserEntityMapper()
    val outAppUserMapper = OutAppUserMapper()
    val outAppCreateUserMapper = OutAppCreateUserMapper()
    val outAppEventsMapper = OutAppEventsMapper()
    val outAppCityMapper = OutAppCityMapper()
    val outAppEventTypeMapper = OutAppEventTypeMapper()
    val outAppUserByIdMapper = OutAppUserByIdMapper()
    val outAppTransactionsMapper = OutAppTransactionsMapper()

    private val apiDataSource = ApiDataSource(
        userService,
        outUserEntityMapper,
        outAppUserMapper,
        outAppCreateUserMapper,
        outAppEventsMapper,
        outAppCityMapper,
        outAppEventTypeMapper,
        outAppUserByIdMapper,
        outAppTransactionsMapper
    )
    val repository = Repository(apiDataSource)


}