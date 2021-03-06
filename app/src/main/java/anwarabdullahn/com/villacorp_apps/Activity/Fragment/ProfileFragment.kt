package anwarabdullahn.com.villacorp_apps.Activity.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import anwarabdullahn.com.villacorp_apps.API.AnwAPI
import anwarabdullahn.com.villacorp_apps.API.AnwCallback
import anwarabdullahn.com.villacorp_apps.API.AnwError
import anwarabdullahn.com.villacorp_apps.API.AnwResponse
import anwarabdullahn.com.villacorp_apps.Activity.LoginActivity
import anwarabdullahn.com.villacorp_apps.Model.Profile
import anwarabdullahn.com.villacorp_apps.R
import anwarabdullahn.com.villacorp_apps.Utils.AnwLoadingHelper
import com.orhanobut.hawk.Hawk
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import org.jetbrains.anko.*

class  ProfileFragment: Fragment(){

    private lateinit var contentView: View
    var loadingScreen: DialogFragment = AnwLoadingHelper.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        contentView = inflater.inflate(R.layout.fragment_profile,null)
        contentView.logoutBtn.setOnClickListener{logout()}

        loadingScreen.show(fragmentManager,"loading Screen")
        content()
        contentView.swipeUp.setOnRefreshListener {
            content()
            swipeUp.isRefreshing = false
        }
        return contentView

    }

    private fun logout(){
        Log.d("Token ", Hawk.get("TOKEN"))
        activity!!.alert("Apakah Anda Yakin Ingin Keluar", "Logout") {
            yesButton {
                loadingScreen.show(fragmentManager,"loading Screen")
                AnwAPI.service().logout().enqueue(object : AnwCallback<AnwResponse>() {
                    override fun onSuccess(t: AnwResponse?) {
                        loadingScreen.dismiss()
                        activity!!.startActivity<LoginActivity>()
                        activity!!.toast(t!!.msg)
                        activity!!.finish()
                        AnwAPI.logOut()
                    }

                    override fun onError(error: AnwError?) {
                        loadingScreen.dismiss()
                        activity!!.toast(error?.msg.toString())
                    }

                })
            }
            noButton {  }
        }.show()
    }

    fun content(){
        AnwAPI.service().profile().enqueue(object : AnwCallback<Profile>(){
            @SuppressLint("SetTextI18n")
            override fun onSuccess(profile: Profile) {
                loadingScreen.dismiss()
                Picasso.get().load(profile.photo).resize(50, 50).centerCrop().into(photoImg)
                karyawanNameTxt.text = profile.name
                jabatanTxt.text = profile.jabatan
                posisiTxt.text = " - " + profile.posisi
                tempatLahirTxt.text = profile.birth_place
                tanggalLahir.text = profile.birth_date
                jenisKelaminTxt.text = profile.sex
                agamaTxt.text = profile.religion
                alamatTxt.text = profile.address
                noTelponTxt.text = profile.phone
                brandtxt.text = profile.brand
                kantorTxt.text = profile.office
                departmentTxt.text = profile.department
                jadwalKerjaTxt.text = profile.schedule
            }

            override fun onError(error: AnwError?) {
                loadingScreen.dismiss()
                activity!!.toast(error?.msg.toString())
            }

        })
    }
}
