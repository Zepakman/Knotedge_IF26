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
import fr.if26.projet.knotedge_if26.entity.Book;

public class AdapterBook extends RecyclerView.Adapter<AdapterBook.ViewHolderBook> {

    private LayoutInflater layoutInflater;
    private Context context;
    private List<Book> datas;


    public AdapterBook(Context context, List<Book> datas) {
        this.context = context;
        this.datas = datas;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolderBook onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.item_recycle_view_book, viewGroup, false);
        AdapterBook.ViewHolderBook myViewHolderBook = new AdapterBook.ViewHolderBook(view);
        return myViewHolderBook;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderBook holder, int pos) {
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

    class ViewHolderBook extends RecyclerView.ViewHolder {

        TextView nameTextView;

        public ViewHolderBook(@NonNull View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.name_book_item_recycle_view);
        }
    }
}
