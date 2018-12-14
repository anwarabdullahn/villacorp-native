package anwarabdullahn.com.villacorp_apps.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import anwarabdullahn.com.villacorp_apps.Model.Pesan;
import anwarabdullahn.com.villacorp_apps.R;

import java.util.List;

public class AdapterPesanRecycler extends RecyclerView.Adapter<AdapterPesanRecycler.MyViewHolder> {

    private List<Pesan> pesanList;
    private Context context;

    public AdapterPesanRecycler(List<Pesan> pesanList, Context context){
        this.pesanList = pesanList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_pesan,viewGroup,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {


    }

    @Override
    public int getItemCount() {
        return pesanList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView judulTxt , waktuTxt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            judulTxt = itemView.findViewById(R.id.pesanJudulTxt);
            waktuTxt = itemView.findViewById(R.id.pesanWaktuTxt);
        }
    }

    public void addmore(List<Pesan> pesan)
    {
        for(Pesan pm : pesan){
            pesan.add(pm);
        }
    }
}
