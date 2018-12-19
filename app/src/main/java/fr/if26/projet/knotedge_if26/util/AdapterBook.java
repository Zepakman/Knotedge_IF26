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

import java.util.List;

import fr.if26.projet.knotedge_if26.R;
import fr.if26.projet.knotedge_if26.dao.KnotedgePersistance;
import fr.if26.projet.knotedge_if26.entity.Book;

public class AdapterBook extends RecyclerView.Adapter<AdapterBook.ViewHolderBook> implements View.OnClickListener {

    private LayoutInflater layoutInflater;
    private Context context;
    private List<Book> datas;
    private View view;

    private OnItemClickListener mOnItemClickListener = null;

    public AdapterBook(Context context, List<Book> datas) {
        this.context = context;
        this.datas = datas;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolderBook onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = layoutInflater.inflate(R.layout.item_recycle_view_book, viewGroup, false);
        AdapterBook.ViewHolderBook myViewHolderBook = new AdapterBook.ViewHolderBook(view);
        view.setOnClickListener(this);
        return myViewHolderBook;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderBook holder, int pos) {
        final int position = pos;
        holder.nameTextView.setText(datas.get(pos).getName());

        holder.itemView.setTag(pos);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(new ContextThemeWrapper(view.getContext(), R.style.AppTheme));

                alert.setMessage("Voulez-vous supprimer ce livre ainsi que ses liens ?");
                alert.setPositiveButton("OUI", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        KnotedgePersistance knotedgePersistance = new KnotedgePersistance(view.getContext());
                        knotedgePersistance.removeBook(datas.get(position));
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

    class ViewHolderBook extends RecyclerView.ViewHolder {

        TextView nameTextView;

        public ViewHolderBook(@NonNull View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.name_book_item_recycle_view);
        }
    }

    public static interface OnItemClickListener {
        void onItemClick(View view , int position);
    }


}
