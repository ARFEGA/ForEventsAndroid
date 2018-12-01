package net.forevents.foreventsandroid.presentation.Settings

import net.forevents.foreventsandroid.Util.mvvm.BaseViewModel
import androidx.lifecycle.MutableLiveData

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import net.forevents.foreventsandroid.Data.CreateUser.RandomUser.UserEntity
import net.forevents.foreventsandroid.Data.CreateUser.User.AppCity
import net.forevents.foreventsandroid.Data.CreateUser.User.AppEvents
import net.forevents.foreventsandroid.presentation.servicelocator.Inject
import java.util.concurrent.TimeUnit


class CityListVM: BaseViewModel() {

    //Uso de LiveData con la activity:
    val citiesListState : MutableLiveData<List<AppCity>> = MutableLiveData()
    val isLoadingState : MutableLiveData<Boolean> = MutableLiveData()

        fun loadCitiestList(city:String,limit:String){
            Inject.repository.getCitiesList(city,limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .debounce(4000,TimeUnit.MILLISECONDS)
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
                        Inject.settingsManager.firstLoad = false
                    }

                ).addTo(compositeDisposable)
        }
}