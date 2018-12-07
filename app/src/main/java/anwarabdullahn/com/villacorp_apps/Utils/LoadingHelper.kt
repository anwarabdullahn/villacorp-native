package anwarabdullahn.com.villacorp_apps.Utils

import android.app.ProgressDialog
import android.content.Context
import anwarabdullahn.com.villacorp_apps.R

/**
 * Created by anwarabdullahn on 1/30/18.
 */

object LoadingHelper {
    private var loading: ProgressDialog? = null

    fun loadingShow(context: Context) {
        loading = ProgressDialog.show(context, null, context.getString(R.string.please_wait))
    }

    fun loadingDismiss() {
        loading!!.dismiss()
    }
}
