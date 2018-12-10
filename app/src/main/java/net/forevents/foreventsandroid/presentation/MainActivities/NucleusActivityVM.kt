package net.forevents.foreventsandroid.presentation.MainActivities

import net.forevents.foreventsandroid.Util.mvvm.BaseViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import net.forevents.foreventsandroid.Data.CreateUser.User.AppEvents
import net.forevents.foreventsandroid.Data.Model.Transactions.ApiTransaction
import net.forevents.foreventsandroid.Util.closeApp
import net.forevents.foreventsandroid.presentation.servicelocator.Inject
import retrofit2.Response
import retrofit2.http.Body


class NucleusActivityVM: BaseViewModel() {

    //Uso de LiveData con la activity:
    val deleteUserState : MutableLiveData<Response<Body>> = MutableLiveData()
    val saveTransactionState : MutableLiveData<ApiTransaction> = MutableLiveData()
    val deleteTransactionState : MutableLiveData<Response<Body>> = MutableLiveData()
    val eventListState : MutableLiveData<List<AppEvents>> = MutableLiveData()
    val isLoadingState : MutableLiveData<Boolean> = MutableLiveData()

        fun loadEventList(userId:String){
            Inject.repository.getEventsList(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe{isLoadingState.postValue(true)}
                .doOnTerminate{isLoadingState.postValue(false)}
                .subscribeBy(
                    onNext = {
                        eventListState.value = it
                    },
                    onError = {
                        println("########ERROR IN LOAD EVENTS  ########### ${it.message}")
                    },
                    onComplete = {
                        Inject.settingsManager.firstLoad = false
                    }

                ).addTo(compositeDisposable)
        }

        fun deleteProfile(userId:String,token:String){
            Inject.repository.deleteProfile(userId,token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = {
                       when(it.code()) {
                           204 -> deleteUserState.value = it
                       }

                    },
                    onError = {
                        println("########ERROR IN DELETE PROFILE  ########### ${it.message}")
                    }

                ).addTo(compositeDisposable)
        }


    fun saveTransaction(token: String,eventId:String){
        Inject.repository.postTransaction(token,eventId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    saveTransactionState.value = it
                },
                onError = {
                    println("########ERROR IN SAVE TRANSACTION  ########### ${it.message}")
                }
            ).addTo(compositeDisposable)
    }


    fun delTransaction(token:String,transactionId:String){
        Inject.repository.delTransaction(token,transactionId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    when(it.code()) {
                        204 -> deleteTransactionState.value = it
                    }

                },
                onError = {
                    println("########ERROR IN DELETE TRANSACTION  ########### ${it.message}")
                }

            ).addTo(compositeDisposable)
    }
}