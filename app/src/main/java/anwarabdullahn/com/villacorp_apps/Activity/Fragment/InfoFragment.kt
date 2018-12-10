package anwarabdullahn.com.villacorp_apps.Activity.Fragment

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import anwarabdullahn.com.villacorp_apps.API.API
import anwarabdullahn.com.villacorp_apps.API.APICallback
import anwarabdullahn.com.villacorp_apps.API.APIError
import anwarabdullahn.com.villacorp_apps.Model.Status
import anwarabdullahn.com.villacorp_apps.R
import anwarabdullahn.com.villacorp_apps.Utils.LoadingHelper
import kotlinx.android.synthetic.main.tab_fragment_status.*

class  InfoFragment: Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView = inflater.inflate(R.layout.fragment_info,null)

        var loadingScreen: DialogFragment = LoadingHelper.getInstance()

        loadingScreen.show(fragmentManager,"loading Screen")
        API.service().status().enqueue(object: APICallback<Status>(){
            override fun onSuccess(status: Status) {
                loadingScreen.dismiss()
                sisaHariKerjaTxt.text = status.sisaHariKerja
                sisaCutiTxt.text = status.jmlsisacuti.toString() + " Hari"
                sisaDOPTxt.text = status.hakdop.toString() + " Hari"
            }

            override fun onError(error: APIError?) {
                loadingScreen.dismiss()
            }

        })

        return contentView

    }
}
