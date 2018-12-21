package anwarabdullahn.com.villacorp_apps.Activity.Fragment

import android.annotation.SuppressLint
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
import anwarabdullahn.com.villacorp_apps.Model.Info
import anwarabdullahn.com.villacorp_apps.R
import anwarabdullahn.com.villacorp_apps.Utils.LoadingHelper
import kotlinx.android.synthetic.main.fragment_info.*
import kotlinx.android.synthetic.main.fragment_info.view.*
import org.jetbrains.anko.toast

class  InfoFragment: Fragment(){

    private lateinit var contentView: View
    var loadingScreen: DialogFragment = LoadingHelper.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        contentView = inflater.inflate(R.layout.fragment_info,null)

        loadingScreen.show(fragmentManager,"loading Screen")
        content()
        contentView.swipeUp.setOnRefreshListener {
            content()
            swipeUp.isRefreshing = false
        }

        return contentView

    }

    fun content(){
        API.service().status().enqueue(object: APICallback<Info>(){
            @SuppressLint("SetTextI18n")
            override fun onSuccess(info: Info) {
                loadingScreen.dismiss()
                if (info.sisaHariKerja == "Permanen"){
                    sisaHariKerjaTxt.text = info.sisaHariKerja
                } else {
                    sisaHariKerjaTxt.text = (info.sisaHariKerja).substring(0,8)
                }
                Log.d("Benda ","Itu")
                Log.d("Mananyaa Benda Itu", info.jmlcutitahunan.toString())

                sisaCutiTxt.text = info.jmlsisacuti.toString() + " hari"
                sisaDOPTxt.text = info.hakdop.toString() + " hari"
                hariTelatKerjaLambat.text = info.haritelatkerja.lambat.toString() + " hari"
                hariTelatKerjaCepat.text = info.haritelatkerja.cepat.toString() + " hari"
                jmlhariKerja.text = info.jmlharikerja
                telatKerjaLate.text = info.telatkerja.totalLate
                telatKerjaEarly.text = info.telatkerja.totalEarly
                jmlDopLibur.text = info.jmldoplibur.toString() + " hari"
                jmlAbsen.text = info.jmlabsen.toString() + " hari"
                jmlIzin.text = info.jmlizin.toString() + " hari"
                jmlSakit.text = info.jmlsakit.toString() + " hari"
                jmlCutiKhusus.text = info.jmlcutikhusus.toString() + " hari"
                jmlCutiTahunan.text = info.jmlcutitahunan.toString() + " hari"
                jmlDopMasuk.text = info.jmldopmasuk.toString() + " hari"
                priodeTxt.text = info.periode
            }

            override fun onError(error: APIError?) {
                loadingScreen.dismiss()
                activity!!.toast(error?.msg.toString())
            }

        })
    }
}
