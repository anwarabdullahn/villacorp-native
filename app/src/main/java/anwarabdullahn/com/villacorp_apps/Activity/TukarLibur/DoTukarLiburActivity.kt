package anwarabdullahn.com.villacorp_apps.Activity.TukarLibur

import android.annotation.SuppressLint
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
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import anwarabdullahn.com.villacorp_apps.Model.OffDay
import anwarabdullahn.com.villacorp_apps.Utils.LoadingHelper
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import org.jetbrains.anko.find
import java.text.ParseException


class DoTukarLiburActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {


    private var new_date: String? = null
    lateinit var holidays: ArrayList<String>
    lateinit var alasanTukarLiburTxt: EditText

    val loadingScreen: DialogFragment = LoadingHelper.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_do_tukar_libur)
        Slidr.attach(this)
        toolbarDoTukarLibur.title = "Tukar Tanggal Libur"
        setSupportActionBar(toolbarDoTukarLibur)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        alasanTukarLiburTxt = find(R.id.alasanTukarLiburTxt)

        doTukarTanggalOldDateTxt.text = intent.extras!!.getString("oldDate")

        doTukarTanggalNewDateTxt.setOnClickListener {

//            val calendar = Calendar.getInstance()
//            val _year = calendar.get(Calendar.YEAR)
//            val _month = calendar.get(Calendar.MONTH)
//            val _day = calendar.get(Calendar.DAY_OF_MONTH)
//
//            val datePickerDialog = DatePickerDialog(this, R.style.AlertDialog,
//
//                    DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
//                        new_date = convertDate(convertToMillis(dayOfMonth, month, year))
//
//                        doTukarTanggalNewDateTxt.text = new_date
//
//                    }, _year, _month, _day)
//
//            datePickerDialog.datePicker.minDate = calendar.timeInMillis + countDay(1)
//            datePickerDialog.show()
//            val holidays = arrayOf("07-03-2018", "05-03-2018", "10-03-2018")
//            val now = Calendar.getInstance()
//            val _year = now.get(Calendar.YEAR)
//            val _month = now.get(Calendar.MONTH)
//            val _day = now.get(Calendar.DAY_OF_MONTH)
//            var calendar = GregorianCalendar(_year, _month,_day+1)
//
//            val dpd = DatePickerDialog.newInstance(
//                this@DoTukarLiburActivity,
//                now.get(Calendar.YEAR),
//                now.get(Calendar.MONTH),
//                now.get(Calendar.DAY_OF_MONTH)
//            )
//            dpd.vibrate(true)
//            dpd.minDate = calendar
////            dpd.disabledDays =
//            dpd.setVersion(DatePickerDialog.Version.VERSION_2)
//            dpd.show(fragmentManager, "Datepickerdialog")

            showDatePicker()

        }

        doTukarLiburSubmitBtn.setOnClickListener {

            if (new_date == "" || new_date == null) {
                hideSoftKeyboard(this@DoTukarLiburActivity)
                toast("Tanggal Baru Belum dipilih.")
                return@setOnClickListener
            }

            if (alasanTukarLiburTxt.text.toString() == "") {
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


            loadingScreen.show(supportFragmentManager, "loading Screen")
            API.service().changeoff(body).enqueue(object : APICallback<APIResponse>() {
                override fun onSuccess(t: APIResponse?) {
                    loadingScreen.dismiss()
                    val intent = Intent(this@DoTukarLiburActivity, DashboardActivity::class.java)
                    intent.putExtra("result", t!!.msg)
                    startActivity(intent)
                }

                override fun onError(error: APIError?) {
                    loadingScreen.dismiss()
                    SnackbarManager.show(
                        Snackbar.with(applicationContext)
                            .text(error!!.msg)
                            .duration(Snackbar.SnackbarDuration.LENGTH_LONG)
                            .actionLabel("OK")
                        , this@DoTukarLiburActivity
                    )
                }
            })
        }
    }

    fun showDatePicker() {

        API.service().offday().enqueue(object: APICallback<OffDay>() {
            override fun onSuccess(t: OffDay) {

//                Log.d("nganu", t.offday.toString())

                var calendar = Calendar.getInstance()
                val dpd = DatePickerDialog.newInstance(
                    this@DoTukarLiburActivity,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
                dpd.vibrate(true)
                dpd.minDate = calendar
                dpd.version = DatePickerDialog.Version.VERSION_2
                dpd.show(fragmentManager, "DatePickerDialog")

                val sdf = SimpleDateFormat("yyyy-MM-dd")
                holidays = ArrayList(t.offday)
                
                var date: java.util.Date? = null
                for (i in 0 until holidays.size) {
                    try {
                        date = sdf.parse(holidays[i])
                    } catch (e: ParseException) {
                        e.printStackTrace()
                    }
                    calendar = dateToCalendar(date)
                    val dates = ArrayList<Calendar>()
                    dates.add(calendar)
                    val display = dates.toTypedArray()
                    dpd.disabledDays = display
                }

            }

            override fun onError(error: APIError) {
                toast(error.msg)
                return
            }

        })



    }
    private fun dateToCalendar(date: Date?): Calendar {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        new_date = convertDate(convertToMillis(dayOfMonth, monthOfYear, year))
        doTukarTanggalNewDateTxt.text = new_date
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
        return day * 24 * 60 * 60 * 1000
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
