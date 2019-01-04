package fr.if26.projet.knotedge_if26;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import fr.if26.projet.knotedge_if26.dao.KnotedgePersistance;
import fr.if26.projet.knotedge_if26.entity.Tag;
import fr.if26.projet.knotedge_if26.util.AdapterTag;
import fr.if26.projet.knotedge_if26.util.DividerItemDecoration;

public class AllTagsFragment extends Fragment {

    private View view;
    private ArrayList allTags;
    public AdapterTag adapter;
    public RecyclerView recyclerView;
    private KnotedgePersistance knotedgePersistance;
    private TransmissionListener listener;

    @Override
    public void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);

        listener = (TransmissionListener) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_tags, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_all_tags);
        setHasOptionsMenu(true);
        knotedgePersistance = new KnotedgePersistance(getContext());

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





    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.return_menu, menu);
        inflater.inflate(R.menu.add_tag_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_tag:
                AlertDialog.Builder alert = new AlertDialog.Builder(new ContextThemeWrapper(view.getContext(), R.style.AppTheme));
                final EditText fieldTagName = new EditText(getContext());
                alert.setView(fieldTagName);
                alert.setMessage("Nom du tag");
                alert.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        KnotedgePersistance knotedgePersistance = new KnotedgePersistance(view.getContext());
                        Tag newTag = new Tag("");
                        newTag.setName(fieldTagName.getText().toString());
                        knotedgePersistance.addTag(newTag);
                        adapter.insertItem(newTag);
                        dialog.dismiss();
                    }
                });
                alert.setNegativeButton("NON", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
                return true;
            case R.id.action_return:

                listener.loadFragmentProfile();


            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
