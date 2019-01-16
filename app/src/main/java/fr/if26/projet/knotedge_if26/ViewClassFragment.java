package fr.if26.projet.knotedge_if26;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import fr.if26.projet.knotedge_if26.entity.Object;
import fr.if26.projet.knotedge_if26.util.AdapterObject;
import fr.if26.projet.knotedge_if26.util.DividerItemDecoration;

public class ViewClassFragment extends Fragment {

    private View view;
    private ArrayList<Object> allClasses;
    private AdapterObject adapter;
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
        view = inflater.inflate(R.layout.fragment_view_class, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_all_classes);
        setHasOptionsMenu(true);

        Bundle bundle = getArguments();
        allClasses = (ArrayList) bundle.getSerializable("classes");

        adapter = new AdapterObject(view.getContext(), allClasses);
        recyclerView.setAdapter(adapter);

        //recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(),2));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration decoration = new DividerItemDecoration(this.getContext(),DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(decoration);

        if (allClasses.size() == 0) {
            Toast.makeText(getContext(), "No classes", Toast.LENGTH_SHORT).show();
        }


        adapter.setOnItemClickListener(new AdapterObject.OnItemClickListener(){
            @Override
            public void onItemClick(View view , int position){
                int id = allClasses.get(position).getId();
                listener.loadDetailObject(id);
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.return_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_return:
                listener.loadFragmentProfile();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
