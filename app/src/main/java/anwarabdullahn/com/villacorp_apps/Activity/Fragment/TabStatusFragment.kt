package anwarabdullahn.com.villacorp_apps.Activity.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import anwarabdullahn.com.villacorp_apps.R

class  TabStatusFragment: Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView = inflater.inflate(R.layout.tab_fragment_status,null)
//        var loadingScreen: DialogFragment = LoadingHelper.getInstance()
//
//        loadingScreen.show(fragmentManager,"loading Screen")
//        API.service().status().enqueue(object: APICallback<Status>(){
//            override fun onSuccess(status: Status) {
//                loadingScreen.dismiss()
//                sisaHariKerjaTxt.text = status.sisaHariKerja
//                sisaCutiTxt.text = status.jmlsisacuti.toString() + " Hari"
//                sisaDOPTxt.text = status.hakdop.toString() + " Hari"
//            }
//
//            override fun onError(error: APIError?) {
//                loadingScreen.dismiss()
//            }
//
//        })

        return contentView
    }
}
