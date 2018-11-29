package net.forevents.foreventsandroid.Util

import androidx.fragment.app.Fragment
import androidx.lifecycle.*

inline fun <reified T : ViewModel> androidx.fragment.app.FragmentActivity.getViewModel(): T {
    return ViewModelProviders.of(this )[T::class.java]
}

inline fun <reified T : ViewModel> androidx.fragment.app.FragmentActivity.withViewModel(body: T.() -> Unit): T {
    val vm = getViewModel<T>()
    vm.body()
    return vm
}

inline fun <reified T : ViewModel> Fragment.getViewModel(): T {
    return ViewModelProviders.of(this)[T::class.java]
}

inline fun <reified T : ViewModel> Fragment.withViewModel( body: T.() -> Unit): T {
    val vm = getViewModel<T>()
    vm.body()
    return vm
}

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) {
    liveData.observe(this, Observer(body))
}
