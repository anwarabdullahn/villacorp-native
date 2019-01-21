package anwarabdullahn.com.villacorp_apps.Adapter.LeaveFinger

import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import anwarabdullahn.com.villacorp_apps.Activity.InOut.DoInOutDetail
import anwarabdullahn.com.villacorp_apps.Activity.LeaveFinger.DoLeaveFinger
import anwarabdullahn.com.villacorp_apps.Activity.LeaveFinger.DoLeaveFingerDetails
import anwarabdullahn.com.villacorp_apps.Activity.TukarLibur.DoTukarLiburActivity
import anwarabdullahn.com.villacorp_apps.Model.InOut
import anwarabdullahn.com.villacorp_apps.Model.JadwalLibur
import anwarabdullahn.com.villacorp_apps.Model.LeaveFinger
import anwarabdullahn.com.villacorp_apps.R
import org.jetbrains.anko.find

class LeaveFingerAdapter(val leaveFingerList: MutableList<LeaveFinger>) : RecyclerView.Adapter <LeaveFingerAdapter.ViewHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val contentView = LayoutInflater.from(p0.context).inflate(R.layout.list_cuti, p0, false)

        return ViewHolder(contentView)
    }

    override fun getItemCount(): Int {
        return leaveFingerList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val codeTxt = itemView.find<TextView>(R.id.codeInOutTxt)
        val tanggalTxt= itemView.find<TextView>(R.id.tanggalInOutTxt)
        val statusTxt= itemView.find<TextView>(R.id.statusInOutTxt)

        val redColor = itemView.resources.getDrawable(R.drawable.circle_menu_red)!!
        val yellowColor = itemView.resources.getDrawable(R.drawable.circle_menu_yellow)!!
        val greenColor = itemView.resources.getDrawable(R.drawable.circle_menu_green)!!
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val leaveFinger: LeaveFinger = leaveFingerList[p1]
        p0.codeTxt.text = leaveFinger.Nomor
        p0.tanggalTxt.text = leaveFinger.Date

        when {
            leaveFinger.Status == "2" -> {
                p0.statusTxt.text = "Rejected"
                p0.statusTxt.background =  p0.redColor
            }
            leaveFinger.Status == "1" -> {
                p0.statusTxt.text = "Approved"
                p0.statusTxt.background =  p0.greenColor
            }
            else -> {
                p0.statusTxt.text = "On Process"
                p0.statusTxt.background =  p0.yellowColor
            }
        }

        p0.statusTxt.setOnClickListener {
            val intent = Intent(p0.itemView.context, DoLeaveFingerDetails::class.java)
            intent.putExtra("id", leaveFinger.ID)
            intent.putExtra("nomor", leaveFinger.Nomor)
            intent.putExtra("type", leaveFinger.TypeLeave)
            intent.putExtra("date", leaveFinger.Date)
            intent.putExtra("nama", leaveFinger.InputBy)
            intent.putExtra("alasan", leaveFinger.Reason)
            intent.putExtra("update_by", leaveFinger.UpdateBy)
            intent.putExtra("input_by", leaveFinger.InputBy)
            intent.putExtra("reject_reason", leaveFinger.RejectReason)
            intent.putExtra("lampiran", leaveFinger.Lampiran)
            intent.putExtra("status", leaveFinger.Status)
            startActivity(p0.itemView.context,intent,null)
        }
    }

    fun addmore(leaveFinger: MutableList<LeaveFinger>){
        for (iO in leaveFinger){
            leaveFingerList.add(iO)
        }
        notifyDataSetChanged()
    }


}