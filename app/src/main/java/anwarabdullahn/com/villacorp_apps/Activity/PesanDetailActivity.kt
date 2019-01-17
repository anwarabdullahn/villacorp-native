package anwarabdullahn.com.villacorp_apps.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import anwarabdullahn.com.villacorp_apps.API.AnwAPI
import anwarabdullahn.com.villacorp_apps.API.AnwCallback
import anwarabdullahn.com.villacorp_apps.API.AnwError
import anwarabdullahn.com.villacorp_apps.API.AnwResponse
import anwarabdullahn.com.villacorp_apps.R
import anwarabdullahn.com.villacorp_apps.Request.ReadRequest
import com.r0adkll.slidr.Slidr
import kotlinx.android.synthetic.main.activity_pesan_detail.*
import org.jetbrains.anko.toast

class PesanDetailActivity : AppCompatActivity() {

    var body = ReadRequest()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesan_detail)
        Slidr.attach(this)
        toolbar.title = "Pesan"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        pesanTxt.text = intent.extras!!.getString("pesan")
        judulTxt.text = intent.extras!!.getString("judul")
        approveTxt.text = "diselesaikan oleh - " + intent.extras!!.getString("approve")
        waktuTxt.text = intent.extras!!.getString("waktu")

        content()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    internal fun content(){
        body.id = (intent.extras.getString("id")).toInt()
        AnwAPI.service().read(body).enqueue(object: AnwCallback<AnwResponse>(){
            override fun onSuccess(t: AnwResponse) {
                toast(t.msg)
            }

            override fun onError(error: AnwError) {
                toast(error.msg)
            }

        })
    }
}
