package anwarabdullahn.com.villacorp_apps.Activity.InOut

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.MenuItem
import anwarabdullahn.com.villacorp_apps.R
import com.r0adkll.slidr.Slidr
import kotlinx.android.synthetic.main.activity_do_in_out.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton
import java.text.SimpleDateFormat
import java.util.*
import android.util.Log
import android.view.inputmethod.InputMethodManager
import anwarabdullahn.com.villacorp_apps.API.API
import anwarabdullahn.com.villacorp_apps.API.APICallback
import anwarabdullahn.com.villacorp_apps.API.APIError
import anwarabdullahn.com.villacorp_apps.API.APIResponse
import anwarabdullahn.com.villacorp_apps.Activity.DashboardActivity
import anwarabdullahn.com.villacorp_apps.Utils.LoadingHelper
import com.nispok.snackbar.Snackbar
import com.nispok.snackbar.SnackbarManager
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.*


class DoInOutActivity : AppCompatActivity(){

    var CAPTURE_IMAGE : Int = 1
    var SELECT_GALLERY: Int = 0

    private var new_date: String? = null
    var mFile: File? = null
    val loadingScreen: DialogFragment = LoadingHelper.getInstance()
    lateinit var body: MultipartBody

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_do_in_out)

        toolbar.title = intent.extras!!.getString("Title")
        var type :String = intent.extras!!.getString("Type")!!.toString()
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

        fileInOutTxt.setOnClickListener {
            selectImage()
        }

        doInOutSubmitBtn.setOnClickListener {

            if (new_date == "" || new_date == null){
                hideSoftKeyboard(this@DoInOutActivity)
                toast("Tanggal Baru Belum dipilih.")
                return@setOnClickListener
            }

            if (alasanInOutTxt.text.toString() == ""){
                hideSoftKeyboard(this@DoInOutActivity)
                toast("Alasan harus diisi.")
                return@setOnClickListener
            }

            if (mFile == null&& (type == "1" || type == "0")){
                toast("File Belum dipilih.")
                return@setOnClickListener
            }

            if((type == "1" || type == "0") && mFile!!.isFile){
                body = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("userfile", mFile!!.name, RequestBody.create(MediaType.parse("image/jpeg"), mFile!!))
                    .addFormDataPart("in_out", type)
                    .addFormDataPart("tanggal", tanggalInOutTxtBtn.text.toString())
                    .addFormDataPart("reason", alasanInOutTxt.text.toString())
                    .build()
            } else {
                body = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("in_out", type)
                    .addFormDataPart("tanggal", tanggalInOutTxtBtn.text.toString())
                    .addFormDataPart("reason", alasanInOutTxt.text.toString())
                    .build()
            }

            loadingScreen.show(supportFragmentManager,"loading Screen")

            API.service().changeinout(body).enqueue(object : APICallback<APIResponse>(){
                override fun onSuccess(t: APIResponse?) {
                    loadingScreen.dismiss()
                    val intent = Intent(this@DoInOutActivity, DashboardActivity::class.java)
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
                        , this@DoInOutActivity)
                }
            })
        }
    }


    private fun selectImage() {

        val items = arrayOf<CharSequence>("Camera", "Gallery", "Cancel")
        val builder = AlertDialog.Builder(this@DoInOutActivity)
        builder.setItems(items) { dialog, which ->
            when {
                items[which] == "Camera" -> {

                    EasyImage.openCamera(this@DoInOutActivity , CAPTURE_IMAGE)
                }
                items[which] == "Gallery" -> {
                    EasyImage.openGallery(this@DoInOutActivity, SELECT_GALLERY)

                }
                items[which] == "Cancel" -> dialog.dismiss()
            }
        }
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, object : DefaultCallback() {
            override fun onImagePickerError(e: Exception?, source: EasyImage.ImageSource?, type: Int) {
                //Some error handling
            }

            override fun onImagePicked(imageFile: File, source: EasyImage.ImageSource, type: Int) {
                //Handle the image
                mFile = imageFile
                fileInOutTxt.text = imageFile.name
            }

            override fun onCanceled(source: EasyImage.ImageSource?, type: Int) {
                if (source == EasyImage.ImageSource.CAMERA) {
                    val photoFile = EasyImage.lastlyTakenButCanceledPhoto(this@DoInOutActivity)
                    photoFile?.delete()
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    //    Date Monday, 23 January 2018
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

    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity.currentFocus!!.windowToken, 0
        )
    }

}
