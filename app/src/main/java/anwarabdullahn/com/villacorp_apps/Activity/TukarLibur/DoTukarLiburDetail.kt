package anwarabdullahn.com.villacorp_apps.Activity.TukarLibur

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import anwarabdullahn.com.villacorp_apps.R
import com.r0adkll.slidr.Slidr
import kotlinx.android.synthetic.main.activity_do_tukar_libur_detail.*

class DoTukarLiburDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_do_tukar_libur_detail)

        Slidr.attach(this)
        toolbarDoTukarLiburDetail.title = "Pengajuan Detail"
        setSupportActionBar(toolbarDoTukarLiburDetail)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

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
        } else if(intent.extras!!.getString("Status") == "1") {
            doPengajuanSubmitBtn.visibility = View.GONE
            statusPengajuanTxt.text = "Approved"
            statusPengajuanTxt.background = resources.getDrawable(R.drawable.circle_menu_green)
        } else {
            doPengajuanSubmitBtn.visibility = View.GONE
            statusPengajuanTxt.text = "Rejected"
            statusPengajuanTxt.background = resources.getDrawable(R.drawable.circle_menu_red)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
