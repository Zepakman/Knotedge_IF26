package fr.if26.projet.knotedge_if26;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import fr.if26.projet.knotedge_if26.util.AdapterTag;
import fr.if26.projet.knotedge_if26.util.DividerItemDecoration;

public class AllTagsFragment extends Fragment {

    private View view;
    private ArrayList allTags;
    private AdapterTag adapter;
    private RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_tags, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_all_tags);

        Bundle bundle = getArguments();
        allTags = (ArrayList) bundle.getSerializable("tags");

        adapter = new AdapterTag(view.getContext(), allTags);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(),3));

        DividerItemDecoration decoration = new DividerItemDecoration(this.getContext(),DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(decoration);

        if(allTags.size()==0) {
            Toast.makeText(getContext(), "No Tags", Toast.LENGTH_SHORT).show();
        }

        return view;
    }
}
