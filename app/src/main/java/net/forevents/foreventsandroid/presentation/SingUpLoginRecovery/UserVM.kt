package net.forevents.foreventsandroid.presentation.SingUpLoginRecovery


import androidx.lifecycle.MutableLiveData

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers



import net.forevents.foreventsandroid.Data.Model.City.AppCity
import net.forevents.foreventsandroid.Data.Model.CreateUser.AppCreateUser
import net.forevents.foreventsandroid.Data.Model.LoginUser.AppUser

import net.forevents.foreventsandroid.Data.Model.Response.OnlyResponse
import net.forevents.foreventsandroid.Data.Model.UserById.AppUserById
import net.forevents.foreventsandroid.Util.closeApp
import net.forevents.foreventsandroid.presentation.servicelocator.Inject
import net.forevents.foreventsandroid.Util.mvvm.BaseViewModel
import retrofit2.Response
import retrofit2.http.Body
import java.util.concurrent.TimeUnit


class UserVM: BaseViewModel() {
    val userState : MutableLiveData<AppUser> = MutableLiveData()
    val updateUserState : MutableLiveData<OnlyResponse.ResultUpdateProfile> = MutableLiveData()
    val createUserState : MutableLiveData<AppCreateUser> = MutableLiveData()
    val userByIdState: MutableLiveData<AppUserById> = MutableLiveData()
    val recoveryPasswordState : MutableLiveData<OnlyResponse.ResultRecoveryPassword> = MutableLiveData()
    val citiesListState : MutableLiveData<List<AppCity>> = MutableLiveData()

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

    fun loadCitiestList(city:String,limit:String){
        Inject.repository.getCitiesList(city,limit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

            //Para hacer delay con las pulsaciones del user
            .debounce(300, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()

            .subscribeBy(
                onNext = {
                    citiesListState.value = it
                },
                onError = {
                    println("########ERROR IN LOAD EVENTS  ########### ${it.message}")
                },
                onComplete = {
                    //Inject.settingsManager.firstLoad = false
                }

            ).addTo(compositeDisposable)
    }
    fun getUserById(userId:String,token:String){
        Inject.repository.getUserById(userId,token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    userByIdState.value = it
                },
                onError = {
                    println("########ERROR IN LOAD DAT USER BY ID  ########### ${it.message}")
                }
            ).addTo(compositeDisposable)
    }

    fun updateProfile(userId:String,
                      token:String,
                      email: String,
                      first_name:String,
                      last_name:String,
                      address: String,
                      city: String,
                      zip_code:String,
                      province:String,
                      country:String,
                      alias:String){
        Inject.repository.updateProfile(userId, token, email, first_name, last_name, address, city, zip_code, province, country, alias)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    updateUserState.value = it

                },
                onError = {
                    println("########ERROR IN UPDATE PROFILE  ########### ${it.message}")
                }

            ).addTo(compositeDisposable)
    }


}