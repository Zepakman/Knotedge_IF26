package fr.if26.projet.knotedge_if26;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import fr.if26.projet.knotedge_if26.entity.Note;
import fr.if26.projet.knotedge_if26.util.AdapterNote;
import fr.if26.projet.knotedge_if26.util.DividerItemDecoration;

public class AllNotesFragment extends Fragment {

    private View view;
    private ArrayList<Note> allNotes;
    private AdapterNote adapter;
    private RecyclerView recyclerView;

    private TransmissionListener listener;
    @Override
    public void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);

        listener = (TransmissionListener) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_notes, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_all_notes);

        Bundle bundle = getArguments();
        allNotes = (ArrayList) bundle.getSerializable("notes");

        adapter = new AdapterNote(view.getContext(), allNotes);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        DividerItemDecoration decoration = new DividerItemDecoration(this.getContext(),DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(decoration);

        if(allNotes.size()==0) {
            Toast.makeText(getContext(), "No Notes", Toast.LENGTH_SHORT).show();
        }

        adapter.setOnItemClickListener(new AdapterNote.OnItemClickListener(){
            @Override
            public void onItemClick(View view , int position){
                int id = allNotes.get(position).getId();
                listener.loadDetailNote(id);
            }
        });

        return view;
    }
}
