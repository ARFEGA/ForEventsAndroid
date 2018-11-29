package net.forevents.foreventsandroid.presentation.SingUpLoginRecovery


import androidx.lifecycle.MutableLiveData

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import net.forevents.foreventsandroid.Data.CreateUser.User.AppCreateUser
import net.forevents.foreventsandroid.Data.CreateUser.User.AppUser
import net.forevents.foreventsandroid.Data.Model.Response.OnlyResponse
import net.forevents.foreventsandroid.presentation.servicelocator.Inject
import net.forevents.foreventsandroid.Util.mvvm.BaseViewModel


class UserVM: BaseViewModel() {
    val userState : MutableLiveData<AppUser> = MutableLiveData()
    val createUserState : MutableLiveData<AppCreateUser> = MutableLiveData()
    val recoveryPasswordState : MutableLiveData<OnlyResponse.ResultRecoveryPassword> = MutableLiveData()

    fun loginUser(email: String,
                  password: String){
        Inject.repository.getUserToken(email,password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
           .subscribeBy (
               onSuccess = {
                   println(it.token)
                   userState.value = it
               },
               onError = {
                    println("########ERROR on LOAD USER########### ${it.message}")
               }
               /* onNext = {
                    userState.value = it
                },
                onError = {

                },
                onComplete = {

                }*/
            ).addTo(compositeDisposable)
    }

    fun createUser(email: String,
                   password: String,
                   first_name:String,
                   last_name:String,
                   address: String,
                   city: String,
                   zip_code:String,
                   province:String,
                   country:String,
                   alias:String){
        Inject.repository.createUser(email, password, first_name, last_name, address, city, zip_code, province, country, alias)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = {
                    createUserState.value=it
                },
                onError = {
                    println("######## ERROR on CREATE ########### ${it.message}")
                }
            ).addTo(compositeDisposable)
    }

    fun recoveryPassword(email:String){
        Inject.repository.recoveryPassword(email)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = {
                    recoveryPasswordState.value = it
                },
                onError = {
                    println("######## ERROR on RECOVERY ########### ${it.message}")
                }
            ).addTo(compositeDisposable)
    }
}