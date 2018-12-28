package anwarabdullahn.com.villacorp_apps.Activity.InOut

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
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
import anwarabdullahn.com.villacorp_apps.API.API
import anwarabdullahn.com.villacorp_apps.API.APICallback
import anwarabdullahn.com.villacorp_apps.API.APIError
import anwarabdullahn.com.villacorp_apps.API.APIResponse
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
    lateinit var stringUri: String
    lateinit var mFile: File

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

        fileInOutChooserBtn.setOnClickListener {
            toast("Ketekan")
            selectImage()
        }

        doInOutSubmitBtn.setOnClickListener {

            var body = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("userfile", mFile.name, RequestBody.create(MediaType.parse("image/jpeg"), mFile))
                .addFormDataPart("in_out", type)
                .addFormDataPart("tanggal", tanggalInOutTxtBtn.text.toString())
                .addFormDataPart("reason", alasanInOutTxt.text.toString())
                .build()

            API.service().changeinout(body).enqueue(object : APICallback<APIResponse>(){
                override fun onSuccess(t: APIResponse?) {
                    toast(t!!.msg)
                }

                override fun onError(error: APIError?) {
                    toast(error!!.msg)
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

}
