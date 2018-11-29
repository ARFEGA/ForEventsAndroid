package net.forevents.foreventsandroid.presentation.EventDetail


import androidx.lifecycle.MutableLiveData
import net.forevents.foreventsandroid.Data.CreateUser.RandomUser.UserEntity
import net.forevents.foreventsandroid.Util.mvvm.BaseViewModel


class EventDetailVM : BaseViewModel(){
    //Parte de LiveData, creando variable para exponer la información
    val userState : MutableLiveData<UserEntity> = MutableLiveData()


   /* fun loadUserById(userId:Long){
        Inject.repository.getUserdetail(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    userState.value = it
                },
                onError = {

                },
                onComplete = {

                }
            ).addTo(compositeDisposable)
    }*/

}