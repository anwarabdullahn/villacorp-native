package anwarabdullahn.com.villacorp_apps.Activity.InOut

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import anwarabdullahn.com.villacorp_apps.R
import com.r0adkll.slidr.Slidr
import kotlinx.android.synthetic.main.activity_do_in_out.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import java.text.SimpleDateFormat
import java.util.*



class DoInOutActivity : AppCompatActivity(), View.OnClickListener {

    val mMediaUri : Uri? = null
    val fileUri: Uri? = null
    val mediaPath: String? = null
    var mediaImageLocation = ""
    var postPath: String? = ""

    private var new_date: String? = null

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
                "NB : Potongan sesuai dengan ketentuan yang berlaku", "Telat & Pulang Cepat") {
            yesButton {  }
        }.show()

        tanggalInOutTxtBtn.setOnClickListener {
            val calendar = Calendar.getInstance()
            val _year = calendar.get(Calendar.YEAR)
            val _month = calendar.get(Calendar.MONTH)
            val _day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, R.style.AlertDialog,

                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    new_date = convertDate(convertToMillis(dayOfMonth, month, year))

                    tanggalInOutTxtBtn.text = new_date

                }, _year, _month, _day)

            datePickerDialog.datePicker.minDate = calendar.timeInMillis
            datePickerDialog.datePicker.maxDate = calendar.timeInMillis + countDay(6)
            datePickerDialog.show()
        }

    }


    override fun onClick(v: View?) {

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    //    Date Monday, 23 January 2018
    @SuppressLint("SimpleDateFormat")
    fun convertDate(mTime: Long): String {
        val df = SimpleDateFormat("EEEE, d MMMM yyyy")
        return df.format(mTime)
    }

    //    Convert To Millis Date
    fun convertToMillis(day: Int, month: Int, year: Int): Long {
        val calendarStart = Calendar.getInstance()
        calendarStart.set(Calendar.YEAR, year)
        calendarStart.set(Calendar.MONTH, month)
        calendarStart.set(Calendar.DAY_OF_MONTH, day)
        return calendarStart.timeInMillis
    }

    fun countDay(day: Int): Int {
        return day*24*60*60*1000
    }
}
