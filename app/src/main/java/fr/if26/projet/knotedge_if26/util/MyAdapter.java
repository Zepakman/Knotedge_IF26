package fr.if26.projet.knotedge_if26.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import fr.if26.projet.knotedge_if26.R;
import fr.if26.projet.knotedge_if26.entity.Object;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private LayoutInflater layoutInflater;
    private Context context;
    private List<Object> datas;


    public MyAdapter(Context context, List<Object> datas) {
        this.context = context;
        this.datas = datas;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.item_recycle_view, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int pos) {
        holder.nameTextView.setText(datas.get(pos).getName());
        holder.typeTextView.setText(datas.get(pos).getType());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:
                Toast.makeText(context, "itemView clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView typeTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.name_item_recycle_view);
            typeTextView = (TextView) itemView.findViewById(R.id.type_item_recycle_view);
        }
    }
}


