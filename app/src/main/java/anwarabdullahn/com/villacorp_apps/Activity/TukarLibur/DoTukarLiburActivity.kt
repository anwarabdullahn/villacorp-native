package anwarabdullahn.com.villacorp_apps.Activity.TukarLibur

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import anwarabdullahn.com.villacorp_apps.API.API
import anwarabdullahn.com.villacorp_apps.API.APICallback
import anwarabdullahn.com.villacorp_apps.API.APIError
import anwarabdullahn.com.villacorp_apps.API.APIResponse
import anwarabdullahn.com.villacorp_apps.Activity.DashboardActivity
import anwarabdullahn.com.villacorp_apps.R
import anwarabdullahn.com.villacorp_apps.Request.ChangeOffRequest
import com.nispok.snackbar.Snackbar
import com.nispok.snackbar.SnackbarManager
import com.r0adkll.slidr.Slidr
import kotlinx.android.synthetic.main.activity_do_tukar_libur.*
import org.jetbrains.anko.toast
import java.util.*
import java.text.SimpleDateFormat
import android.app.Activity
import android.support.v4.app.DialogFragment
import android.view.View
import android.view.inputmethod.InputMethodManager
import anwarabdullahn.com.villacorp_apps.Utils.LoadingHelper


class DoTukarLiburActivity : AppCompatActivity(){

    private var new_date: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_do_tukar_libur)
        Slidr.attach(this)
        toolbarDoTukarLibur.title = "Tukar Tanggal Libur"
        setSupportActionBar(toolbarDoTukarLibur)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        doTukarTanggalOldDateTxt.text = intent.extras!!.getString("oldDate")

        doTukarTanggalNewDateTxt.setOnClickListener {

            val calendar = Calendar.getInstance()
            val _year = calendar.get(Calendar.YEAR)
            val _month = calendar.get(Calendar.MONTH)
            val _day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, R.style.AlertDialog,

                    DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                        new_date = convertDate(convertToMillis(dayOfMonth, month, year))

                        doTukarTanggalNewDateTxt.text = new_date

                    }, _year, _month, _day)

            datePickerDialog.datePicker.minDate = calendar.timeInMillis + countDay(1)
            datePickerDialog.show()
        }

        doTukarLiburSubmitBtn.setOnClickListener {

            if (new_date == "" || new_date == null){
                hideSoftKeyboard(this@DoTukarLiburActivity)
                toast("Tanggal Baru Belum dipilih.")
                return@setOnClickListener
            }

            if (alasanTukarLiburTxt.text.toString() == ""){
                hideSoftKeyboard(this@DoTukarLiburActivity)
                toast("Alasan Harus diisi.")
                return@setOnClickListener
            }

            hideSoftKeyboard(this@DoTukarLiburActivity)
            val body = ChangeOffRequest()
            body.id = intent.extras!!.getString("id")
            body.jdid = intent.extras!!.getString("jdid")
            body.old_date = intent.extras!!.getString("oldDate")
            body.new_date = new_date as String
            body.reason = alasanTukarLiburTxt.text.toString()

            loadingDoTukarJadwal.visibility = View.VISIBLE
            API.service().changeoff(body).enqueue(object : APICallback<APIResponse>(){
                override fun onSuccess(t: APIResponse?) {
                    loadingDoTukarJadwal.visibility = View.GONE
                    val intent = Intent(this@DoTukarLiburActivity, DashboardActivity::class.java)
                        intent.putExtra("result", t!!.msg)
                    startActivity(intent)
                }

                override fun onError(error: APIError?) {
                    loadingDoTukarJadwal.visibility = View.GONE
                    SnackbarManager.show(
                        Snackbar.with(applicationContext)
                            .text(error!!.msg)
                            .duration(Snackbar.SnackbarDuration.LENGTH_LONG)
                            .actionLabel("OK")
                        , this@DoTukarLiburActivity)
                }

            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    //    Date Monday, 23 January 2018
    @SuppressLint("SimpleDateFormat")
    fun convertDate(mTime: Long): String {
        val df = SimpleDateFormat("EEEE, W MMMM yyyy")
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

    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity.currentFocus!!.windowToken, 0
        )
    }
}
