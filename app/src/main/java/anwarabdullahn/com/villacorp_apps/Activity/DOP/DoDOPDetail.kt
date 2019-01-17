package anwarabdullahn.com.villacorp_apps.Activity.DOP

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
import kotlinx.android.synthetic.main.activity_do_dopdetail.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton

class DoDOPDetail : AppCompatActivity() {

    val loadingScreen: DialogFragment = AnwLoadingHelper.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_do_dopdetail)

        toolbar.title = "DOP Detail"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        Slidr.attach(this)

        val Nomor = intent.extras!!.getString("nomor") as String
        var Id = intent.extras!!.getString("id") as String
        val Type = intent.extras!!.getString("type") as String
        val Date = intent.extras!!.getString("date") as String
        val Nama = intent.extras!!.getString("nama") as String
        val InputBy = intent.extras!!.getString("input_by") as String
        val Reason = intent.extras!!.getString("alasan") as String
        val Expired =  intent.extras!!.getString("expired")
        val Status =  intent.extras!!.getString("status") as String
        val StatusAmbil =  intent.extras!!.getString("status_ambil")
        val RejectReason = intent.extras!!.getString("reject_reason")
        val UpdateBy = intent.extras!!.getString("update_by")

        when {
            Type == "0" && Status == "0" -> {
                expiredLabel.visibility = View.GONE
                expiredTxt.visibility = View.GONE
                expiredTxt.text = ""
                statusAmbilLabel.visibility = View.GONE
                statusAmbilTxt.visibility = View.GONE
            }
            Type == "0" && Status == "1" -> {
                expiredLabel.visibility = View.GONE
                expiredTxt.visibility = View.GONE
                expiredTxt.text = ""
                statusAmbilLabel.visibility = View.GONE
                statusAmbilTxt.visibility = View.GONE
            }
            Type == "0" && Status == "2" -> {
                expiredLabel.visibility = View.GONE
                expiredTxt.visibility = View.GONE
                expiredTxt.text = ""
                statusAmbilLabel.visibility = View.GONE
                statusAmbilTxt.visibility = View.GONE
            }
            Type == "1" && Status == "0" -> {
                expiredLabel.visibility = View.VISIBLE
                expiredTxt.visibility = View.VISIBLE
                expiredTxt.text = Expired
                statusAmbilLabel.visibility = View.GONE
                statusAmbilTxt.visibility = View.GONE
            }
            Type == "1" && Status == "1" -> {
                expiredLabel.visibility = View.VISIBLE
                expiredTxt.visibility = View.VISIBLE
                expiredTxt.text = Expired
                if (StatusAmbil == "0"){
                    statusAmbilTxt.text = "Belum Diambil"
                    statusAmbilTxt.background = resources.getDrawable(R.drawable.circle_menu_green)
                } else if(StatusAmbil == "1"){
                    statusAmbilTxt.text = "Sudah Diambil"
                    statusAmbilTxt.background = resources.getDrawable(R.drawable.circle_menu_red)
                }
            }
            Type == "1" && Status == "2" -> {
                expiredLabel.visibility = View.GONE
                expiredTxt.visibility = View.GONE
                expiredTxt.text = ""
                statusAmbilLabel.visibility = View.GONE
                statusAmbilTxt.visibility = View.GONE
            }
        }

        when (Type) {
            "0" -> {
                typeTxt.text = "DOP Libur"
            }
            "1" -> {
                typeTxt.text = "DOP Masuk"
            }
        }

        nomorTxt.text = Nomor
        inputByTxt.text = InputBy
        alasanTxt.hint = Reason
        dateTxt.text = Date
        namaTxt.text = Nama

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
            alert("Apakah Anda Yakin Ingin Cancel Pengajuan", "DOP Detail") {
                yesButton {
                    loadingScreen.show(supportFragmentManager,"loading Screen")
                    AnwAPI.service().deletedop(Id).enqueue(object : AnwCallback<AnwResponse>(){
                        override fun onSuccess(t: AnwResponse?) {
                            loadingScreen.dismiss()
                            val intent = Intent(this@DoDOPDetail, DashboardActivity::class.java)
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
