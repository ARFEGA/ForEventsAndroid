package net.forevents.foreventsandroid.presentation


import android.app.Application
import android.preference.PreferenceManager
import net.forevents.foreventsandroid.presentation.servicelocator.Inject
import net.forevents.foreventsandroid.Util.SettingsManager


class UserApp:Application() {
    override fun onCreate() {
        super.onCreate()
        Inject.settingsManager = SettingsManager(PreferenceManager.getDefaultSharedPreferences(this))
    }
}