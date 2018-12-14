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
import fr.if26.projet.knotedge_if26.entity.Tag;

public class AdapterTag extends RecyclerView.Adapter<AdapterTag.ViewHolderTag> {

    private LayoutInflater layoutInflater;
    private Context context;
    private List<Tag> datas;


    public AdapterTag(Context context, List<Tag> datas) {
        this.context = context;
        this.datas = datas;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public AdapterTag.ViewHolderTag onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.item_recycle_view_tag, viewGroup, false);
        AdapterTag.ViewHolderTag myViewHolderBook = new AdapterTag.ViewHolderTag(view);
        return myViewHolderBook;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTag holder, int pos) {
        holder.nameTextView.setText(datas.get(pos).getName());
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

    class ViewHolderTag extends RecyclerView.ViewHolder {

        TextView nameTextView;

        public ViewHolderTag(@NonNull View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.name_tag_item_recycle_view);
        }
    }
}