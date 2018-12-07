package anwarabdullahn.com.villacorp_apps

import android.app.Application
import com.orhanobut.hawk.Hawk

class Initialize : Application() {

    override fun onCreate() {
        super.onCreate()
        Hawk.init(this).build()
    }
}