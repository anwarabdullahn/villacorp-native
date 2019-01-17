package anwarabdullahn.com.villacorp_apps.Activity.TukarLibur

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
import kotlinx.android.synthetic.main.activity_do_tukar_libur_detail.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton

class DoTukarLiburDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_do_tukar_libur_detail)

        Slidr.attach(this)
        toolbarDoTukarLiburDetail.title = "Pengajuan Detail"
        setSupportActionBar(toolbarDoTukarLiburDetail)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val loadingScreen: DialogFragment = AnwLoadingHelper.getInstance()

        nomorPengajuanTxt.text = intent.extras!!.getString("Nomor")
        doPengajuanOldDateTxt.text = intent.extras!!.getString("DateOld")
        doPengajuanNewDateTxt.text = intent.extras!!.getString("DateNew")
        doPengajuanInputDate.text = intent.extras!!.getString("InputDate")
//        statusPengajuanTxt.text = intent.extras!!.getString("Status")
        alasanPengajuanTxt.hint = intent.extras!!.getString("Alasan")
        inputByPengajuanTxt.text = intent.extras!!.getString("InputBy")

        if (intent.extras!!.getString("Status") == "0"){
            doPengajuanSubmitBtn.visibility = View.VISIBLE
            statusPengajuanTxt.text = "On Process"
            statusPengajuanTxt.background = resources.getDrawable(R.drawable.circle_menu_yellow)
            updatedByPengajuanTukarLiburLabel.visibility = View.GONE
            updatedByPengajuanTukarLiburTxt.visibility = View.GONE
            alasanDitolakLabelPengajuanTukarLibur.visibility = View.GONE
            alasanPengajuanTxt2.visibility = View.GONE
        } else if(intent.extras!!.getString("Status") == "1") {
            doPengajuanSubmitBtn.visibility = View.GONE
            statusPengajuanTxt.text = "Approved"
            statusPengajuanTxt.background = resources.getDrawable(R.drawable.circle_menu_green)
            updatedByPengajuanTukarLiburLabel.visibility = View.VISIBLE
            updatedByPengajuanTukarLiburTxt.visibility = View.VISIBLE
            updatedByPengajuanTukarLiburTxt.text = intent.extras!!.getString("UpdatedBy")
            alasanDitolakLabelPengajuanTukarLibur.visibility = View.GONE
            alasanPengajuanTxt2.visibility = View.GONE
        } else {
            doPengajuanSubmitBtn.visibility = View.GONE
            statusPengajuanTxt.text = "Rejected"
            statusPengajuanTxt.background = resources.getDrawable(R.drawable.circle_menu_red)
            updatedByPengajuanTukarLiburLabel.visibility = View.VISIBLE
            updatedByPengajuanTukarLiburTxt.visibility = View.VISIBLE
            updatedByPengajuanTukarLiburTxt.text = intent.extras!!.getString("UpdatedBy")
            alasanDitolakLabelPengajuanTukarLibur.visibility = View.VISIBLE
            alasanPengajuanTxt2.hint = intent.extras!!.getString("RejectReason")
            alasanPengajuanTxt2.visibility = View.VISIBLE
        }

        doPengajuanSubmitBtn.setOnClickListener {
            alert("Apakah Anda Yakin Ingin Cancel Pengajuan Tukar Libur", "Tukar Libur") {
                yesButton {
                    loadingScreen.show(supportFragmentManager,"loading Screen")
                    AnwAPI.service().deletechangeoff(intent.extras!!.getString("IdChangeOff")).enqueue(object : AnwCallback<AnwResponse>(){
                        override fun onSuccess(t: AnwResponse?) {
                            loadingScreen.dismiss()
                            val intent = Intent(this@DoTukarLiburDetail, DashboardActivity::class.java)
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
