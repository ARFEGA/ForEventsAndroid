package net.forevents.foreventsandroid.presentation.EventList

import net.forevents.foreventsandroid.Util.mvvm.BaseViewModel
import androidx.lifecycle.MutableLiveData

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

import net.forevents.foreventsandroid.Data.Model.Events.AppEvents
import net.forevents.foreventsandroid.presentation.servicelocator.Inject


class EventListVM: BaseViewModel() {

    //Uso de LiveData con la activity:
    val eventListState : MutableLiveData<List<AppEvents>> = MutableLiveData()
    val isLoadingState : MutableLiveData<Boolean> = MutableLiveData()

        fun loadEventList(media:String,userId:String,typeEventId:String,locationRadius:String){
            Inject.repository.getEventsList(media,userId,typeEventId,locationRadius)
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
}