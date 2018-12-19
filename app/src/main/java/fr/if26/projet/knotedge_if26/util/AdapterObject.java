package fr.if26.projet.knotedge_if26.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import fr.if26.projet.knotedge_if26.R;
import fr.if26.projet.knotedge_if26.dao.KnotedgePersistance;
import fr.if26.projet.knotedge_if26.entity.Object;

public class AdapterObject extends RecyclerView.Adapter<AdapterObject.ViewHolderObject> {

    private LayoutInflater layoutInflater;
    private Context context;
    private List<Object> datas;
    private View view;


    public AdapterObject(Context context, List<Object> datas) {
        this.context = context;
        this.datas = datas;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolderObject onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = layoutInflater.inflate(R.layout.item_recycle_view_object, viewGroup, false);
        ViewHolderObject myViewHolder = new ViewHolderObject(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterObject.ViewHolderObject holder, int pos) {
        final int position = pos;
        holder.nameTextView.setText(datas.get(pos).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:
                Toast.makeText(context, "itemView clicked", Toast.LENGTH_SHORT).show();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //TODO:
                AlertDialog.Builder alert = new AlertDialog.Builder(new ContextThemeWrapper(view.getContext(), R.style.AppTheme));

                alert.setMessage("Voulez-vous supprimer cet objet ainsi que ses liens ?");
                alert.setPositiveButton("OUI", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do your work here
                        KnotedgePersistance knotedgePersistance = new KnotedgePersistance(view.getContext());
                        knotedgePersistance.removeObject(datas.get(position));
                        notifyDataSetChanged();

                        dialog.dismiss();

                    }
                });
                alert.setNegativeButton("NON", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                notifyDataSetChanged();
                alert.show();
                return true;

            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolderObject extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView typeTextView;

        public ViewHolderObject(@NonNull View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.name_item_recycle_view);
            typeTextView = (TextView) itemView.findViewById(R.id.type_item_recycle_view);
        }
    }
}


