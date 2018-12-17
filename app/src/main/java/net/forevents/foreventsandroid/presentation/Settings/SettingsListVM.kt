package net.forevents.foreventsandroid.presentation.Settings

import net.forevents.foreventsandroid.Util.mvvm.BaseViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import net.forevents.foreventsandroid.Data.Model.City.AppCity
import net.forevents.foreventsandroid.Data.Model.EventType.AppEventType
import net.forevents.foreventsandroid.presentation.servicelocator.Inject
import java.util.concurrent.TimeUnit


class SettingsListVM: BaseViewModel() {

    //Uso de LiveData con la activity:
    val citiesListState : MutableLiveData<List<AppCity>> = MutableLiveData()
    val isLoadingState : MutableLiveData<Boolean> = MutableLiveData()
    val eventTypeListState : MutableLiveData<List<AppEventType>> = MutableLiveData()

        fun loadCitiestList(city:String,limit:String){
            Inject.repository.getCitiesList(city,limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .debounce(300,TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .doOnSubscribe{isLoadingState.postValue(true)}
                .doOnTerminate{isLoadingState.postValue(false)}
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

    fun loadEventTypeList(){
        Inject.repository.getEventType()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    eventTypeListState.value = it
                },
                onError = {
                    println("########ERROR IN LOAD EVENTS type ########### ${it.message}")
                },
                onComplete = {
                    //Inject.settingsManager.firstLoad = false
                }

            ).addTo(compositeDisposable)
    }
}