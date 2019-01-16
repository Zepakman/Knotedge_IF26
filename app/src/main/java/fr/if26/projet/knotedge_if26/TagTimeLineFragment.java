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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import fr.if26.projet.knotedge_if26.dao.KnotedgePersistance;
import fr.if26.projet.knotedge_if26.entity.Book;
import fr.if26.projet.knotedge_if26.entity.Object;
import fr.if26.projet.knotedge_if26.entity.Tag;
import fr.if26.projet.knotedge_if26.util.AdapterBook;
import fr.if26.projet.knotedge_if26.util.AdapterObject;
import fr.if26.projet.knotedge_if26.util.DividerItemDecoration;

public class TagTimeLineFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private View view;
    private Spinner spinnerTag;
    private Button selectTag;

    private RecyclerView recyclerViewBooks;
    private AdapterBook adapterBook;

    private RecyclerView recyclerViewObjects;
    private AdapterObject adapterObject;
    private String[] typeList = {"All classes","Book","Person","Event","Place","Object"};
    private int typeSelected = 0;

    private KnotedgePersistance knotedgePersistance;

    private TransmissionListener listener;


    @Override
    public void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);
        listener = (TransmissionListener) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tag_timeline, container, false);
        setHasOptionsMenu(true);

        recyclerViewBooks =  view.findViewById(R.id.recycle_view_all_books_by_tag);
        recyclerViewObjects = view.findViewById(R.id.recycle_view_all_object_by_tag);

        knotedgePersistance = new KnotedgePersistance(this.getContext());

        final ArrayList<String> tagList = knotedgePersistance.getAllTagsName();

        //SPINNER CODE
        spinnerTag = view.findViewById(R.id.tag_selector);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_item, tagList);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerTag.setAdapter(adapter);
        spinnerTag.setOnItemSelectedListener(this);


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.view_tag_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_tag_relation:
                listener.loadFragmentViewTagAlphabet();
                return true;
            case R.id.action_tag_timeline:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String selectedTag = (String) spinnerTag.getItemAtPosition(position);

        if (selectedTag != null) {
            Tag tag = knotedgePersistance.getTag(selectedTag);
            final ArrayList<Book> booksLinkedToTag = knotedgePersistance.getAllBooksByTag(tag);
            if (booksLinkedToTag == null) {
                Toast.makeText(getContext(), "No Books", Toast.LENGTH_SHORT).show();
            } else {
                adapterBook = new AdapterBook(this.getContext(), booksLinkedToTag);
                recyclerViewBooks.setAdapter(adapterBook);
                LinearLayoutManager linearLayoutManagerBooks = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
                recyclerViewBooks.setLayoutManager(linearLayoutManagerBooks);
                DividerItemDecoration decorationBook = new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL_LIST);
                recyclerViewBooks.addItemDecoration(decorationBook);

                adapterBook.setOnItemClickListener(new AdapterBook.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        int id = booksLinkedToTag.get(position).getId();
                        listener.loadDetailBook(id);
                    }
                });
            }

            final ArrayList<Object> objectsLinkedToTag = knotedgePersistance.getAllObjectsByTag(tag);

            if (objectsLinkedToTag == null) {
                Toast.makeText(getContext(), "No Objects", Toast.LENGTH_SHORT).show();
            } else {
                adapterObject = new AdapterObject(this.getContext(), objectsLinkedToTag);
                recyclerViewObjects.setAdapter(adapterObject);
                LinearLayoutManager linearLayoutManagerObjects = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
                recyclerViewObjects.setLayoutManager(linearLayoutManagerObjects);
                DividerItemDecoration decorationObject = new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL_LIST);
                recyclerViewObjects.addItemDecoration(decorationObject);

                adapterObject.setOnItemClickListener(new AdapterObject.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        int id = objectsLinkedToTag.get(position).getId();
                        listener.loadDetailObject(id);
                    }
                });

            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
