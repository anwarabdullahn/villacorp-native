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
import anwarabdullahn.com.villacorp_apps.Model.Info
import anwarabdullahn.com.villacorp_apps.R
import anwarabdullahn.com.villacorp_apps.Utils.LoadingHelper
import kotlinx.android.synthetic.main.fragment_info.*

class  InfoFragment: Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView = inflater.inflate(R.layout.fragment_info,null)

        var loadingScreen: DialogFragment = LoadingHelper.getInstance()

        loadingScreen.show(fragmentManager,"loading Screen")
        API.service().status().enqueue(object: APICallback<Info>(){
            override fun onSuccess(info: Info) {
                loadingScreen.dismiss()
                if (info.sisaHariKerja == "Permanen"){
                    sisaHariKerjaTxt.text = info.sisaHariKerja
                } else {
                    sisaHariKerjaTxt.text = (info.sisaHariKerja).substring(0,8)
                }
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
            }

        })

        return contentView

    }
}
