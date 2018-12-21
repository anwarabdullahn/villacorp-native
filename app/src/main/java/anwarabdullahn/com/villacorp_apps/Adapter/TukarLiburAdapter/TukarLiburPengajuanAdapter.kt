package anwarabdullahn.com.villacorp_apps.Adapter.TukarLiburAdapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import anwarabdullahn.com.villacorp_apps.Model.Pengajuan

class TukarLiburPengajuanAdapter(val pengajuanList: MutableList<Pengajuan>) : RecyclerView.Adapter <TukarLiburJadwalAdapter.ViewHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TukarLiburJadwalAdapter.ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(p0: TukarLiburJadwalAdapter.ViewHolder, p1: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    fun addmore(pengajuan: MutableList<Pengajuan>){
        for (jd in pengajuan){
            pengajuanList.add(jd)
        }
    }
}