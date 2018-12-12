package anwarabdullahn.com.villacorp_apps.Activity.Fragment

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import anwarabdullahn.com.villacorp_apps.API.API
import anwarabdullahn.com.villacorp_apps.API.APICallback
import anwarabdullahn.com.villacorp_apps.API.APIError
import anwarabdullahn.com.villacorp_apps.API.APIResponse
import anwarabdullahn.com.villacorp_apps.Activity.LoginActivity
import anwarabdullahn.com.villacorp_apps.R
import anwarabdullahn.com.villacorp_apps.Utils.LoadingHelper
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.fragment_profile.view.*
import org.jetbrains.anko.*

class  ProfileFragment: Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView = inflater.inflate(R.layout.fragment_profile,null)

//        contentView.logoutBtn.setOnClickListener{logout()}

        return contentView

    }

    private fun logout(){
        var loadingScreen: DialogFragment = LoadingHelper.getInstance()
        Log.d("Token ", Hawk.get("TOKEN"))
        activity!!.alert("Apakah Anda Yakin Ingin Keluar", "Logout") {
            yesButton {
                loadingScreen.show(fragmentManager,"loading Screen")
                API.service().logout().enqueue(object : APICallback<APIResponse>() {
                    override fun onSuccess(msg: APIResponse?) {
                        loadingScreen.dismiss()
                        activity!!.startActivity<LoginActivity>()
                        activity!!.toast("Berhasil Logout")
                        activity!!.finish()
                        API.logOut()
                    }

                    override fun onError(error: APIError?) {
                        loadingScreen.dismiss()
                        activity!!.toast(error?.msg.toString())
                    }

                })
            }
            noButton {  }
        }.show()
    }
}
