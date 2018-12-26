package anwarabdullahn.com.villacorp_apps.Activity.InOut

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import anwarabdullahn.com.villacorp_apps.R
import com.r0adkll.slidr.Slidr
import kotlinx.android.synthetic.main.activity_in_out.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

class DoInOutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_do_in_out)

        toolbar.title = intent.extras!!.getString("Title")
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        Slidr.attach(this)

        alert("Dispensasi Datang Telat & Pulang Cepat (Tidak Potong Gaji)\n" +
                "1. Force Majeur : Ban bocor, tabrakan (wajib melampirkan photo) \n" +
                "2. Personal : Urusan pribadi yang sifatnya penting \n" +
                "NB : Maksimal izin 120 menit dalam satu periode payroll (late in atau early out) satu kali.\n\n" +
                "Datang Telat & Pulang Cepat (Potong Gaji)\n" +
                "Yang tidak termasuk dalam kriteria diatas \n" +
                "NB : Potongan sesuai dengan ketentuan yang berlaku") {
            yesButton {  }
        }.show()
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
