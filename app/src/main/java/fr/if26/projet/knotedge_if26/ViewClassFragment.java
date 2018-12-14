package fr.if26.projet.knotedge_if26;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import fr.if26.projet.knotedge_if26.util.DividerItemDecoration;
import fr.if26.projet.knotedge_if26.util.AdapterObject;

public class ViewClassFragment extends Fragment {

    private View view;
    private ArrayList allClasses;
    private AdapterObject adapter;
    private RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_class, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_all_classes);

        Bundle bundle = getArguments();
        allClasses = (ArrayList) bundle.getSerializable("classes");

        adapter = new AdapterObject(view.getContext(), allClasses);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(),3));
        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(5,StaggeredGridLayoutManager.HORIZONTAL));

        DividerItemDecoration decoration = new DividerItemDecoration(this.getContext(),DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(decoration);


        return view;
    }
}