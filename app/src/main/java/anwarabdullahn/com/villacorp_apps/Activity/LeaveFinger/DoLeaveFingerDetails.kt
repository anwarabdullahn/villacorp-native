package anwarabdullahn.com.villacorp_apps.Activity.LeaveFinger

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.MenuItem
import android.view.View
import anwarabdullahn.com.villacorp_apps.API.AnwAPI
import anwarabdullahn.com.villacorp_apps.API.AnwCallback
import anwarabdullahn.com.villacorp_apps.API.AnwError
import anwarabdullahn.com.villacorp_apps.API.AnwResponse
import anwarabdullahn.com.villacorp_apps.Activity.DashboardActivity
import anwarabdullahn.com.villacorp_apps.R
import anwarabdullahn.com.villacorp_apps.Utils.AnwLoadingHelper
import com.r0adkll.slidr.Slidr
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_do_leave_finger_details.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton

class DoLeaveFingerDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_do_leave_finger_details)

        toolbar.title = "Pengajuan Detail"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        Slidr.attach(this)

        val loadingScreen: DialogFragment = AnwLoadingHelper.getInstance()
        var Nomor = intent.extras!!.getString("nomor")
        var Id = intent.extras!!.getString("id")
        var Type = intent.extras!!.getString("type")
        var Date = intent.extras!!.getString("date")
        var Nama = intent.extras!!.getString("nama")
        var InputBy = intent.extras!!.getString("input_by")
        var Reason = intent.extras!!.getString("alasan")
        var Lampiran =  intent.extras!!.getString("lampiran")
        var Status =  intent.extras!!.getString("status")
        var RejectReason = intent.extras!!.getString("reject_reason")
        var UpdateBy = intent.extras!!.getString("update_by")

        Picasso.get().load(Lampiran).into(imagesContainer)

        nomorTxt.text = Nomor
        inputByTxt.text = InputBy
        alasanTxt.hint = Reason

        dateTxt.text = Date
        namaTxt.text = Nama

        when (Type) {
            "4" -> {
                typeTxt.text = "Dispensasi / Cuti Khusus"
            }
            "5" -> {
                typeTxt.text = "Cuti Tahunan"
            }
//            "2" -> {
//                typeTxt.text = "Datang Telat"
//            }
//            "3" -> {
//                typeTxt.text = "Pulang Cepat"
//            }
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
            alert("Apakah Anda Yakin Ingin Cancel Pengajuan", "Cuti Dibayar") {
                yesButton {
                    loadingScreen.show(supportFragmentManager,"loading Screen")
                    AnwAPI.service().deleteleavefinger(Id).enqueue(object : AnwCallback<AnwResponse>(){
                        override fun onSuccess(t: AnwResponse?) {
                            loadingScreen.dismiss()
                            val intent = Intent(this@DoLeaveFingerDetails, DashboardActivity::class.java)
                            intent.putExtra("result", t!!.msg)
                            startActivity(intent)
                        }

                        override fun onError(error: AnwError?) {
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
