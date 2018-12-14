package fr.if26.projet.knotedge_if26.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.if26.projet.knotedge_if26.R;
import fr.if26.projet.knotedge_if26.entity.Note;

public class AdapterNote extends RecyclerView.Adapter<AdapterNote.ViewHolderNote> {

    private LayoutInflater layoutInflater;
    private Context context;
    private List<Note> datas;

    private List<Integer> heights;

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
        View view = layoutInflater.inflate(R.layout.item_recycle_view_note, viewGroup, false);
        AdapterNote.ViewHolderNote myViewHolderBook = new AdapterNote.ViewHolderNote(view);
        return myViewHolderBook;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderNote holder, int pos) {

        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = heights.get(pos);
        holder.itemView.setLayoutParams(layoutParams);
        holder.nameTextView.setText(datas.get(pos).getTitle());
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

    class ViewHolderNote extends RecyclerView.ViewHolder {

        TextView nameTextView;

        public ViewHolderNote(@NonNull View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.title_note_item_recycle_view);
        }
    }
}