package anwarabdullahn.com.villacorp_apps.Activity.TukarLibur

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import anwarabdullahn.com.villacorp_apps.R

class JadwalFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView = inflater.inflate(R.layout.tukar_libur_jadwal,container,false)

        return contentView
    }
}