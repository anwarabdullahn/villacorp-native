package anwarabdullahn.com.villacorp_apps.Activity.InOut

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.MenuItem
import android.view.View
import anwarabdullahn.com.villacorp_apps.API.API
import anwarabdullahn.com.villacorp_apps.API.APICallback
import anwarabdullahn.com.villacorp_apps.API.APIError
import anwarabdullahn.com.villacorp_apps.API.APIResponse
import anwarabdullahn.com.villacorp_apps.Activity.DashboardActivity
import anwarabdullahn.com.villacorp_apps.R
import anwarabdullahn.com.villacorp_apps.Utils.LoadingHelper
import com.r0adkll.slidr.Slidr
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_do_in_out_detail.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton

class DoInOutDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_do_in_out_detail)

        val loadingScreen: DialogFragment = LoadingHelper.getInstance()
        var Nomor = intent.extras!!.getString("nomor")
        var Id = intent.extras!!.getString("id_inout")
        var Type = intent.extras!!.getString("type_inout")
        var Date = intent.extras!!.getString("date_inout")
        var Nama = intent.extras!!.getString("nama")
        var InputBy = intent.extras!!.getString("input_by")
        var Reason = intent.extras!!.getString("alasan")
        var Lampiran =  intent.extras!!.getString("lampiran")
        var Status =  intent.extras!!.getString("status")
        var RejectReason = intent.extras!!.getString("reject_reason")
        var UpdateBy = intent.extras!!.getString("update_by")


        Picasso.get().load(Lampiran).into(imagesContainer)

        toolbar.title = "Pengajuan Detail"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        Slidr.attach(this)

        nomorTxt.text = Nomor
        inputByTxt.text = InputBy
        alasanTxt.hint = Reason

        dateTxt.text = Date
        namaTxt.text = Nama

        when (Type) {
            "0" -> {
                typeTxt.text = "Datang Telat (Dispensasi)"
            }
            "1" -> {
                typeTxt.text = "Pulang Cepat (Dispensasi)"
            }
            "2" -> {
                typeTxt.text = "Datang Telat"
            }
            "3" -> {
                typeTxt.text = "Pulang Cepat"
            }
        }

        when (Status) {
            "0" -> {
                doCancelBtn.visibility = View.VISIBLE
                statusTxt.text = "On Process"
                statusTxt.background = resources.getDrawable(R.drawable.circle_menu_yellow)
                updateLabel.visibility = View.GONE
                updateTxtBy.visibility = View.GONE
                rejectLabel.visibility = View.GONE
                rejectTxt.visibility = View.GONE
            }
            "1" -> {
                doCancelBtn.visibility = View.GONE
                statusTxt.text = "Approved"
                statusTxt.background = resources.getDrawable(R.drawable.circle_menu_green)
                updateLabel.visibility = View.VISIBLE
                updateTxtBy.visibility = View.VISIBLE
                updateTxtBy.text = UpdateBy
                rejectLabel.visibility = View.GONE
                rejectTxt.visibility = View.GONE
            }
            else -> {
                doCancelBtn.visibility = View.GONE
                statusTxt.text = "Rejected"
                statusTxt.background = resources.getDrawable(R.drawable.circle_menu_red)
                updateLabel.visibility = View.VISIBLE
                updateTxtBy.visibility = View.VISIBLE
                updateTxtBy.text = UpdateBy
                rejectLabel.visibility = View.VISIBLE
                rejectTxt.visibility = View.VISIBLE
                rejectTxt.hint = RejectReason
            }
        }

        doCancelBtn.setOnClickListener {
            alert("Apakah Anda Yakin Ingin Cancel Pengajuan", "Telat & Pulang Cepat") {
                yesButton {
                    loadingScreen.show(supportFragmentManager,"loading Screen")
                    API.service().deletechangeinout(Id).enqueue(object : APICallback<APIResponse>(){
                        override fun onSuccess(t: APIResponse?) {
                            loadingScreen.dismiss()
                            val intent = Intent(this@DoInOutDetail, DashboardActivity::class.java)
                            intent.putExtra("result", t!!.msg)
                            startActivity(intent)
                        }

                        override fun onError(error: APIError?) {
                            loadingScreen.dismiss()
                            toast(error!!.msg)
                        }

                    })
                }
                noButton {  }
            }.show()
        }

    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
