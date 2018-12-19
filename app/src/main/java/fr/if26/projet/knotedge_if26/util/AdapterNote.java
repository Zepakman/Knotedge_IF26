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

import java.util.ArrayList;
import java.util.List;

import fr.if26.projet.knotedge_if26.R;
import fr.if26.projet.knotedge_if26.dao.KnotedgePersistance;
import fr.if26.projet.knotedge_if26.entity.Note;

public class AdapterNote extends RecyclerView.Adapter<AdapterNote.ViewHolderNote> implements View.OnClickListener {

    private LayoutInflater layoutInflater;
    private Context context;
    private List<Note> datas;
    private View view;

    private List<Integer> heights;

    private OnItemClickListener mOnItemClickListener = null;


    public AdapterNote(Context context, List<Note> datas) {
        this.context = context;
        this.datas = datas;
        layoutInflater = LayoutInflater.from(context);

        heights = new ArrayList<Integer>();
        for (int i = 0; i < datas.size() ; i++) {
            heights.add((int)(100+Math.random()*300));
        }
    }

    @NonNull
    @Override
    public AdapterNote.ViewHolderNote onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = layoutInflater.inflate(R.layout.item_recycle_view_note, viewGroup, false);
        AdapterNote.ViewHolderNote myViewHolderBook = new AdapterNote.ViewHolderNote(view);
        view.setOnClickListener(this);
        return myViewHolderBook;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderNote holder, int pos) {
        final int position = pos;
        holder.nameTextView.setText(datas.get(pos).getTitle());

        holder.itemView.setTag(pos);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //TODO:
                AlertDialog.Builder alert = new AlertDialog.Builder(new ContextThemeWrapper(view.getContext(), R.style.AppTheme));

                alert.setMessage("Voulez-vous supprimer cette note ainsi que ses liens ?");
                alert.setPositiveButton("OUI", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do your work here
                        KnotedgePersistance knotedgePersistance = new KnotedgePersistance(view.getContext());
                        knotedgePersistance.removeNote(datas.get(position));
                        notifyDataSetChanged();
                        deleteItem(position);
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

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public void deleteItem(int pos) {
        datas.remove(pos);
        notifyItemChanged(pos);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    class ViewHolderNote extends RecyclerView.ViewHolder {

        TextView nameTextView;

        public ViewHolderNote(@NonNull View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.title_note_item_recycle_view);
        }
    }

    public static interface OnItemClickListener {
        void onItemClick(View view , int position);
    }
}